package com.kideya.photomanagerbot.botapi.bot_workers.simple_flow;

import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.bot_workers.simple_flow.Params;
import com.kideya.photomanagerbot.services.PhotoLoaderService;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleFlowWorker implements Worker {

    private Map<Integer, Params> params = new HashMap<>();

    @Autowired
    private TextService textService;

    @Autowired
    private PhotoLoaderService loaderService;


    @Override
    public BotApiMethod<?> doWork(Update update) {
        Integer userId = Utils.getUserId(update);

        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            params.get(userId).setArg(text);

        }

        return new AnswerCallbackQuery();
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return false;
    }

    public SendMessage registerCommand(Update update, Params commandParams) {
        Integer userId = Utils.getUserId(update);
        Long chatId = Utils.getChatId(update);

        params.put(userId, commandParams);

        return textService.getMessage(chatId, commandParams.getResourceTag() + ".start");
    }

    private void
}
