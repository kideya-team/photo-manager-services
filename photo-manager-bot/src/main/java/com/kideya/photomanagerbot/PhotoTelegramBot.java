package com.kideya.photomanagerbot;

import com.kideya.photomanagerbot.botapi.TelegramFacade;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@Setter
public class PhotoTelegramBot extends TelegramWebhookBot {

    private String botPath;
    private String botUsername;
    private String botToken;

    @Autowired
    private TelegramFacade facade;

    public PhotoTelegramBot(DefaultBotOptions options) {
        super(options);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        System.out.println("UPDATE RECEIVED!!");

        if (update.getMessage() != null) {
            return facade.handleUpdate(update);
        }

        return new AnswerCallbackQuery();
    }

}
