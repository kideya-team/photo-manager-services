package com.kideya.photocatcherservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageTag {
    @Id
    public String id;

    public String value;
}
