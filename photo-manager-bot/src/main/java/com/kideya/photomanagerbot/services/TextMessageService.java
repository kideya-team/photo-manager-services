package com.kideya.photomanagerbot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Locale;

@Service
public class TextMessageService {

    private final Locale locale;
    private final MessageSource messageSource;

    public TextMessageService(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }


    public SendMessage getMessage(long groupId, String resource) {
        String text = messageSource.getMessage(resource, null, locale);
        return new SendMessage(groupId, text);
    }

//    public SendMessage getMessage(String message, Object... args) {
//        return messageSource.getMessage(message, args, locale);
//    }

}
