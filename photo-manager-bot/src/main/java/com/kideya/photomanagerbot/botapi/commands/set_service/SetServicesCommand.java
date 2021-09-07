package com.kideya.photomanagerbot.botapi.commands.set_service;

import com.kideya.photomanagerbot.botapi.TelegramFacade;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import com.kideya.photomanagerbot.botapi.commands.set_service.buttons_headlers.ButtonPressHandler;
import com.kideya.photomanagerbot.utils.KeyboardBuilder;
import com.kideya.photomanagerbot.model.settings_service.ServiceSettings;
import com.kideya.photomanagerbot.services.LocaleTextService;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SetServicesCommand implements BotCommand, Worker {

    private final String name = "/setServices";

    @Autowired
    private TextService textService;

    @Autowired
    private LocaleTextService localeTextService;

    @Autowired
    private List<ButtonPressHandler> handlers;

    @Autowired
    private TelegramFacade facade;

    private final Map<Integer, List<ServiceSettings>> cache = new HashMap<>();
    private final Map<Integer, ButtonPressHandler> currentHandler = new HashMap<>();

    @Override
    public BotApiMethod<?> runCommand(Update update) {

        Integer userId = update.getMessage().getFrom().getId();

        //load data

        //-------------
        List<ServiceSettings> settings = new ArrayList<>();
        ServiceSettings serviceSetting = new ServiceSettings();
        serviceSetting.setActive(true);
        serviceSetting.setServiceName("hdd");
        settings.add(serviceSetting);

        ServiceSettings serviceSetting1 = new ServiceSettings();
        serviceSetting1.setActive(false);
        serviceSetting1.setServiceName("disk");
        settings.add(serviceSetting1);
        //-------------

        cache.put(userId, settings);
        facade.changeBehaviour(userId, this);



        return sendStartMessage(update, settings);
    }

    @Override
    public String getCommandName() {
        return name;
    }

    private InlineKeyboardMarkup buildKeyboard(List<ServiceSettings> settings) {

        KeyboardBuilder keyboardBuilder = new KeyboardBuilder();

        for (ServiceSettings setting : settings) {

            String buttonName = setting.getServiceName();

            if (setting.isActive()) {
                buttonName += " - " + localeTextService.getTranslatedText("button.status");
            }
            keyboardBuilder.addButton(buttonName, setting.getServiceName());
        }

        return keyboardBuilder.addButton(localeTextService.getTranslatedText("button.back"), "back").build();
    }

    @Override
    public BotApiMethod<?> doWork(Update update) {
        Integer userId = Utils.getUserId(update);
        List<ServiceSettings> settings = cache.get(userId);

        if (currentHandler.containsKey(userId)) {
            ButtonPressHandler handler = currentHandler.get(userId);
            BotApiMethod result = handler.pressHandle(update, getSettings(settings, handler.getName()));
            if (handler.isFinished(userId)) {
                currentHandler.remove(userId);
                return sendStartMessage(update, settings);
            } else {
                return result;
            }
        }

        if (update.hasCallbackQuery()) {
            String handlerName = update.getCallbackQuery().getData();

            for (ButtonPressHandler handler : handlers) {
                if (handler.getName().equals(handlerName)) {

                    currentHandler.put(userId, handler);
                    return handler.pressHandle(update, getSettings(settings, handlerName));
                }
            }

            throw new IllegalArgumentException();
        }

        return new AnswerCallbackQuery();
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return cache.containsKey(userId);
    }

    @Component
    protected class BackButtonHandler implements ButtonPressHandler {

        @Override
        public String getName() {
            return "back";
        }

        @Override
        public BotApiMethod<?> pressHandle(Update update, ServiceSettings settings) {
            Integer userId = update.getCallbackQuery().getFrom().getId();
            cache.remove(userId);
            return new AnswerCallbackQuery();
        }

        @Override
        public boolean isFinished(Integer userId) {
            return true;
        }
    }

    private ServiceSettings getSettings(List<ServiceSettings> settings, String serviceName) {
        return settings.stream().filter(setting -> setting.getServiceName().equals(serviceName)).findFirst().orElse(new ServiceSettings());
    }

    private SendMessage sendStartMessage(Update update, List<ServiceSettings> settings) {
        SendMessage message =
                textService.getMessage(Utils.getChatId(update), "reply.setGroupHeader");

        message.setReplyMarkup(buildKeyboard(settings));
        return message;
    }
}
