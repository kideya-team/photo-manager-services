package com.kideya.photosettingsservice.service;

import com.kideya.photosettingsservice.dto.GroupsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupsDtoService{

	public GroupsDto toGroupsDto(long userId, List<Long> groupIds) {
		return GroupsDto.builder()
				.userId(userId)
				.groupIds(groupIds)
				.build();
	}
}
