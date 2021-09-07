package com.kideya.photosettingsservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class GroupsDto {
	private Long userId;
	private List<Long> groupIds;
}
