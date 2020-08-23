package com.example.sandbox.cloud.simpleapp;

import com.example.sandbox.cloud.simpleapp.config.SimpleApplicationConfig;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Configuration
@AllArgsConstructor
@EnableDiscoveryClient
public class SimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleApplication.class, args);
    }

    @Autowired
    private SimpleApplicationConfig simpleApplicationConfig;

    @GetMapping
    public String getDefault() {
        return "Hello from simple application - no param";
    }

    @GetMapping(value = "/{path}")
    public String getSingle(@PathVariable String path) {
        return "Hello from simple application<br/>\nOne parameter: " + path +
                ", corresponding value: " + simpleApplicationConfig.getVariable(path);
    }

}
