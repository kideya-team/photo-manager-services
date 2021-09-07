package com.kideya.photomanagerbot.botapi.bot_workers;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Worker {

    BotApiMethod doWork(Update update);

    boolean isStillWorking(Integer userId);
}
