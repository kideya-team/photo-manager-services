package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.model.ServiceSettings;
import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiceSettingsService {
    @Autowired
    private SettingsService settingsService;

    @Autowired
    private SettingsRepository settingsRepository;

    public void addServiceSettingsToUser(Long userId, ServiceSettings serviceSettings) {
        Settings settings = settingsService.getSettingsByUserId(userId);
        List<ServiceSettings> serviceSettingsList = settings.getServiceSettings().stream()
                .filter(ss -> !ss.getServiceName().equals(serviceSettings.getServiceName()))
                .collect(Collectors.toList());

        serviceSettingsList.add(serviceSettings);

        settings.setServiceSettings(serviceSettingsList);
        settingsService.update(settings);
    }

    public List<ServiceSettings> getServiceSettingsByUser(Long userId) {
        return settingsService.getSettingsByUserId(userId).getServiceSettings();
    }

}
