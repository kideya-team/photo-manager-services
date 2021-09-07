package com.kideya.photocatcherservice.repository;

import com.kideya.photocatcherservice.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageRepository extends MongoRepository<Image, String> {
    List<Image> findByUserId(Long userId);
    List<Image> findByGroupId(Long groupId);
}
