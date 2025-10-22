package com.learningportal.filter;

import com.learningportal.service.RateLimitingService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

/**
 * Filter for implementing rate limiting on API requests
 */
@Component
@Order(1)
public class RateLimitingFilter implements Filter {

    private final RateLimitingService rateLimitingService;

    // Rate limit configurations
    private static final int DEFAULT_MAX_REQUESTS = 1000;
    private static final int PREMIUM_MAX_REQUESTS = 5000;
    private static final Duration WINDOW_DURATION = Duration.ofHours(1);

    public RateLimitingFilter(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Skip rate limiting for certain paths
        String requestPath = httpRequest.getRequestURI();
        if (shouldSkipRateLimit(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        // Get client identifier (IP address or user ID)
        String clientId = getClientIdentifier(httpRequest);
        
        // Determine rate limit based on user tier
        int maxRequests = getUserTierLimit(httpRequest);
        
        // Check rate limit
        if (!rateLimitingService.isAllowed(clientId, maxRequests, WINDOW_DURATION)) {
            // Rate limit exceeded
            httpResponse.setStatus(429); // Too Many Requests
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(
                "{\"error\":\"Rate limit exceeded\",\"message\":\"Too many requests. Please try again later.\"}"
            );
            return;
        }

        // Add rate limit headers
        int remaining = rateLimitingService.getRemainingRequests(clientId, maxRequests);
        long resetTime = rateLimitingService.getResetTime(clientId);
        
        httpResponse.setHeader("X-RateLimit-Limit", String.valueOf(maxRequests));
        httpResponse.setHeader("X-RateLimit-Remaining", String.valueOf(remaining));
        httpResponse.setHeader("X-RateLimit-Reset", String.valueOf(resetTime));

        chain.doFilter(request, response);
    }

    private boolean shouldSkipRateLimit(String path) {
        return path.startsWith("/actuator/") || 
               path.startsWith("/swagger-ui/") || 
               path.startsWith("/api-docs/") ||
               path.equals("/favicon.ico");
    }

    private String getClientIdentifier(HttpServletRequest request) {
        // Try to get user ID from session/token first
        String userId = request.getHeader("X-User-ID");
        if (userId != null) {
            return "user:" + userId;
        }
        
        // Fall back to IP address
        String clientIp = getClientIpAddress(request);
        return "ip:" + clientIp;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    private int getUserTierLimit(HttpServletRequest request) {
        // Check for premium user header/token
        String userTier = request.getHeader("X-User-Tier");
        if ("premium".equalsIgnoreCase(userTier)) {
            return PREMIUM_MAX_REQUESTS;
        }
        
        return DEFAULT_MAX_REQUESTS;
    }
}