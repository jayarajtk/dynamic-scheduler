package com.jay.dynamicscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DynamicSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicSchedulerApplication.class, args);
    }

}