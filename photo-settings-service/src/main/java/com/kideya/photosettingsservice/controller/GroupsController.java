package com.kideya.photosettingsservice.controller;

import com.kideya.photosettingsservice.dto.GroupsDto;
import com.kideya.photosettingsservice.service.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings/user")
public class GroupsController {

	@Autowired
	private GroupsService groupsService;

	@GetMapping("/{id}/groups")
	public GroupsDto getGroupsByUserId(@PathVariable long id) {
		return groupsService.getGroupsDtoByUserId(id);
	}

	@PutMapping("/{id}/groups/{groupId}")
	public void addGroupToUser(@PathVariable long id, @PathVariable long groupId) {
		groupsService.subscribeUserToGroup(id, groupId);
	}
}
