package com.kideya.photomanagerbot.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class TelegramApiSendingService {

    @Autowired
    private RestTemplate restTemplate;

    private String baseUrl;

    @Value("${telegrambot.botToken}")
    private String token;

    @PostConstruct
    private void init() {
        baseUrl = "https://api.telegram.org/bot" + token;
    }

    public void sendTextMessage(Long chatId, String text) {
        String url = baseUrl + "/sendMessage?chat_id=" + chatId + "&text=" + text;
        restTemplate.getForEntity(url, Object.class);
    }

    public void sendImage(Long chatId, String imageId) {
        String url = baseUrl + "/sendPhoto?chat_id=" + chatId + "&photo=" + imageId;
        restTemplate.getForEntity(url, Object.class);
    }
}
