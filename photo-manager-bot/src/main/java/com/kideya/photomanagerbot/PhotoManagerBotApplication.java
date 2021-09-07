package com.kideya.photomanagerbot;

import com.kideya.photomanagerbot.config.BotConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties(BotConfig.class)
public class PhotoManagerBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoManagerBotApplication.class, args);
    }

}
