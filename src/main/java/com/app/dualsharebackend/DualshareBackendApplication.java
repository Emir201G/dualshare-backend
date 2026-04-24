package com.app.dualsharebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class DualshareBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DualshareBackendApplication.class, args);
    }

}
