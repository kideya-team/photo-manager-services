package com.kideya.photomanagerbot.botapi.bot_workers.simple_flow;

import com.kideya.photomanagerbot.botapi.TelegramFacade;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.bot_workers.simple_flow.Params;
import com.kideya.photomanagerbot.model.catcher_service.Image;
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
import java.util.List;
import java.util.Map;

@Component
public class SimpleFlowWorker implements Worker {

    private static final String BASE_URL = "/api/provider/";

    private final Map<Integer, Params> params = new HashMap<>();

    @Autowired
    private TextService textService;

    @Autowired
    private PhotoLoaderService loaderService;

    @Autowired
    private TelegramFacade facade;

    @Override
    public BotApiMethod<?> doWork(Update update) {
        Integer userId = Utils.getUserId(update);
        Long chatId = Utils.getChatId(update);

        if (update.hasMessage()) {
            String text = update.getMessage().getText();
            params.get(userId).setArg(text);

            sendPhotos(userId, chatId);
            String tag = params.get(userId).getResourceTag();
            params.remove(userId);
            return textService.getMessage(chatId,  tag + ".request_was_sent_to_server");
        }

        return new AnswerCallbackQuery();
    }

    @Override
    public boolean isStillWorking(Integer userId) {
        return params.containsKey(userId);
    }

    public SendMessage registerCommand(Update update, Params commandParams) {
        Integer userId = Utils.getUserId(update);
        Long chatId = Utils.getChatId(update);

        params.put(userId, commandParams);

        facade.changeBehaviour(userId, this);
        return textService.getMessage(chatId, commandParams.getResourceTag() + ".start");
    }

    private void sendPhotos(Integer userId, Long chatId) {
        Params userParams = this.params.get(userId);

        List<Image> images = loaderService.loadPhotos(BASE_URL
                + userId
                + userParams.getRestPoint()
                + "?" + userParams.getArgName()
                + "=" + userParams.getArg());

        loaderService.sendPhotosToChat(chatId, images);
    }
}
