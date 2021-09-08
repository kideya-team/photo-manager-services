package com.kideya.photomanagerbot.botapi.image_processing;

import com.kideya.photomanagerbot.model.catcher_service.Image;
import com.kideya.photomanagerbot.model.catcher_service.ImageTag;
import com.kideya.photomanagerbot.services.SendingMessageService;
import com.kideya.photomanagerbot.utils.MicroservicesNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupImageProcessor implements ImageProcessor{

    @Autowired
    private SendingMessageService sendingMessageService;

    public BotApiMethod<?> processImage(Message message) {
        List<PhotoSize> photo = message.getPhoto();

        if (photo != null) {

            Image image = prepareImage(message, message.getChatId(), message.getFrom().getId());
            sendingMessageService.sendPost(MicroservicesNames.CATCHER_SERVICE_NAME, "/api/catcher/photo", image, String.class);

        }
        return new AnswerCallbackQuery();
    }

    private Image prepareImage(Message message, long groupId, int userId) {

        List<PhotoSize> photos = message.getPhoto();

        PhotoSize photo = photos.get(photos.size() - 1);

        return Image.builder()
                 .id(photo.getFileId())
                 .groupId(groupId)
                 .userId(userId)
                 .tags(parseTags(message.getCaption()))
                 .date(LocalDate.now()).build();
    }

    private List<ImageTag> parseTags(String text) {

        List<ImageTag> tags = new ArrayList<>();

        if (text != null && text.contains("#")) {
            tags = Arrays.stream(text.split(" "))
                    .filter(s -> s.startsWith("#"))
                    .map(s -> s.replace("#", ""))
                    .map(ImageTag::new)
                    .collect(Collectors.toList());
        }

        return tags;
    }
}
