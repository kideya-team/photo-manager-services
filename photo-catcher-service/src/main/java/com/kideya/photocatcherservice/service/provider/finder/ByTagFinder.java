package com.kideya.photocatcherservice.service.provider.finder;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ByTagFinder implements PhotoFinder{
    private final int userId;
    private final String tagValue;

    @Override
    public List<Image> find(ImageRepository imageRepository) {
        return imageRepository.findByUserId(userId).stream()
                .filter(image -> image.getTags().stream()
                        .anyMatch(tag -> tag.getValue().equals(tagValue)))
                .collect(Collectors.toList());
    }
}
