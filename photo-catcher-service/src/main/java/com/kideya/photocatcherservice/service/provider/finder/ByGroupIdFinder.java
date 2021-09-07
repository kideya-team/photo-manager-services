package com.kideya.photocatcherservice.service.provider.finder;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ByGroupIdFinder implements PhotoFinder {
    private final Long groupId;

    @Override
    public List<Image> find(ImageRepository imageRepository) {
        return imageRepository.findByGroupId(groupId);
    }
}
