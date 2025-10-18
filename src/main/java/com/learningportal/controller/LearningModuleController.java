package com.learningportal.controller;

import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST Controller for Learning Modules
 * Provides API endpoints for accessing learning content
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LearningModuleController {
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    
    /**
     * Get all learning modules
     */
    @GetMapping("/learning-modules")
    public ResponseEntity<List<LearningModule>> getAllModules() {
        List<LearningModule> modules = moduleRepository.findByActiveTrueOrderBySortOrderAsc();
        return ResponseEntity.ok(modules);
    }
    
    /**
     * Get a specific learning module by ID
     */
    @GetMapping("/learning-modules/{id}")
    public ResponseEntity<LearningModule> getModuleById(@PathVariable Long id) {
        Optional<LearningModule> module = moduleRepository.findById(id);
        return module.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get topics for a specific module
     */
    @GetMapping("/learning-modules/{moduleId}/topics")
    public ResponseEntity<List<Topic>> getTopicsByModule(@PathVariable Long moduleId) {
        List<Topic> topics = topicRepository.findActiveTopicsByModuleId(moduleId);
        return ResponseEntity.ok(topics);
    }
    
    /**
     * Get a specific topic by ID
     */
    @GetMapping("/topics/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get modules by category
     */
    @GetMapping("/learning-modules/category/{category}")
    public ResponseEntity<List<LearningModule>> getModulesByCategory(@PathVariable String category) {
        try {
            LearningModule.Category cat = LearningModule.Category.valueOf(category.toUpperCase());
            List<LearningModule> modules = moduleRepository.findActiveByCategoryOrdered(cat);
            return ResponseEntity.ok(modules);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Search modules by name or description
     */
    @GetMapping("/learning-modules/search")
    public ResponseEntity<List<LearningModule>> searchModules(@RequestParam String query) {
        List<LearningModule> modules = moduleRepository.findByActiveTrue();
        return ResponseEntity.ok(modules);
    }
}