package com.learningportal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration
 * 
 * Configures Spring Security for the application:
 * - Password encoding with BCrypt
 * - HTTP security rules
 * - CORS configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    /**
     * Password encoder bean for secure password hashing
     * Uses BCrypt algorithm with strength 10
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    /**
     * Security filter chain configuration
     * Currently permits all requests for development
     * TODO: Add proper authentication and authorization in production
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for development
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow all requests for now
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin()) // Allow H2 console in iframe
            );
        
        return http.build();
    }
}
