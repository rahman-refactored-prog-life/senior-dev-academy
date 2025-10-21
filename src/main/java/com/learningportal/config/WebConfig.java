package com.learningportal.config;

import com.learningportal.interceptor.PerformanceMonitoringInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web Configuration
 * 
 * Configures web-related components including interceptors
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PerformanceMonitoringInterceptor performanceMonitoringInterceptor;

    public WebConfig(PerformanceMonitoringInterceptor performanceMonitoringInterceptor) {
        this.performanceMonitoringInterceptor = performanceMonitoringInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(performanceMonitoringInterceptor)
                .addPathPatterns("/api/**", "/modules/**")
                .excludePathPatterns("/actuator/**", "/swagger-ui/**", "/v3/api-docs/**");
    }
}