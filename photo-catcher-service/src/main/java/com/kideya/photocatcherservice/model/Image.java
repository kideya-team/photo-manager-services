package com.kideya.photocatcherservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@Builder
public class Image {
    @Id
    private String id;

    private Long userId;
    private Long groupId;
    private List<ImageTag> tags;
    private LocalDate date;
}
