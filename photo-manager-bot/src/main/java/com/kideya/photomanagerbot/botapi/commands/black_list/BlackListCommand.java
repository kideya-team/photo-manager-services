package com.kideya.photomanagerbot.botapi.commands.black_list;

import com.kideya.photomanagerbot.botapi.TelegramFacade;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import com.kideya.photomanagerbot.botapi.commands.set_service.buttons_headlers.ButtonPressHandler;
import com.kideya.photomanagerbot.model.settings_service.GroupsDto;
import com.kideya.photomanagerbot.model.settings_service.ServiceSettings;
import com.kideya.photomanagerbot.services.LocaleTextService;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.KeyboardBuilder;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.lang.reflect.Array;
import java.util.*;

public class BlackListCommand implements BotCommand, Worker {

	private static final String NAME = "/setBlackList";

	@Autowired
	private SendingMessageService sendingMessageService;

	@Autowired
	private TextService textService;

	@Autowired
	private LocaleTextService localeTextService;

	@Autowired
	private List<ButtonPressHandler> handlers;

	@Autowired
	private TelegramFacade facade;

	private final Map<Integer, List<Long>> cache = new HashMap<>();
	private final Map<Integer, ButtonPressHandler> currentHandler = new HashMap<>();

	@Override
	public BotApiMethod doWork(Update update) {
		return null;
	}

	@Override
	public boolean isStillWorking(Integer userId) {
		return cache.containsKey(userId);
	}

	@Override
	public BotApiMethod<?> runCommand(Update update) {
		Integer userId = Utils.getUserId(update);
		String groupsUrl = "/api/settings/user/"+userId+"/blacklist";
		ResponseEntity<Long[]> response = sendingMessageService.sendGet(MicroservicesNames.SETTINGS_SERVICE_NAME,
				groupsUrl, Long[].class);

		List<Long> blackList = Arrays.asList(Objects.requireNonNull(response.getBody()));
		cache.put(userId, blackList);
		facade.changeBehaviour(userId, this);

		return sendStartMessage(update, blackList);
	}

	private SendMessage sendStartMessage(Update update, List<Long> blackList) {
		SendMessage message =
				textService.getMessage(Utils.getChatId(update), "black_list.users");

		message.setReplyMarkup(buildKeyboard(blackList));
		return message;
	}

	private InlineKeyboardMarkup buildKeyboard(List<Long> blackList) {

		KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

		for (Long userId : blackList) {

			String buttonName = String.valueOf(userId);

			buttonName += " - " + localeTextService.getTranslatedText("black_list.remove");
			keyboardBuilder.addButton(buttonName, String.valueOf(userId));
		}

		return keyboardBuilder.addButton(localeTextService.getTranslatedText("button.back"), "back").build();
	}

	@Override
	public String getCommandName() {
		return NAME;
	}
}
