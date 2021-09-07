package com.kideya.photocatcherservice.service.catcher;

import com.kideya.photocatcherservice.dto.ServicePhotoInfoDto;
import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.dto.ServiceSettingsDto;
import com.netflix.discovery.shared.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServicesGetterService {

    private final static String SETTINGS_SERVICE_NAME = "PHOTOSETTINGS";

    @Autowired
    private RequestSenderService requestSenderService;


    public List<Pair<Long, ServiceSettingsDto>> getServicesToSend(Image image) {
        long imageOwnerId = image.getUserId();
        long imageGgroupId = image.getGroupId();

        List<Pair<Long, ServiceSettingsDto>> settingServices = new ArrayList<>();
        List<Long> users = getUsersByGroupId(imageGgroupId);
        for (var user : users) {
            List<Long> blackListOfUser = getBlackListOfUser(user);
            if (user == imageOwnerId || blackListOfUser.contains(imageOwnerId)) {
                continue;
            }

            getServicesOfUser(user).stream()
                    .filter(ServiceSettingsDto::isActive)
                    .forEach(service -> settingServices.add(new Pair<>(user, service)));
        }

        return  settingServices;
    }

    private List<Long> getUsersByGroupId(long groupId) {
        return Arrays.asList(requestSenderService.sendGetRequest(SETTINGS_SERVICE_NAME,
                "/api/settings/group/" + groupId,
                Long[].class));


    }

    private List<Long> getBlackListOfUser(long userId) {
        return Arrays.asList(requestSenderService.sendGetRequest(SETTINGS_SERVICE_NAME,
                "/api/settings/user/" + userId + "/blackList",
                Long[].class));
    }

    private List<ServiceSettingsDto> getServicesOfUser(long userId) {
        return Arrays.asList(requestSenderService.sendGetRequest(SETTINGS_SERVICE_NAME,
                "/api/settings/user/" + userId + "/services",
                ServiceSettingsDto[].class));
    }
}