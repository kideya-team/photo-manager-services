package com.kideya.photocatcherservice.service.catcher;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageSaverService {

    @Autowired
    private ImageRepository imageRepository;

    public void saveImageToDataBase(Image image) {
        Optional<Image> optional = imageRepository.findById(image.getId());
        if (optional.isEmpty()) {
            imageRepository.insert(image);
        }
    }

    public List<Image> getImages() {
        return imageRepository.findAll();
    }
}