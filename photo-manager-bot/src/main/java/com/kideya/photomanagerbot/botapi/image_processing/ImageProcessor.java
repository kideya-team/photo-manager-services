package com.kideya.photomanagerbot.botapi.image_processing;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface ImageProcessor {
    BotApiMethod<?> processImage(Message message);
}
