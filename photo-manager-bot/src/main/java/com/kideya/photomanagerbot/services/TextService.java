package com.kideya.photomanagerbot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Locale;

@Service
public class TextService {

    @Autowired
    private LocaleTextService localeTextService;

    public SendMessage getMessage(long groupId, String resource) {
        String text = localeTextService.getTranslatedText(resource);
        return new SendMessage(groupId, text);
    }

}
