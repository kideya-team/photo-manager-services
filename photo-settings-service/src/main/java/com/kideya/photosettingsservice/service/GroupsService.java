package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.dto.GroupsDto;
import com.kideya.photosettingsservice.model.Settings;
import com.kideya.photosettingsservice.repository.SettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupsService {
    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private GroupsDtoService groupsDtoService;

    @Autowired
    private SettingsService settingsService;

    public GroupsDto getGroupsDtoByUserId(Long id) {
        return groupsDtoService.toGroupsDto(id, getGroupIdListByUserId(id));
    }

    private List<Long> getGroupIdListByUserId(Long id) {
        Settings settings = settingsRepository.findByUserId(id);
        return settings.getConstraintsSettings().getGroupIds();
    }

    public void addGroupToUser(Long userId, Long groupId) {
        Settings settings = settingsRepository.findByUserId(userId);

        if (settings == null) {
            settingsService.register(userId);
            settings = settingsRepository.findByUserId(userId);
        }

        List<Long> groups = settings.getConstraintsSettings().getGroupIds();
        if (!groups.contains(groupId)) {
            groups.add(groupId);
        }
        settingsService.update(settings);
    }


    public List<Long> getUserIdsByGroupId(Long groupId) {
        return settingsRepository.findAll().stream()
                .filter(settings -> {
                    List<Long> groupIds = settings.getConstraintsSettings().getGroupIds();
                    return groupIds.contains(groupId);
                })
                .map(Settings::getUserId)
                .collect(Collectors.toList());
    }

    public void removeGroupFromUser(Long userId, Long groupId) {
        Settings settings = settingsRepository.findByUserId(userId);
        List<Long> groups = settings.getConstraintsSettings().getGroupIds();
        groups.remove(groupId);
        settingsService.update(settings);
    }

}
