package com.kideya.photocatcherservice.service.provider;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import com.kideya.photocatcherservice.service.provider.finder.PhotoFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    private final ImageRepository imageRepository;

    public ProviderServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Image> find(PhotoFinder photoFinder) {
        return photoFinder.find(imageRepository);
    }
}
