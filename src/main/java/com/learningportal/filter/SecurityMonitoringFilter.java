package com.learningportal.filter;

import com.learningportal.service.SecurityMonitoringService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Filter for monitoring security events and API usage
 */
@Component
@Order(2)
public class SecurityMonitoringFilter implements Filter {

    private final SecurityMonitoringService securityMonitoringService;

    public SecurityMonitoringFilter(SecurityMonitoringService securityMonitoringService) {
        this.securityMonitoringService = securityMonitoringService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String clientIp = getClientIpAddress(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        String endpoint = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        
        long startTime = System.currentTimeMillis();

        try {
            // Check for suspicious IP
            if (securityMonitoringService.isSuspiciousIp(clientIp)) {
                securityMonitoringService.logSuspiciousActivity("BLOCKED_SUSPICIOUS_IP", 
                    clientIp, "Request from suspicious IP blocked");
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            // Detect suspicious patterns
            detectSuspiciousPatterns(httpRequest, clientIp);

            chain.doFilter(request, response);

        } finally {
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Log API usage
            securityMonitoringService.logApiUsage(clientIp, endpoint, method, 
                httpResponse.getStatus(), responseTime);

            // Log security events for certain status codes
            if (httpResponse.getStatus() == 401) {
                securityMonitoringService.logSecurityEvent("UNAUTHORIZED_ACCESS", 
                    clientIp, userAgent, endpoint, "Unauthorized access attempt");
            } else if (httpResponse.getStatus() == 403) {
                securityMonitoringService.logSecurityEvent("FORBIDDEN_ACCESS", 
                    clientIp, userAgent, endpoint, "Forbidden access attempt");
            }
        }
    }

    private void detectSuspiciousPatterns(HttpServletRequest request, String clientIp) {
        String userAgent = request.getHeader("User-Agent");
        String endpoint = request.getRequestURI();
        
        // Detect potential SQL injection attempts
        String queryString = request.getQueryString();
        if (queryString != null && containsSqlInjectionPatterns(queryString)) {
            securityMonitoringService.logSuspiciousActivity("SQL_INJECTION_ATTEMPT", 
                clientIp, "Potential SQL injection in query: " + queryString);
        }

        // Detect potential XSS attempts
        if (containsXssPatterns(endpoint) || (queryString != null && containsXssPatterns(queryString))) {
            securityMonitoringService.logSuspiciousActivity("XSS_ATTEMPT", 
                clientIp, "Potential XSS attempt detected");
        }

        // Detect bot-like behavior
        if (userAgent == null || userAgent.isEmpty() || containsBotPatterns(userAgent)) {
            securityMonitoringService.logSecurityEvent("BOT_DETECTED", 
                clientIp, userAgent, endpoint, "Potential bot activity");
        }
    }

    private boolean containsSqlInjectionPatterns(String input) {
        String lowerInput = input.toLowerCase();
        return lowerInput.contains("union select") || 
               lowerInput.contains("drop table") || 
               lowerInput.contains("insert into") || 
               lowerInput.contains("delete from") ||
               lowerInput.contains("' or '1'='1") ||
               lowerInput.contains("' or 1=1") ||
               lowerInput.contains("--");
    }

    private boolean containsXssPatterns(String input) {
        String lowerInput = input.toLowerCase();
        return lowerInput.contains("<script") || 
               lowerInput.contains("javascript:") || 
               lowerInput.contains("onload=") || 
               lowerInput.contains("onerror=") ||
               lowerInput.contains("alert(") ||
               lowerInput.contains("document.cookie");
    }

    private boolean containsBotPatterns(String userAgent) {
        if (userAgent == null) return true;
        
        String lowerUserAgent = userAgent.toLowerCase();
        return lowerUserAgent.contains("bot") || 
               lowerUserAgent.contains("crawler") || 
               lowerUserAgent.contains("spider") ||
               lowerUserAgent.contains("scraper") ||
               userAgent.length() < 10;
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
}