package com.kideya.photocatcherservice.service.provider;

import com.kideya.photocatcherservice.dto.GroupsDto;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserGroupServiceImpl implements UserGroupService {
    @Autowired
    private EurekaClient eurekaClient;

    private String getServicePrefix(String serviceName) {
        InstanceInfo photoSettings = eurekaClient.getApplications().getInstancesBySecureVirtualHostName(serviceName).get(0);
        return "http://" + photoSettings.getHostName() + ":" + photoSettings.getPort();
    }

    private GroupsDto getGroupsByUserId(Long userId) {
        RestTemplate restTemplate = new RestTemplate();

        GroupsDto groups = restTemplate.getForObject(getServicePrefix("PHOTOSETTINGS") + "/api/settings/user/{userId}/groups", GroupsDto.class, userId);
        System.out.println(groups);

        return groups;
    }

    @Override
    public List<Long> getGroupIdsByUserId(Long userId) {
        GroupsDto groups = getGroupsByUserId(userId);

        return groups.getGroupIds();
    }

    @Override
    public boolean isUserInGroup(Long userId, Long groupId) {
        GroupsDto groups = getGroupsByUserId(userId);

        return groups.getGroupIds().contains(groupId);
    }
}
