package com.kideya.photomanagerbot.controller;

import com.kideya.photomanagerbot.PhotoManagerBotApplication;
import com.kideya.photomanagerbot.PhotoTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {

    @Autowired
    private PhotoTelegramBot photoTelegramBot;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return photoTelegramBot.onWebhookUpdateReceived(update);
    }
}
