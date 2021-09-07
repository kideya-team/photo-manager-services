package com.kideya.photomanagerbot.model.settings_service;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
public class GroupsDto {
	private Long userId;
	private List<Long> groupIds;

	public String getGroupsString(){
		return groupIds.stream()
				.map(n -> String.valueOf(n))
				.collect(Collectors.joining("\n"));
	}
}
