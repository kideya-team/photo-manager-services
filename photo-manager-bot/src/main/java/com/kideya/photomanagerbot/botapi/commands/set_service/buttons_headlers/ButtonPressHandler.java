package com.kideya.photomanagerbot.botapi.commands.set_service.buttons_headlers;

import com.kideya.photomanagerbot.model.settings_service.ServiceSettings;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

public interface ButtonPressHandler {

    String getName();

    BotApiMethod<?> pressHandle(Update update, ServiceSettings settings);

    boolean isFinished(Integer userId);
}
