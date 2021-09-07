package com.kideya.photomanagerbot.model.catcher_service;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Builder
public class Image {
    private String id;

    private int userId;
    private long groupId;
    private List<ImageTag> tags;
    private LocalDate date;

    public Image(String imageId, int userId, long groupId, List<ImageTag> tags, LocalDate date) {
        this.id = imageId;
        this.userId = userId;
        this.groupId = groupId;
        this.tags = tags;
        this.date = date;
    }
}