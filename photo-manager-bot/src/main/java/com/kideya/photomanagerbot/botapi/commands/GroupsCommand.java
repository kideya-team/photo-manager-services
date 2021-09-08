package com.kideya.photomanagerbot.botapi.commands;

import com.kideya.photomanagerbot.model.settings_service.GroupsDto;
import com.kideya.photomanagerbot.services.LocaleTextService;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.services.TelegramApiSendingService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GroupsCommand implements BotCommand {

    private final String name = "/groups";

    @Autowired
    private SendingMessageService sendingMessageService;

    @Autowired
    private TelegramApiSendingService telegramApiSendingService;

    @Autowired
    private LocaleTextService localeService;

    @Override
    public BotApiMethod<?> runCommand(Update update) {
        Integer userId = Utils.getUserId(update);
        String groupsUrl = "/api/settings/user/"+userId+"/groups";
        ResponseEntity<GroupsDto> groupsDtoEntity = sendingMessageService.sendGet(MicroservicesNames.SETTINGS_SERVICE_NAME,
                groupsUrl, GroupsDto.class);

        Long chatId = Utils.getChatId(update);
        String textMessage = localeService.getTranslatedText("groups.get_groups") + "\n" +
                groupsDtoEntity.getBody().getGroupsString();
        telegramApiSendingService.sendTextMessage(chatId, textMessage);

        return new AnswerCallbackQuery();
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
