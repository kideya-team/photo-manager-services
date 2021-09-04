package com.kideya.photocatcherservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class Image {
    @Id
    public String id;

    public int userId;
    public int groupId;
    public List<ImageTag> tags;
    public LocalDateTime date;

    public Image() {

    }

    public Image(String imageId, int userId, int groupId, List<ImageTag> tags, LocalDateTime date) {
        this.id = imageId;
        this.userId = userId;
        this.groupId = groupId;
        this.tags = tags;
        this.date = date;
    }
}
