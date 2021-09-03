package com.kideya.photocatcherservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotoCatcherServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoCatcherServiceApplication.class, args);
    }

}
