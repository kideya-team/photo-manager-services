package com.kideya.photocatcherservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Image {
    @Id
    private String id;

    private int userId;
    private int groupId;
    private List<ImageTag> tags;
    private LocalDateTime date;

    public Image(String imageId, int userId, int groupId, List<ImageTag> tags, LocalDateTime date) {
        this.id = imageId;
        this.userId = userId;
        this.groupId = groupId;
        this.tags = tags;
        this.date = date;
    }
}
