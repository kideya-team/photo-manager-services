package com.kideya.photocatcherservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicePhotoInfoDto {
    private String params;
    private String imageId;
    private long userId;
}