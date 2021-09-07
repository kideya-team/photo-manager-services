package com.kideya.photosettingsservice.repository;

import com.kideya.photosettingsservice.model.Settings;
import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.data.repository.CrudRepository;


public interface SettingsRepository extends MongoRepository<Settings, Long> {
    Settings findByUserId(Long userId);
}