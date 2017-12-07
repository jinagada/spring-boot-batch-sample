package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class BatchApplication extends SpringBootServletInitializer {
    public static void main(final String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }
}
