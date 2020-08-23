package com.example.sandbox.cloud.simpleapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"local","test"})
public class LocalConfig implements SimpleApplicationConfig {

    @Override
    public String getVariable(String variableName) {
        return "local";
    }
}
