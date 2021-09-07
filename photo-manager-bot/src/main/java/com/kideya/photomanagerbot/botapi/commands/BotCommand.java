package com.kideya.photomanagerbot.botapi.commands;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface BotCommand {

    BotApiMethod<?> runCommand(Update update);
    String getCommandName();
}
