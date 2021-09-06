package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings get(int id) {
        return settingsRepository.findByUserId(id);
    }

    public void add(Settings settings) {
        settingsRepository.insert(settings);
    }

    public Settings go(int id) {
        Settings set1 = new Settings();
        set1.setUserId(id);
        //settingsRepository.save(set1);
        settingsRepository.insert(set1);
        System.out.println("lek");
        set1.setUserId(229);
        return settingsRepository.findByUserId(id);
    }
}
