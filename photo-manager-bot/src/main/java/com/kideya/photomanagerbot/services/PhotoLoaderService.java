package com.kideya.photomanagerbot.services;

import com.kideya.photomanagerbot.model.catcher_service.Image;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PhotoLoaderService {

    @Autowired
    private SendingMessageService sendingMessageService;

    @Autowired
    private TelegramApiSendingService telegramApiSendingService;

    public List<Image> loadPhotos(String url) {
        ResponseEntity<Image[]> responce = sendingMessageService.sendGet(MicroservicesNames.CATCHER_SERVICE_NAME, url, Image[].class);

        return Arrays.asList(Objects.requireNonNull(responce.getBody()));
    }

    public void sendPhotosToChat(List<Image> images) {


//        for (Image image : images) {
//            telegramApiSendingService.sendImage();
//        }
    }
}
