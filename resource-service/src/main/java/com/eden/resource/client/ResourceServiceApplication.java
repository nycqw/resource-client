package com.eden.resource.client;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class ResourceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServiceApplication.class, args);
    }

}
