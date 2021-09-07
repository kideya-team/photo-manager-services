package com.kideya.photocatcherservice.service.catcher;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageSaverService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImageToDataBase(Image image) {
        imageRepository.insert(image);
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }
}