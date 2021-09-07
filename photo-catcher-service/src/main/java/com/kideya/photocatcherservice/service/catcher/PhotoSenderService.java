package com.kideya.photocatcherservice.service.catcher;

import com.kideya.photocatcherservice.dto.ServiceSettingsDto;
import com.kideya.photocatcherservice.model.Image;
import com.kideya.photocatcherservice.dto.ServicePhotoInfoDto;
import com.netflix.discovery.shared.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class PhotoSenderService {
    @Autowired
    private RequestSenderService requestSenderService;

    public void send(List<Pair<Long, ServiceSettingsDto>> usersAndSettings, Image image) {
        for (var userAndSetting : usersAndSettings) {
            ServicePhotoInfoDto spi = ServicePhotoInfoDto.builder()
                    .imageId(image.getId())
                    .userId(userAndSetting.first())
                    .params(userAndSetting.second().getParams())
                    .build();
            sendRequestToService(spi, userAndSetting.second().getServiceName());
        }
    }

    private void sendRequestToService(ServicePhotoInfoDto servicePhotoInfo, String serviceName) {
        requestSenderService.sendPostRequest(serviceName.toUpperCase(Locale.ROOT),
                "/api/" + serviceName.toLowerCase(Locale.ROOT) + "/photo",
                servicePhotoInfo,
                Object.class);
    }
}