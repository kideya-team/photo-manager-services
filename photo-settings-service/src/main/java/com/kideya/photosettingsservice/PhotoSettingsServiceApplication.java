package com.kideya.photosettingsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaClient
public class PhotoSettingsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoSettingsServiceApplication.class, args);
    }

}
