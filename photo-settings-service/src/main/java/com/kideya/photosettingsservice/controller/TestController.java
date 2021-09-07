package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/settings/test")
public class TestController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("/health")
    public String health() {
        return "Healthy";
    }

    // teeeeest
    @GetMapping("/add/{id}")
    public void test_add(@PathVariable Long id) {

        settingsService.add(Settings.builder().userId(id).build());
    }

    @GetMapping("/get/{id}")
    public Settings test_get(@PathVariable Long id) {

        //settingsService.go(id);
        return settingsService.getSettingsByUserId(id);

    }

    @PostMapping("/change/{id}")
    public void test_change(@PathVariable Long id) {

        Settings settings = settingsService.getSettingsByUserId(id);
        settingsService.remove(settings);
        settings.setUserId(322L);
        settingsService.add(settings);
    }
}