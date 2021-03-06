package com.kideya.photomanagerbot.model.settings_service;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class ServiceSettings {
    private String serviceName;
    private boolean isActive;
    private String params;

    public void loadData(ServiceSettings settings) {
        serviceName = settings.getServiceName();
        isActive = settings.isActive();
        params = settings.getParams();
    }
}