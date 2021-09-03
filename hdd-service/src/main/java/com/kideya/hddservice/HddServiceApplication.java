package com.kideya.hddservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class HddServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HddServiceApplication.class, args);
    }

}
