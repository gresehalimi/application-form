package com.example.applicationform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ApplyingFormApplication {

    public static void main(String[] args) {

        SpringApplication.run(ApplyingFormApplication.class, args);

    }
}

