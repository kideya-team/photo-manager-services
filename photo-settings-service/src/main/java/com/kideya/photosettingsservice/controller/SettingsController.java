package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import com.kideya.photosettingsservice.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/health")
    public String health() {
        return "Healthy";
    }

}