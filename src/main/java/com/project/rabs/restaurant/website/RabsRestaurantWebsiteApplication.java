package com.project.rabs.restaurant.website;

import io.mongock.runner.springboot.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class RabsRestaurantWebsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabsRestaurantWebsiteApplication.class, args);
    }
}