package com.kideya.photoproviderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PhotoProviderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoProviderServiceApplication.class, args);
    }

}
