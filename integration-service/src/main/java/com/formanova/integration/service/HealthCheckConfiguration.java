package com.formanova.integration.service;

import com.formanova.integration.client.UserServiceClient;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HealthCheckConfiguration {

    @Bean
    ReactiveHealthContributor coreServices(UserServiceClient integration) {

        final Map<String, ReactiveHealthIndicator> registry = new HashMap<>();

        registry.put("user", integration::getUserServiceHealth);

        return CompositeReactiveHealthContributor.fromMap(registry);
    }

}
