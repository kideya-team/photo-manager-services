package com.kideya.photocatcherservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupsDto {
    private Long userId;
    private List<Long> groupIds;
}
