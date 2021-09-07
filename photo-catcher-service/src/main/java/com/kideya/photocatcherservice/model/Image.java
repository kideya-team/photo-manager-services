package com.kideya.photocatcherservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    private String id;

    private long userId;
    private long groupId;
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
