package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.dto.GroupsDto;
import com.kideya.photosettingsservice.model.GroupSettings;
import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
