package com.kideya.photomanagerbot.botapi.commands;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class GroupsCommand implements BotCommand {

    private final String name = "/groups";

    @Autowired
    private EurekaClient eurekaClient;

    @Override
    public BotApiMethod<?> runCommand(Update update) {
        InstanceInfo instanceInfo = eurekaClient.getApplications().getInstancesByVirtualHostName("TEST").get(0);

        RestTemplate restTemplate = new RestTemplateBuilder().build();

        String url = "http://" + instanceInfo.getHostName() +":"+instanceInfo.getPort() +"/test";
        System.out.println(url);
        restTemplate.getForEntity(url, String.class);
//        restTemplate.getForEntity("http://TEST:5555/test", String.class);
//        Integer answer = restTemplate.getForObject("url", int.class);
//        System.out.println(12);
        return null;
    }

    @Override
    public String getCommandName() {
        return name;
    }
}
