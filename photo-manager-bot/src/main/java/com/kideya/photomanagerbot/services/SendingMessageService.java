package com.kideya.photomanagerbot.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SendingMessageService {

    @Autowired
    private EurekaClient eurekaClient;

    private final RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<T> sendPost(String serviceName, String url, Object object, Class<T> responseClass) {
        String service_url = prepareUrl(serviceName, url);

        ResponseEntity<T> result = restTemplate.postForEntity(service_url, object, responseClass);

        System.out.println(result.getStatusCode());

        return result;
    }

    private String prepareUrl(String serviceName, String url) {
        InstanceInfo instanceInfo = eurekaClient.getApplications().getInstancesByVirtualHostName(serviceName).get(0);
        return  "http://" + instanceInfo.getHostName() +":"+instanceInfo.getPort() + url;
    }

    public <T> ResponseEntity<T> sendGet(String serviceName, String url, Class<T> gettingEntityClass) {
        String service_url = prepareUrl(serviceName, url);
        return restTemplate.getForEntity(service_url, gettingEntityClass);
    }
}
