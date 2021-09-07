package com.kideya.photomanagerbot.botapi.commands.set_service.buttons_headlers;

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
import java.util.List;

@Component
public class HddButtonHandler implements ButtonPressHandler {

    private final List<Integer> currentUsers = new ArrayList<>();

    private static final String BACK_BUTTON = "BACK_BUTTON";
    private static final String STATUS_CHANGE_BUTTON = "STATUS_CHANGE_BUTTON";
    private static final String FOLDER_NAME_BUTTON = "FOLDER_NAME_BUTTON";

    @Autowired
    private TextService textService;

    @Autowired
    private LocaleTextService localeTextService;

    @Override
    public String getName() {
        return "hdd";
    }

    @Override
    public BotApiMethod<?> pressHandle(Update update, ServiceSettings settings) {

        Integer userId = Utils.getUserId(update);

        if (currentUsers.contains(userId)) {
            if (update.hasCallbackQuery()) {

                if (update.getCallbackQuery().getData().equals(STATUS_CHANGE_BUTTON)) {
                    return statusButtonHandle(update, settings);
                }

                if (update.getCallbackQuery().getData().equals(FOLDER_NAME_BUTTON)) {
                    System.out.println("Unsupported now");
                    return new AnswerCallbackQuery();
                }

                if (update.getCallbackQuery().getData().equals(BACK_BUTTON)) {
                    return backButtonHandle(update, settings);
                }
            }
            return new AnswerCallbackQuery();
        }

        currentUsers.add(userId);

        return sendMenu(update);
    }

    @Override
    public boolean isFinished(Integer userId) {
        return !currentUsers.contains(userId);
    }

    private SendMessage sendMenu(Update update) {
        SendMessage message = textService.getMessage(Utils.getChatId(update), "button.hdd_handler.settings");
        InlineKeyboardMarkup keyboard = new KeyboardBuilder()
                .addButton("вкл / выкл", STATUS_CHANGE_BUTTON)
                .addButton("имя папки", FOLDER_NAME_BUTTON)
                .addButton(localeTextService.getTranslatedText("button.back"), BACK_BUTTON)
                .build();

        message.setReplyMarkup(keyboard);

        return message;
    }


    private SendMessage statusButtonHandle(Update update, ServiceSettings settings) {

        settings.setActive(!settings.isActive());
        //send post
        currentUsers.remove(Utils.getUserId(update));
        return textService.getMessage(Utils.getChatId(update), "button.hdd_handler.confirm");

    }

    private BotApiMethod<?> backButtonHandle(Update update, ServiceSettings settings) {

        currentUsers.remove(Utils.getUserId(update));

        return new AnswerCallbackQuery();

    }
}
