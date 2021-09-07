package com.kideya.photomanagerbot.botapi.image_processing;

import com.kideya.photomanagerbot.model.catcher_service.Image;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDate;
import java.util.List;

@Component
public class GroupImageProcessor implements ImageProcessor{

    @Autowired
    private SendingMessageService sendingMessageService;

    public BotApiMethod<?> processImage(Message message) {
        List<PhotoSize> photo = message.getPhoto();

        if (photo != null) {

            Image image = prepareImage(message, message.getChatId(), message.getFrom().getId());
            sendingMessageService.send(MicroservicesNames.CATCHER_SERVICE_NAME, "/api/catcher/add", image);

        }
        return null;
    }

    private Image prepareImage(Message message, long groupId, int userId) {

        List<PhotoSize> photos = message.getPhoto();

        PhotoSize photo = photos.get(photos.size() - 1);

        return Image.builder()
                 .id(photo.getFileId())
                 .groupId((int) groupId) ///TODO fix this cast
                 .userId(userId)
                 .date(LocalDate.now()).build();
    }
}