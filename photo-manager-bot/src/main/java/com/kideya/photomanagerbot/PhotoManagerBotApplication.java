package com.kideya.photomanagerbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotoManagerBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoManagerBotApplication.class, args);
    }

}
