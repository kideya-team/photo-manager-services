package com.kideya.photomanagerbot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kideya.photomanagerbot.PhotoTelegramBot;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GettingImageServiceImpl implements GettingImageService{
    @Autowired
    private PhotoTelegramBot photoTelegramBot;

    @Value("${telegrambot.apiurl}")
    private String telegramApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @SneakyThrows
    public String getImagePathById(String imageId) {
        return getFilePath(imageId);
    }

    @SneakyThrows
    private String getFilePath(String imageId) {
        ResponseEntity<String> response = restTemplate.getForEntity(telegramApiUrl + "/bot" + photoTelegramBot.getBotToken() + "/getFile?file_id=" + imageId, String.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalStateException();
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());

        return telegramApiUrl + "/file/bot" + photoTelegramBot.getBotToken() + "/" + root.path("result").path("file_path").asText();
    }
}
