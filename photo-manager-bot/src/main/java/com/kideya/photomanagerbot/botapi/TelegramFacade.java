package com.kideya.photomanagerbot.botapi;

import com.kideya.photomanagerbot.botapi.bot_workers.DefaultWorker;
import com.kideya.photomanagerbot.botapi.bot_workers.Worker;
import com.kideya.photomanagerbot.botapi.image_processing.ImageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    public SendMessage handleUpdate(Update update) {

        if (update.getMessage().getChat().isGroupChat()) {
            return handleGroupMessage(update);
        }

        Integer userId = update.getMessage().getFrom().getId();

        if (usersCache.containsKey(userId) && usersCache.get(userId).isStillWorking()) {
            return usersCache.get(userId).doWork(update);
        }

        DefaultWorker defaultWorker = context.getBean(DefaultWorker.class);
        usersCache.put(userId, defaultWorker);

        return defaultWorker.doWork(update);
    }

    private SendMessage handleGroupMessage(Update update) {

        imageProcessor.processImage(update.getMessage());
        return new SendMessage(update.getMessage().getChatId(), "Это груууппа");
    }
}
