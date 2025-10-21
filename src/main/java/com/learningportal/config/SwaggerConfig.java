package com.learningportal.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI configuration for comprehensive API documentation.
 * 
 * This configuration provides:
 * - Complete API documentation with examples and response schemas
 * - Interactive API testing interface
 * - Automated documentation generation from code annotations
 * - Production-ready API documentation standards
 * 
 * @author Learning Portal Team
 * @version 1.0
 * @since 2024-01-01
 */
@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.application.name:Learning Portal}")
    private String applicationName;

    /**
     * Configures OpenAPI documentation with comprehensive metadata.
     * 
     * Features:
     * - Complete API information with contact details
     * - License information for legal compliance
     * - Server configuration for different environments
     * - Version tracking for API evolution
     * 
     * @return OpenAPI configuration with complete metadata
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Comprehensive Learning Portal API")
                        .version("1.0.0")
                        .description("""
                                # Comprehensive Learning Portal API Documentation
                                
                                ## Overview
                                This API provides comprehensive access to the world's most complete FAANG preparation platform,
                                covering zero-to-expert mastery across all major technology domains.
                                
                                ## Features
                                - **Complete Learning Modules**: Java, Node.js, React, Data Structures, Algorithms
                                - **8000+ Interview Questions**: Real questions from Amazon, Google, Microsoft, Meta, Apple
                                - **Interactive Learning**: Code execution, progress tracking, personalized learning paths
                                - **Enterprise-Grade**: Production-ready with comprehensive monitoring and security
                                
                                ## Authentication
                                All endpoints require proper authentication. Use the `/auth` endpoints to obtain access tokens.
                                
                                ## Rate Limiting
                                API requests are limited to 1000 requests per hour per user to ensure fair usage.
                                
                                ## Error Handling
                                All errors follow RFC 7807 Problem Details standard with comprehensive error information.
                                
                                ## Support
                                For technical support, contact the development team or refer to the troubleshooting guide.
                                """)
                        .contact(new Contact()
                                .name("Learning Portal Development Team")
                                .email("support@learningportal.com")
                                .url("https://github.com/learningportal/comprehensive-dev-portal"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.learningportal.com")
                                .description("Production Server"),
                        new Server()
                                .url("https://staging-api.learningportal.com")
                                .description("Staging Server")
                ));
    }
}