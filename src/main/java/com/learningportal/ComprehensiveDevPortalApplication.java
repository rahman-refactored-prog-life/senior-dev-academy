package com.learningportal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableTransactionManagement
@OpenAPIDefinition(
    info = @Info(
        title = "FAANG Senior Developer Mastery Portal API",
        version = "1.0.0",
        description = """
            The most comprehensive learning portal API for FAANG senior developer preparation.
            
            Features:
            - 500+ comprehensive topics across Java, Node.js, React, System Design
            - 10,800+ real interview questions from top tech companies
            - Interactive learning modules with code examples
            - Progress tracking and analytics
            - Advanced search and filtering capabilities
            
            This API serves the complete curriculum for mastering:
            - Java (Fundamentals to Expert)
            - Node.js (25 comprehensive topics)
            - React (Advanced patterns and optimization)
            - Data Structures & Algorithms (All 30+ structures)
            - System Design (Scalable architectures)
            - Database Design (SQL/NoSQL optimization)
            - DevOps & Cloud (AWS Solutions Architect level)
            """,
        contact = @Contact(
            name = "FAANG Mastery Portal Team",
            email = "support@faangmastery.com",
            url = "https://faangmastery.com"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:9090/api/v1",
            description = "Development REST API Server"
        ),
        @Server(
            url = "https://api.faangmastery.com",
            description = "Production Server"
        )
    }
)
public class ComprehensiveDevPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComprehensiveDevPortalApplication.class, args);
        
        System.out.println("""
            
            🚀 FAANG Senior Developer Mastery Portal Started Successfully!
            
            📚 REST API Access Points:
            • API Base: http://localhost:9090/api/v1
            • Swagger UI: http://localhost:9090/api/v1/swagger-ui.html
            • API Docs: http://localhost:9090/api/v1/v3/api-docs
            • Actuator: http://localhost:9090/api/v1/actuator
            
            🎯 Features Available:
            • 500+ comprehensive learning topics
            • 10,800+ real interview questions
            • Interactive code examples
            • Progress tracking
            • Advanced search & filtering
            
            💡 Ready to master FAANG interviews!
            """);
    }
}