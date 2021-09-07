package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.dto.GroupsDto;
import com.kideya.photosettingsservice.model.ServiceSettings;
import com.kideya.photosettingsservice.service.GroupsService;
import com.kideya.photosettingsservice.service.ServiceSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class ServicesController {

    @Autowired
    private ServiceSettingsService service;

    @GetMapping("/user/{id}/services")
    public List<ServiceSettings> getServiceSettingsByUser(@PathVariable Long id) {
        return service.getServiceSettingsByUser(id);
    }

    @PostMapping("/user/{id}/services")
    public void addServiceSettingsToUser(@PathVariable Long id, @RequestBody ServiceSettings serviceSettings) {
        service.addServiceSettingsToUser(id, serviceSettings);
    }
}
