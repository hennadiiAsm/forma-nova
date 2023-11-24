package com.formanova.user.persistence;

import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;


@Configuration
public class PersistenceConfig {

    @Bean
    public Mutiny.SessionFactory sessionFactory(Environment environment) {

        String[] activeProfiles = environment.getActiveProfiles();
        String persistenceUnitName;
        if (Arrays.stream(activeProfiles).anyMatch("docker"::matches)) {
            persistenceUnitName = "postgresql-container";
        } else {
            persistenceUnitName = "postgresql-local";
        }
        return Persistence.createEntityManagerFactory(persistenceUnitName)
                .unwrap(Mutiny.SessionFactory.class);
    }

}
