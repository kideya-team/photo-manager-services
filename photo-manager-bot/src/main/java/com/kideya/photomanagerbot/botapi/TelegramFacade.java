package com.kideya.photomanagerbot.botapi;

import com.kideya.photomanagerbot.botapi.bot_workers.DefaultWorker;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.image_processing.ImageProcessor;
import com.kideya.photomanagerbot.services.TextService;
import com.kideya.photomanagerbot.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class TelegramFacade {

    private final Map<Integer, Worker> usersCache = new HashMap<>();

    @Autowired
    private ImageProcessor imageProcessor;

    @Autowired
    private ApplicationContext context;

    public BotApiMethod<?> handleUpdate(Update update) {

        Integer userId = Utils.getUserId(update);

        if (update.hasMessage()) {
            Message message = update.getMessage();

            if (message.getChat().isGroupChat()) {
                return handleGroupMessage(update);
            }

            if (usersCache.containsKey(userId) && usersCache.get(userId).isStillWorking(userId)) {
                return usersCache.get(userId).doWork(update);
            }

        }

        if (update.hasCallbackQuery()) {
            if (usersCache.containsKey(userId) && usersCache.get(userId).isStillWorking(userId)) {
                return usersCache.get(userId).doWork(update);
            }
        }

        DefaultWorker defaultWorker = context.getBean(DefaultWorker.class);
        usersCache.put(userId, defaultWorker);

        return defaultWorker.doWork(update);
    }

    private BotApiMethod<?> handleGroupMessage(Update update) {

        imageProcessor.processImage(update.getMessage());
        return new AnswerCallbackQuery();
    }

    public void changeBehaviour(int userId, Worker worker) {
        usersCache.computeIfPresent(userId, (k, v) -> worker);
    }

}
