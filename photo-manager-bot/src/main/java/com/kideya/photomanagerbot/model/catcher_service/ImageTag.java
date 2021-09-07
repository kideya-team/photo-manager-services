package com.kideya.photomanagerbot.model.catcher_service;

import lombok.*;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ImageTag {
    private String value;

    public ImageTag(String value) {
        this.value = value;
    }
}