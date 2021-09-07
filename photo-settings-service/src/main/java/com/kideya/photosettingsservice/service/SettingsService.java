package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings getSettingsByUserId(Long id) {
        return settingsRepository.findByUserId(id);
    }

    public void add(Settings settings) {
        settingsRepository.insert(settings);
    }

    public void remove(Settings settings) {
        settingsRepository.delete(settings);
    }

    public void update(Settings settings) {
        settingsRepository.save(settings);
    }

    public void register(Long userId) {
        Settings settings = new Settings();
        settings.setUserId(userId);
        settingsRepository.save(settings);
    }

}
