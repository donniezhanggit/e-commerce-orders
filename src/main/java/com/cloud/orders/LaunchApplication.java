package com.cloud.orders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Launch class
 */
@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync
public class LaunchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaunchApplication.class, args);
    }
}
