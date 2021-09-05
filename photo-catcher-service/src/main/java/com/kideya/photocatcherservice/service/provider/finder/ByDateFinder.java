package com.kideya.photocatcherservice.service.provider.finder;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ByDateFinder implements PhotoFinder {
    private final int userId;
    private final LocalDateTime fromDate;
    private final LocalDateTime toDate;

    @Override
    public List<Image> find(ImageRepository imageRepository) {
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException();
        }

        return imageRepository.findByUserId(userId).stream()
            .filter(image -> image.getDate().isAfter(fromDate) && image.getDate().isBefore(toDate))
            .collect(Collectors.toList());
    }
}
