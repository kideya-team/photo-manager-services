package com.kideya.photomanagerbot.services;

import com.kideya.photomanagerbot.model.catcher_service.Image;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SendingMessageService {

    @Autowired
    private EurekaClient eurekaClient;

    private final RestTemplate restTemplate = new RestTemplate();

    public void send(String serviceName, String url, Object object) {
        String service_url = prepareUrl(serviceName, url);

        ResponseEntity<String> result = restTemplate.postForEntity(service_url, object, String.class);

        System.out.println(result.getStatusCode());
    }

    private String prepareUrl(String serviceName, String url) {
        InstanceInfo instanceInfo = eurekaClient.getApplications().getInstancesByVirtualHostName(serviceName).get(0);

        return  "http://" + instanceInfo.getHostName() +":"+instanceInfo.getPort() + url;
    }
}
