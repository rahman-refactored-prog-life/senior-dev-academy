package com.learningportal.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Performance Monitoring Configuration
 * 
 * Configures metrics collection for API response times, database queries,
 * memory usage, and performance regression detection.
 */
@Configuration
public class PerformanceMonitoringConfig {

    /**
     * Timer for API response time monitoring
     */
    @Bean
    public Timer apiResponseTimer(MeterRegistry meterRegistry) {
        return Timer.builder("api.response.time")
            .description("API response time in milliseconds")
            .register(meterRegistry);
    }

    /**
     * Timer for database query execution monitoring
     */
    @Bean
    public Timer databaseQueryTimer(MeterRegistry meterRegistry) {
        return Timer.builder("database.query.time")
            .description("Database query execution time in milliseconds")
            .register(meterRegistry);
    }
}