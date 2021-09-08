package com.kideya.photomanagerbot.botapi.commands.set_service;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SupportedServices {

    HDD("Hdd"),
    DRIVE("Google_drive");

    @Getter
    private final String name;
}
