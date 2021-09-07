package com.kideya.photocatcherservice.service.provider;

import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.repository.ImageRepository;
import com.kideya.photocatcherservice.service.provider.finder.ByDateFinder;
import com.kideya.photocatcherservice.service.provider.finder.ByGroupIdFinder;
import com.kideya.photocatcherservice.service.provider.finder.ByTagFinder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;
    private final UserGroupService userGroupService;

    public ImageServiceImpl(ImageRepository imageRepository, UserGroupService userGroupService) {
        this.imageRepository = imageRepository;
        this.userGroupService = userGroupService;
    }

    @Override
    public List<Image> getByGroupId(Long userId, Long groupId) {
        if (userGroupService.isUserInGroup(userId, groupId)) {
            return new ByGroupIdFinder(groupId).find(imageRepository);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Image> getByDate(Long userId, LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null && toDate == null) {
            throw new IllegalArgumentException();
        }

        if (toDate == null) {
            toDate = LocalDate.now();
        }

        List<Long> groupIds = userGroupService.getGroupIdsByUserId(userId);

        return new ByDateFinder(groupIds, fromDate, toDate).find(imageRepository);
    }

    @Override
    public List<Image> getByTag(Long userId, String tagValue) {
        if (tagValue == null) {
            throw new IllegalArgumentException();
        }

        List<Long> groupIds = userGroupService.getGroupIdsByUserId(userId);

        return new ByTagFinder(groupIds, tagValue).find(imageRepository);
    }
}
