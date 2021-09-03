package com.kideya.photocatcherservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    public String id;

    public String imageId;
    public int userId;
    public int groupId;
    public List<ImageTag> tags;
    public LocalDateTime date;
}
