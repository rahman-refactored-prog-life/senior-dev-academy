package com.learningportal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * System Design Controller
 * 
 * Provides endpoints for system design content, patterns, and interview questions.
 */
@RestController
@RequestMapping("/system-design")
@Tag(name = "System Design", description = "System design content and interview preparation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SystemDesignController {
    
    @Operation(
        summary = "Get system design curriculum overview",
        description = "Returns comprehensive system design learning path and topics"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "System design curriculum retrieved successfully")
    })
    @GetMapping("/curriculum")
    public ResponseEntity<Map<String, Object>> getSystemDesignCurriculum() {
        Map<String, Object> curriculum = new HashMap<>();
        
        curriculum.put("title", "System Design Mastery - Senior Engineer Level");
        curriculum.put("description", "Complete system design curriculum for FAANG senior engineer interviews");
        curriculum.put("totalTopics", 15);
        curriculum.put("estimatedHours", 200);
        curriculum.put("difficultyLevel", "Expert");
        curriculum.put("prerequisites", Arrays.asList(
            "Data Structures & Algorithms",
            "Database Design",
            "Networking Fundamentals",
            "Distributed Systems Basics"
        ));
        
        List<Map<String, Object>> topics = Arrays.asList(
            createTopicMap("Scalability Fundamentals", "Load balancing, horizontal vs vertical scaling", 20),
            createTopicMap("Database Design", "SQL vs NoSQL, sharding, replication", 25),
            createTopicMap("Caching Strategies", "Redis, Memcached, CDN, cache patterns", 15),
            createTopicMap("Message Queues", "Kafka, RabbitMQ, pub/sub patterns", 18),
            createTopicMap("Microservices Architecture", "Service decomposition, API design", 22),
            createTopicMap("Distributed Systems", "CAP theorem, consistency, availability", 30),
            createTopicMap("Real-time Systems", "WebSockets, server-sent events, polling", 20),
            createTopicMap("Security & Authentication", "OAuth, JWT, rate limiting", 15),
            createTopicMap("Monitoring & Logging", "Metrics, alerting, distributed tracing", 12),
            createTopicMap("Case Studies", "Design Twitter, Netflix, Uber, Instagram", 40)
        );
        
        curriculum.put("topics", topics);
        curriculum.put("interviewQuestions", 50);
        curriculum.put("practiceProjects", Arrays.asList(
            "Design a URL Shortener",
            "Build a Chat System",
            "Create a Video Streaming Service",
            "Design a Social Media Feed"
        ));
        
        return ResponseEntity.ok(curriculum);
    }
    
    @Operation(
        summary = "Get system design patterns",
        description = "Returns common system design patterns and their use cases"
    )
    @GetMapping("/patterns")
    public ResponseEntity<Map<String, Object>> getSystemDesignPatterns() {
        Map<String, Object> patterns = new HashMap<>();
        
        patterns.put("title", "System Design Patterns");
        patterns.put("description", "Essential patterns for scalable system design");
        
        List<Map<String, Object>> patternList = Arrays.asList(
            createPatternMap("Load Balancer", "Distribute traffic across multiple servers", 
                           Arrays.asList("Round Robin", "Least Connections", "IP Hash")),
            createPatternMap("Database Sharding", "Partition data across multiple databases",
                           Arrays.asList("Horizontal Sharding", "Vertical Sharding", "Directory-based")),
            createPatternMap("Caching", "Store frequently accessed data in fast storage",
                           Arrays.asList("Browser Cache", "CDN", "Application Cache", "Database Cache")),
            createPatternMap("Message Queue", "Asynchronous communication between services",
                           Arrays.asList("Point-to-Point", "Publish-Subscribe", "Request-Reply")),
            createPatternMap("Circuit Breaker", "Prevent cascading failures in distributed systems",
                           Arrays.asList("Closed", "Open", "Half-Open")),
            createPatternMap("API Gateway", "Single entry point for all client requests",
                           Arrays.asList("Routing", "Authentication", "Rate Limiting", "Monitoring"))
        );
        
        patterns.put("patterns", patternList);
        return ResponseEntity.ok(patterns);
    }
    
    @Operation(
        summary = "Get system design interview questions",
        description = "Returns common system design interview questions with guidance"
    )
    @GetMapping("/interview-questions")
    public ResponseEntity<Map<String, Object>> getInterviewQuestions() {
        Map<String, Object> questions = new HashMap<>();
        
        questions.put("title", "System Design Interview Questions");
        questions.put("description", "Real questions asked at top tech companies");
        
        List<Map<String, Object>> questionList = Arrays.asList(
            createQuestionMap("Design a URL Shortener (like bit.ly)", "Amazon, Google", "Medium",
                            Arrays.asList("URL encoding", "Database design", "Caching", "Analytics")),
            createQuestionMap("Design a Chat System (like WhatsApp)", "Meta, Microsoft", "Hard",
                            Arrays.asList("Real-time messaging", "Message delivery", "Group chats", "Push notifications")),
            createQuestionMap("Design a Video Streaming Service (like Netflix)", "Netflix, Amazon", "Hard",
                            Arrays.asList("Content delivery", "Video encoding", "Recommendation system", "Global distribution")),
            createQuestionMap("Design a Social Media Feed (like Twitter)", "Meta, Twitter", "Hard",
                            Arrays.asList("Timeline generation", "Fan-out strategies", "Content ranking", "Real-time updates")),
            createQuestionMap("Design a Search Engine (like Google)", "Google, Microsoft", "Expert",
                            Arrays.asList("Web crawling", "Indexing", "Ranking algorithms", "Query processing")),
            createQuestionMap("Design a Ride Sharing Service (like Uber)", "Amazon, Uber", "Hard",
                            Arrays.asList("Location services", "Matching algorithm", "Real-time tracking", "Payment processing"))
        );
        
        questions.put("questions", questionList);
        questions.put("totalQuestions", questionList.size());
        
        return ResponseEntity.ok(questions);
    }
    
    @Operation(
        summary = "Get system design numbers and estimates",
        description = "Returns important numbers and estimates for system design calculations"
    )
    @GetMapping("/numbers")
    public ResponseEntity<Map<String, Object>> getSystemDesignNumbers() {
        Map<String, Object> numbers = new HashMap<>();
        
        numbers.put("title", "System Design Numbers");
        numbers.put("description", "Important numbers every engineer should know");
        
        Map<String, Object> latencyNumbers = new HashMap<>();
        latencyNumbers.put("L1 cache reference", "0.5 ns");
        latencyNumbers.put("Branch mispredict", "5 ns");
        latencyNumbers.put("L2 cache reference", "7 ns");
        latencyNumbers.put("Mutex lock/unlock", "25 ns");
        latencyNumbers.put("Main memory reference", "100 ns");
        latencyNumbers.put("Compress 1K bytes with Zippy", "10,000 ns");
        latencyNumbers.put("Send 1 KB over 1 Gbps network", "10,000 ns");
        latencyNumbers.put("Read 4 KB randomly from SSD", "150,000 ns");
        latencyNumbers.put("Read 1 MB sequentially from memory", "250,000 ns");
        latencyNumbers.put("Round trip within same datacenter", "500,000 ns");
        latencyNumbers.put("Read 1 MB sequentially from SSD", "1,000,000 ns");
        latencyNumbers.put("Disk seek", "10,000,000 ns");
        latencyNumbers.put("Read 1 MB sequentially from 1 Gbps", "10,000,000 ns");
        latencyNumbers.put("Read 1 MB sequentially from disk", "30,000,000 ns");
        latencyNumbers.put("Send packet CA->Netherlands->CA", "150,000,000 ns");
        
        Map<String, Object> availabilityNumbers = new HashMap<>();
        availabilityNumbers.put("99%", "3.65 days downtime per year");
        availabilityNumbers.put("99.9%", "8.77 hours downtime per year");
        availabilityNumbers.put("99.99%", "52.6 minutes downtime per year");
        availabilityNumbers.put("99.999%", "5.26 minutes downtime per year");
        
        Map<String, Object> capacityNumbers = new HashMap<>();
        capacityNumbers.put("QPS for typical web server", "1,000");
        capacityNumbers.put("QPS for SQL database", "1,000");
        capacityNumbers.put("QPS for NoSQL database", "10,000");
        capacityNumbers.put("QPS for cache server", "100,000 - 1,000,000");
        
        numbers.put("latency", latencyNumbers);
        numbers.put("availability", availabilityNumbers);
        numbers.put("capacity", capacityNumbers);
        
        return ResponseEntity.ok(numbers);
    }
    
    // Helper methods
    private Map<String, Object> createTopicMap(String title, String description, int estimatedMinutes) {
        Map<String, Object> topic = new HashMap<>();
        topic.put("title", title);
        topic.put("description", description);
        topic.put("estimatedMinutes", estimatedMinutes);
        return topic;
    }
    
    private Map<String, Object> createPatternMap(String name, String description, List<String> types) {
        Map<String, Object> pattern = new HashMap<>();
        pattern.put("name", name);
        pattern.put("description", description);
        pattern.put("types", types);
        return pattern;
    }
    
    private Map<String, Object> createQuestionMap(String question, String companies, String difficulty, List<String> keyTopics) {
        Map<String, Object> questionMap = new HashMap<>();
        questionMap.put("question", question);
        questionMap.put("companies", companies);
        questionMap.put("difficulty", difficulty);
        questionMap.put("keyTopics", keyTopics);
        return questionMap;
    }
}