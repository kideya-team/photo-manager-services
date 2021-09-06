package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.dto.GroupsDto;
import com.kideya.photosettingsservice.model.GroupSettings;
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


	public GroupsDto getGroupsDtoByUserId(long id) {
		return groupsDtoService.toGroupsDto(id, getGroupIdListByUserId(id));
	}

	public List<Long> getGroupIdListByUserId(long id) {
		Settings settings = settingsRepository.findByUserId(id);
		return settings
				.getConstraintsSettings()
				.getGroupSettings()
				.stream()
				.map(GroupSettings::getGroupId)
				.collect(Collectors.toList());
	}

	public boolean isUserSubscribedToGroup(Long userId, long GroupId) {
		return getGroupIdListByUserId(userId).contains(GroupId);
	}

	public void subscribeUserToGroup(Long userId, long GroupId) {
		if (!isUserSubscribedToGroup(userId, GroupId)) {
			addGroupToUser(userId, GroupId);
		}
	}

	private void addGroupToUser(Long userId, long groupId) {
		Settings settings = settingsRepository.findByUserId(userId);
		List<GroupSettings> groupSettings = settings.getConstraintsSettings().getGroupSettings();
		groupSettings.add(
				GroupSettings
						.builder()
						.groupId(groupId)
						.groupName("")
						.isActive(true)
						.build()
		);
		settingsRepository.save(settings);
	}

}
