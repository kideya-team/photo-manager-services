package com.kideya.photomanagerbot.botapi.commands.subscribe_processing;

import com.kideya.photomanagerbot.services.LocaleTextService;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.services.TelegramApiSendingService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SubscribeCommandHandler implements CommandHandler {

    private static final String NAME = "/subscribe";

    @Autowired
    private SendingMessageService sendingMessageService;

    @Autowired
    private TelegramApiSendingService telegramApiSendingService;

    @Autowired
    private LocaleTextService localeService;

    @Override
    public BotApiMethod<?> handle(Update update) {
        Integer userId = Utils.getUserId(update);
        Long chatId = Utils.getChatId(update);
        String subscribeUrl = "/api/settings/user/" + userId + "/groups/" + chatId;
        sendingMessageService.sendPost(MicroservicesNames.SETTINGS_SERVICE_NAME, subscribeUrl, "", Object.class);

        String textMessage = localeService.getTranslatedText("groups.subscribed_successfully") + chatId;
        telegramApiSendingService.sendTextMessage(userId.longValue(), textMessage);
        return new AnswerCallbackQuery();
    }

    @Override
    public String getMyCommand() {
        return NAME;
    }

}
