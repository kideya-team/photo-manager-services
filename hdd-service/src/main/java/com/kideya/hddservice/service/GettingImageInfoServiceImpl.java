package com.kideya.hddservice.service;

import com.kideya.hddservice.utils.ServiceName;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GettingImageInfoServiceImpl implements GettingImageInfoService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    private String getServicePrefix(String serviceName) {
        InstanceInfo photoSettings = eurekaClient.getApplications().getInstancesBySecureVirtualHostName(serviceName).get(0);
        return "http://" + photoSettings.getHostName() + ":" + photoSettings.getPort();

    }

    @Override
    public String getImagePath(String imageId) {
        return restTemplate.getForObject(getServicePrefix(ServiceName.PHOTO_BOT) + "/api/image/{imageId}", String.class, imageId);
    }
}
