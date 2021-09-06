package com.kideya.photosettingsservice.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ServiceSettings {
    private String serviceName; // change to enum?
    private boolean isActive;
    private String params;
}
