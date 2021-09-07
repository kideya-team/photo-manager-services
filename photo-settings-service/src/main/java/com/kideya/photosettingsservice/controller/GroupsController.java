package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.dto.GroupsDto;
import com.kideya.photosettingsservice.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
public class GroupsController {

	@Autowired
	private GroupsService groupsService;

	@GetMapping("/user/{id}/groups")
	public GroupsDto getGroupsByUserId(@PathVariable Long id) {
		return groupsService.getGroupsDtoByUserId(id);
	}

	@PostMapping("/user/{id}/groups/{groupId}")
	public void addGroupToUser(@PathVariable Long id, @PathVariable Long groupId) {
		groupsService.subscribeUserToGroup(id, groupId);
	}

	@GetMapping("/group/{groupId}")
	List<Long> getUserIdsByGroupId(@PathVariable Long groupId) {
		return groupsService.getUserIdsByGroupId(groupId);
	}
}
