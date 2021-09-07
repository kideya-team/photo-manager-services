package com.kideya.photocatcherservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ServiceSettingsDto {
    private String serviceName; // change to enum?
    private boolean isActive;
    private String params;
}