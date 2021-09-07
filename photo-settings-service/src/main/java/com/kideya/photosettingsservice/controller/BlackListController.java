package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings/")
public class BlackListController {

    @Autowired
    private SettingsService settingsService;

    @GetMapping("user/{id}/blacklist")
    public List<Long> getAllBannedUsers(@PathVariable Long id) {
        return settingsService.getSettingsByUserId(id).getConstraintsSettings().getBannedUserIds();
    }

    @PostMapping("user/{id}/blacklist/add/{bannedId}")
    public void addBannedUser(@PathVariable Long id, @PathVariable Long bannedId) {
        Settings settings = settingsService.getSettingsByUserId(id);
        if (!settings.getConstraintsSettings().getBannedUserIds().contains(id)) {
            settings.getConstraintsSettings().getBannedUserIds().add(bannedId);
        }
        settingsService.update(settings);
    }

}
