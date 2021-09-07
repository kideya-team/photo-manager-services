package com.kideya.photomanagerbot.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleTextService {

    private final Locale locale;
    private final MessageSource messageSource;

    public LocaleTextService(@Value("${localeTag}") String localeTag, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = Locale.forLanguageTag(localeTag);
    }

    public String getTranslatedText(String resource) {
        return messageSource.getMessage(resource, null, locale);
    }
}
