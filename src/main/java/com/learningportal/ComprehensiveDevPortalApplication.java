package com.learningportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main Spring Boot Application Class
 * 
 * This is the entry point for our Comprehensive Developer Portal.
 * It demonstrates several Spring Boot concepts:
 * - @SpringBootApplication: Combines @Configuration, @EnableAutoConfiguration, and @ComponentScan
 * - @EnableCaching: Enables Spring's caching abstraction
 * - @EnableJpaAuditing: Enables JPA auditing for automatic timestamp management
 */
@SpringBootApplication
@EnableCaching
@EnableJpaAuditing
public class ComprehensiveDevPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComprehensiveDevPortalApplication.class, args);
    }
}