package com.kideya.photocatcherservice.service.catcher;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestSenderService {
    @Autowired
    private EurekaClient eurekaClient;

    @Autowired
    private final RestTemplate restTemplate = new RestTemplate();

    public <T> T sendGetRequest(String serviceName, String url, Class<T> responseType) {
        String serviceUrl = prepareUrl(serviceName, url);
        ResponseEntity<T> result = restTemplate.getForEntity(serviceUrl, responseType);
        return result.getBody();
    }

    public <T> T sendPostRequest(String serviceName, String url, Object requestObject, Class<T> responseType) {
        String serviceUrl = prepareUrl(serviceName, url);
        ResponseEntity<T> result = restTemplate.postForEntity(serviceUrl, requestObject, responseType);
        return result.getBody();
    }

    private String prepareUrl(String serviceName, String url) {
        InstanceInfo instanceInfo = eurekaClient.getApplications().getInstancesByVirtualHostName(serviceName).get(0);
        return "http://" + instanceInfo.getHostName() + ":" + instanceInfo.getPort() + url;
    }
}
