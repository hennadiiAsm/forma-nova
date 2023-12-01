package com.formanova.user.persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.reactive.mutiny.Mutiny;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Map;


@Configuration
public class PersistenceConfig {

    @Bean
    public Mutiny.SessionFactory sessionFactory(Environment environment) throws IOException {

        String[] activeProfiles = environment.getActiveProfiles();
        String persistenceUnitName;
        EntityManagerFactory emf;
        if (Arrays.stream(activeProfiles).anyMatch("docker"::matches)) {
            persistenceUnitName = "postgresql-container";

            // It's required in order to avoid Netty DNS resolving bug
            String url = "jdbc:postgresql://" + InetAddress.getByName("postgres").getHostAddress() + ":5432/analysis";
            emf = Persistence.createEntityManagerFactory(persistenceUnitName, Map.of("jakarta.persistence.jdbc.url", url));
        } else {
            persistenceUnitName = "postgresql-local";
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
        return emf.unwrap(Mutiny.SessionFactory.class);
    }

}

