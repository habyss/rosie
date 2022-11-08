package com.rosie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProvideWeatherApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProvideWeatherApplication.class, args);
    }
}