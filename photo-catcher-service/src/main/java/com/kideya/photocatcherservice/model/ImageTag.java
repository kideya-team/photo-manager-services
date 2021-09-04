package com.kideya.photocatcherservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class ImageTag {
    @Id
    private String id;

    public String value;

    public ImageTag() {

    }

    public ImageTag(String value) {
        this.value = value;
    }
}
