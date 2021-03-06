package com.kideya.photocatcherservice.service.provider.finder;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ByDateFinder implements PhotoFinder {
    private final List<Long> groupIds;
    private final LocalDate fromDate;
    private final LocalDate toDate;

    @Override
    public List<Image> find(ImageRepository imageRepository) {
        if (fromDate == null || toDate == null) {
            throw new IllegalArgumentException();
        }

        return groupIds.stream()
                .flatMap(groupId -> imageRepository.findByGroupId(groupId).stream())
                .filter(image -> image.getDate().isAfter(fromDate) && image.getDate().isBefore(toDate))
                .collect(Collectors.toList());
    }
}
