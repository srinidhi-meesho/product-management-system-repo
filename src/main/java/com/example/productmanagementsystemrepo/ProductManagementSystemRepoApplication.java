package com.example.productmanagementsystemrepo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableKafka
public class ProductManagementSystemRepoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductManagementSystemRepoApplication.class, args);
    }

}
