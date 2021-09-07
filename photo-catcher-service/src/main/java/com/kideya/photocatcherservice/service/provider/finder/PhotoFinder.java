package com.kideya.photocatcherservice.service.provider.finder;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;

import java.util.List;

public interface PhotoFinder {
    List<Image> find(ImageRepository imageRepository);
}
