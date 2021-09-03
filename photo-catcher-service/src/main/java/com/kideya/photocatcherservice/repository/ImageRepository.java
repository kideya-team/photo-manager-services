package com.kideya.photocatcherservice.repository;

import com.kideya.photocatcherservice.model.Image;

import java.util.List;

public interface ImageRepository {
    public List<Image> getAllImages();
}
