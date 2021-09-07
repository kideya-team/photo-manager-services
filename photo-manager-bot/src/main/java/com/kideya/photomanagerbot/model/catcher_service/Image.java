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
    @Id
    private String id;

    private int userId;
    private int groupId;
    private List<ImageTag> tags;
    private LocalDate date;

    public Image(String imageId, int userId, int groupId, List<ImageTag> tags, LocalDate date) {
        this.id = imageId;
        this.userId = userId;
        this.groupId = groupId;
        this.tags = tags;
        this.date = date;
    }
}