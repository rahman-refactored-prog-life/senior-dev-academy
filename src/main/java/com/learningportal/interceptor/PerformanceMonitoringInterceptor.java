package com.learningportal.interceptor;

import com.learningportal.service.PerformanceMonitoringService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Performance Monitoring Interceptor
 * 
 * Automatically tracks API response times for all HTTP requests
 */
@Component
public class PerformanceMonitoringInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(PerformanceMonitoringInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "startTime";

    private final PerformanceMonitoringService performanceMonitoringService;

    public PerformanceMonitoringInterceptor(PerformanceMonitoringService performanceMonitoringService) {
        this.performanceMonitoringService = performanceMonitoringService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTRIBUTE, startTime);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                              Object handler, Exception ex) {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        if (startTime != null) {
            long responseTime = System.currentTimeMillis() - startTime;
            String endpoint = request.getMethod() + " " + request.getRequestURI();
            
            // Record the response time
            performanceMonitoringService.recordApiResponseTime(endpoint, responseTime);
            
            // Log slow requests (>1000ms)
            if (responseTime > 1000) {
                log.warn("Slow API request detected: {} took {}ms", endpoint, responseTime);
            }
        }
    }
}