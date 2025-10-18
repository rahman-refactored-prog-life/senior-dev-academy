package com.learningportal.controller;

import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for data initialization and testing
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DataController {
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    
    /**
     * Initialize basic test data
     */
    @PostMapping("/init-data")
    public ResponseEntity<Map<String, Object>> initializeData() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Check if data already exists
            if (moduleRepository.count() > 0) {
                response.put("message", "Data already exists");
                response.put("moduleCount", moduleRepository.count());
                response.put("topicCount", topicRepository.count());
                return ResponseEntity.ok(response);
            }
            
            // Create Node.js Module
            LearningModule nodeModule = new LearningModule();
            nodeModule.setName("Node.js Fundamentals");
            nodeModule.setDescription("Complete Node.js mastery: 25 topics, 700+ interview questions");
            nodeModule.setDetailedContent("Master Node.js from core concepts to advanced patterns with hands-on projects including NASA Mission Control, SpaceX integration, and real-time applications.");
            nodeModule.setCategory(LearningModule.Category.JAVA_FUNDAMENTALS);
            nodeModule.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
            nodeModule.setEstimatedHours(50);
            nodeModule.setSortOrder(1);
            
            moduleRepository.save(nodeModule);
            
            // Create a sample Node.js topic
            Topic nodeBasics = new Topic();
            nodeBasics.setTitle("Node.js Core Concepts and Event Loop");
            nodeBasics.setDescription("Understanding Node.js architecture, V8 engine, and event-driven programming");
            nodeBasics.setContent("""
                <h2>Node.js Core Concepts</h2>
                <p>Node.js is a JavaScript runtime built on Chrome's V8 JavaScript engine. It uses an event-driven, non-blocking I/O model that makes it lightweight and efficient.</p>
                
                <h3>Key Features:</h3>
                <ul>
                    <li><strong>Event-Driven Architecture</strong>: Uses events and callbacks for asynchronous operations</li>
                    <li><strong>Non-Blocking I/O</strong>: Handles multiple operations concurrently</li>
                    <li><strong>Single-Threaded</strong>: Main event loop runs on a single thread</li>
                    <li><strong>Cross-Platform</strong>: Runs on Windows, macOS, and Linux</li>
                </ul>
                
                <h3>The Event Loop</h3>
                <p>The event loop is the heart of Node.js. It allows Node.js to perform non-blocking I/O operations despite JavaScript being single-threaded.</p>
                
                <h4>Event Loop Phases:</h4>
                <ol>
                    <li><strong>Timer Phase</strong>: Executes callbacks scheduled by setTimeout() and setInterval()</li>
                    <li><strong>Pending Callbacks</strong>: Executes I/O callbacks deferred to the next loop iteration</li>
                    <li><strong>Poll Phase</strong>: Fetches new I/O events and executes I/O related callbacks</li>
                    <li><strong>Check Phase</strong>: Executes setImmediate() callbacks</li>
                    <li><strong>Close Callbacks</strong>: Executes close event callbacks</li>
                </ol>
                """);
            nodeBasics.setCodeExamples("""
                [
                    {
                        "language": "javascript",
                        "code": "// Event Loop Example\\nconsole.log('Start');\\n\\nsetTimeout(() => {\\n  console.log('Timer 1');\\n}, 0);\\n\\nsetImmediate(() => {\\n  console.log('Immediate 1');\\n});\\n\\nprocess.nextTick(() => {\\n  console.log('Next Tick 1');\\n});\\n\\nconsole.log('End');\\n\\n// Output order:\\n// Start\\n// End\\n// Next Tick 1\\n// Timer 1\\n// Immediate 1",
                        "explanation": "Demonstrates the event loop execution order and priority of different callback types."
                    }
                ]
                """);
            nodeBasics.setKeyConcepts("""
                ["Event Loop", "Non-blocking I/O", "V8 Engine", "Single-threaded", "Asynchronous Programming", "Event-driven Architecture"]
                """);
            nodeBasics.setTopicType(Topic.TopicType.THEORY);
            nodeBasics.setEstimatedMinutes(180);
            nodeBasics.setSortOrder(1);
            nodeBasics.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
            nodeBasics.setModule(nodeModule);
            
            topicRepository.save(nodeBasics);
            
            // Create Java Module
            LearningModule javaModule = new LearningModule();
            javaModule.setName("Java Fundamentals");
            javaModule.setDescription("Complete Java programming from basics to advanced concepts");
            javaModule.setDetailedContent("Master Java programming language with comprehensive coverage of core concepts, object-oriented programming, collections, concurrency, and JVM internals.");
            javaModule.setCategory(LearningModule.Category.JAVA_FUNDAMENTALS);
            javaModule.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
            javaModule.setEstimatedHours(120);
            javaModule.setSortOrder(2);
            
            moduleRepository.save(javaModule);
            
            // Create Data Structures Module
            LearningModule dsModule = new LearningModule();
            dsModule.setName("Data Structures");
            dsModule.setDescription("Complete DS coverage: Maps, Sets, Hash Tables → Arrays, Trees, Graphs with 1500+ interview questions");
            dsModule.setDetailedContent("Comprehensive data structures course starting with missing fundamentals (Maps, Sets, Hash Tables) progressing through arrays, trees, graphs to advanced structures. Includes 1500+ interview questions with complexity analysis and real-world applications.");
            dsModule.setCategory(LearningModule.Category.DATA_STRUCTURES);
            dsModule.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
            dsModule.setEstimatedHours(60);
            dsModule.setSortOrder(3);
            
            moduleRepository.save(dsModule);
            
            response.put("message", "Test data initialized successfully");
            response.put("moduleCount", moduleRepository.count());
            response.put("topicCount", topicRepository.count());
            response.put("modules", moduleRepository.findByActiveTrueOrderBySortOrderAsc());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("error", "Failed to initialize data: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
    
    /**
     * Get data statistics
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("moduleCount", moduleRepository.count());
        stats.put("topicCount", topicRepository.count());
        stats.put("modules", moduleRepository.findByActiveTrueOrderBySortOrderAsc());
        return ResponseEntity.ok(stats);
    }
}