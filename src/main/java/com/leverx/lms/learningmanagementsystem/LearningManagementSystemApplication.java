package com.leverx.lms.learningmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class LearningManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningManagementSystemApplication.class, args);
        System.out.println(LocalDateTime.now());
    }

}
