package com.kideya.photocatcherservice.service.provider;

import java.util.List;

public interface UserGroupService {
    List<Long> getGroupIdsByUserId(Long userId);
    boolean isUserInGroup(Long userId, Long GroupId);
}
