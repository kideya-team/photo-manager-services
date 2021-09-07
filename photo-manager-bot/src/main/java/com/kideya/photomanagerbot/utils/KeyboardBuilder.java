package com.kideya.photomanagerbot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class KeyboardBuilder {

    private List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

    public KeyboardBuilder addButton(String buttonName) {
        return addButton(buttonName, buttonName);
    }

    public KeyboardBuilder addButton(String buttonName, String handlerName) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        InlineKeyboardButton button = new InlineKeyboardButton().setText(buttonName);
        button.setCallbackData(handlerName);

        row.add(button);
        keyboard.add(row);
        return this;
    }

    public InlineKeyboardMarkup build() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        inlineKeyboardMarkup.setKeyboard(keyboard);
        keyboard = new ArrayList<>();

        return inlineKeyboardMarkup;
    }

}
