package com.kideya.photomanagerbot.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

public class Utils {

    public static Integer getUserId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getFrom().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId();
        }

        throw new IllegalStateException();
    }

    public static Long getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChat().getId();
        }

        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }

        throw new IllegalStateException();
    }
}
