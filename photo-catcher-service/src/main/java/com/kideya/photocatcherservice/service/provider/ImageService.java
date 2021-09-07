package com.kideya.photocatcherservice.service.provider;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.service.provider.finder.PhotoFinder;

import java.time.LocalDate;
import java.util.List;

public interface ImageService {
    List<Image> getByGroupId(Long userId, Long groupId);
    List<Image> getByDate(Long userId, LocalDate fromDate, LocalDate toDate);
    List<Image> getByTag(Long userId, String tagValue);
}
