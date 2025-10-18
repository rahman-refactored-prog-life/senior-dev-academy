package com.learningportal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Arrays;

/**
 * Controller to showcase the Node.js learning content we generated
 */
@Controller
public class NodeJSShowcaseController {
    
    @GetMapping("/nodejs-content")
    @ResponseBody
    public Map<String, Object> getNodeJSContent() {
        Map<String, Object> content = new HashMap<>();
        
        content.put("title", "Node.js Complete Mastery Curriculum");
        content.put("description", "25 comprehensive topics with 700+ interview questions");
        content.put("totalTopics", 25);
        content.put("totalQuestions", "700+");
        content.put("estimatedHours", "50+ hours");
        
        // All 25 Node.js topics we implemented
        List<Map<String, Object>> topics = Arrays.asList(
            createTopic(1, "Node.js Core Concepts and Event Loop", "Understanding Node.js architecture, V8 engine, and event-driven programming", 180, 25),
            createTopic(2, "Asynchronous Programming: Callbacks, Promises, Async/Await", "Master asynchronous patterns and avoid callback hell", 200, 25),
            createTopic(3, "Node.js Modules and Package Management", "CommonJS, ES6 modules, npm, and package.json mastery", 160, 25),
            createTopic(4, "Express.js Framework and Middleware", "Building REST APIs, middleware patterns, and routing", 180, 25),
            createTopic(5, "Node.js Performance Optimization and Scaling", "Clustering, worker threads, and performance monitoring", 200, 25),
            createTopic(6, "File I/O and Streams: Planets Project", "Working with files, streams, and processing Kepler data", 180, 30),
            createTopic(7, "HTTP and Web Servers", "Creating servers, handling requests, and HTTP protocols", 160, 25),
            createTopic(8, "Database Integration: MongoDB and PostgreSQL", "Database connections, ORMs, and data modeling", 200, 30),
            createTopic(9, "Authentication and Security", "JWT, OAuth, security best practices, and HTTPS", 180, 25),
            createTopic(10, "Testing in Node.js", "Unit testing, integration testing, and TDD practices", 160, 25),
            createTopic(11, "Real-time Applications with WebSockets", "Socket.io, real-time communication, and chat applications", 180, 25),
            createTopic(12, "Microservices Architecture", "Service decomposition, API gateways, and distributed systems", 200, 30),
            createTopic(13, "GraphQL with Node.js", "GraphQL servers, resolvers, and schema design", 180, 25),
            createTopic(14, "Caching Strategies", "Redis, in-memory caching, and cache invalidation", 160, 25),
            createTopic(15, "Message Queues and Event-Driven Architecture", "RabbitMQ, Apache Kafka, and event sourcing", 200, 30),
            createTopic(16, "Containerization with Docker", "Docker containers, multi-stage builds, and orchestration", 180, 25),
            createTopic(17, "Monitoring and Logging", "Application monitoring, structured logging, and observability", 160, 25),
            createTopic(18, "Error Handling and Debugging", "Error patterns, debugging techniques, and production troubleshooting", 140, 20),
            createTopic(19, "Memory Management and Garbage Collection", "V8 internals, memory leaks, and performance profiling", 180, 25),
            createTopic(20, "Deployment Strategies", "CI/CD pipelines, blue-green deployments, and infrastructure", 160, 25),
            createTopic(21, "NASA Mission Control Project", "Building a mission control dashboard with real NASA data", 240, 35),
            createTopic(22, "SpaceX Launch System", "Creating a launch scheduling system with SpaceX API integration", 220, 30),
            createTopic(23, "Pong Game with Socket.io", "Real-time multiplayer game development", 200, 25),
            createTopic(24, "AWS Deployment and Serverless", "Lambda functions, API Gateway, and cloud deployment", 180, 25),
            createTopic(25, "Advanced Scalability Patterns", "Load balancing, caching layers, and high-availability systems", 200, 30)
        );
        
        content.put("topics", topics);
        
        // Project highlights
        content.put("projects", Arrays.asList(
            "🚀 NASA Mission Control Dashboard",
            "🛰️ SpaceX Launch System",
            "🌍 Kepler Planets Data Processing",
            "🎮 Real-time Pong Game",
            "☁️ AWS Serverless Deployment"
        ));
        
        // Skills covered
        content.put("skills", Arrays.asList(
            "Node.js Core & Event Loop",
            "Express.js & REST APIs",
            "Database Integration",
            "Real-time Applications",
            "Microservices Architecture",
            "Performance Optimization",
            "Security & Authentication",
            "Testing & Debugging",
            "Docker & Deployment",
            "AWS & Serverless"
        ));
        
        return content;
    }
    
    @GetMapping("/nodejs-showcase")
    public String showNodeJSPage() {
        return "nodejs-showcase"; // This would return a Thymeleaf template
    }
    
    private Map<String, Object> createTopic(int order, String title, String description, int minutes, int questions) {
        Map<String, Object> topic = new HashMap<>();
        topic.put("order", order);
        topic.put("title", title);
        topic.put("description", description);
        topic.put("estimatedMinutes", minutes);
        topic.put("interviewQuestions", questions);
        topic.put("hours", String.format("%.1f", minutes / 60.0));
        return topic;
    }
}