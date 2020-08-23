package com.example.sandbox.cloud.simpleapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

@Configuration
@Profile("kubernetes")
@RequiredArgsConstructor
public class KubernetesConfig implements SimpleApplicationConfig {

    @Autowired
    private final Environment env;

    @Override
    public String getVariable(String variableName) {
        return env.getProperty(variableName);
    }
}
