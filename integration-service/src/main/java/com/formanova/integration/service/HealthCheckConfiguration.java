package se.magnus.microservices.composite.product;

import java.util.LinkedHashMap;
import java.util.Map;

import com.formanova.integration.service.IntegrationService;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthCheckConfiguration {


    @Bean
    ReactiveHealthContributor coreServices(IntegrationService integration) {

        final Map<String, ReactiveHealthIndicator> registry = new LinkedHashMap<>();

        registry.put("user", integration::getUserServiceHealth);

        return CompositeReactiveHealthContributor.fromMap(registry);
    }

}
