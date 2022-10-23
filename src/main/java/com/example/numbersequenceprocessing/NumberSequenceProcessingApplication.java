package com.example.numbersequenceprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class NumberSequenceProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberSequenceProcessingApplication.class, args);
    }

}
