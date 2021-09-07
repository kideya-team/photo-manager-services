package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/user/{userId}")
    public Settings get(@PathVariable Long userId) {
        return settingsService.getSettingsByUserId(userId);
    }

    @PostMapping("/user/{id}/register")
    public void register(@PathVariable Long id) {
        settingsService.register(id);
    }

}