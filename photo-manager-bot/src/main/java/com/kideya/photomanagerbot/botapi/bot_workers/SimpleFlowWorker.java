package com.kideya.photomanagerbot.botapi.bot_workers;

import com.kideya.photomanagerbot.botapi.bot_workers.Params;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.commands.BotCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
public class SimpleFlowWorker implements Worker {

    private Map<Integer, Params> params;

    @Override
    public BotApiMethod doWork(Update update) {
        return null;
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return false;
    }

    public void registerCommand(Integer userId, Params commandParams) {
        params.put(userId, commandParams);
    }
}
