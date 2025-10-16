package com.learningportal.controller;

import com.learningportal.model.LearningModule;
import com.learningportal.service.LearningModuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Learning Module REST Controller - API endpoints for learning content management
 * 
 * Advanced features inspired by top learning platforms:
 * - Module recommendations based on user progress
 * - Learning path optimization
 * - Content difficulty progression
 * - Module statistics and analytics
 * - Search and filtering capabilities
 */
@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class LearningModuleController {
    
    private final LearningModuleService moduleService;
    
    /**
     * Get all active learning modules with enhanced metadata
     * GET /api/modules
     */
    @GetMapping
    public ResponseEntity<List<ModuleResponse>> getAllModules(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String difficulty,
            @RequestParam(required = false) String search) {
        
        log.info("GET /api/modules - category: {}, difficulty: {}, search: {}", category, difficulty, search);
        
        List<LearningModule> modules;
        
        if (search != null && !search.trim().isEmpty()) {
            modules = moduleService.searchModules(search);
        } else if (category != null) {
            LearningModule.Category cat = LearningModule.Category.valueOf(category.toUpperCase());
            modules = moduleService.getModulesByCategory(cat);
        } else if (difficulty != null) {
            LearningModule.DifficultyLevel diff = LearningModule.DifficultyLevel.valueOf(difficulty.toUpperCase());
            modules = moduleService.getModulesByDifficulty(diff);
        } else {
            modules = moduleService.getAllActiveModules();
        }
        
        List<ModuleResponse> response = modules.stream()
            .map(this::convertToModuleResponse)
            .toList();
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get module by ID with full details including topics
     * GET /api/modules/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DetailedModuleResponse> getModuleById(@PathVariable Long id) {
        log.info("GET /api/modules/{} - Fetching module with topics", id);
        
        Optional<LearningModule> moduleOpt = moduleService.findModuleWithTopics(id);
        
        if (moduleOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        LearningModule module = moduleOpt.get();
        DetailedModuleResponse response = convertToDetailedModuleResponse(module);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get recommended learning path for progressive skill building
     * GET /api/modules/recommended-path
     */
    @GetMapping("/recommended-path")
    public ResponseEntity<LearningPathResponse> getRecommendedLearningPath() {
        log.info("GET /api/modules/recommended-path - Generating personalized learning path");
        
        List<LearningModule> recommendedModules = moduleService.getRecommendedLearningPath();
        
        LearningPathResponse response = new LearningPathResponse(
            "Recommended Learning Path for Senior Developer Roles",
            "Optimized progression from fundamentals to advanced concepts",
            recommendedModules.stream().map(this::convertToModuleResponse).toList(),
            moduleService.getTotalEstimatedHours()
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Get modules by category with statistics (inspired by Coursera's category pages)
     * GET /api/modules/category/{category}
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<CategoryModulesResponse> getModulesByCategory(@PathVariable String category) {
        log.info("GET /api/modules/category/{} - Fetching modules with statistics", category);
        
        try {
            LearningModule.Category cat = LearningModule.Category.valueOf(category.toUpperCase());
            List<LearningModuleService.ModuleWithStats> modulesWithStats = moduleService.getModulesWithStats(cat);
            
            CategoryModulesResponse response = new CategoryModulesResponse(
                cat.getDisplayName(),
                getCategory Description(cat),
                modulesWithStats.stream()
                    .map(mws -> convertToModuleWithStatsResponse(mws))
                    .toList()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid category: {}", category);
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get learning statistics and analytics
     * GET /api/modules/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<LearningStatsResponse> getLearningStats() {
        log.info("GET /api/modules/stats - Fetching learning statistics");
        
        List<LearningModule> allModules = moduleService.getAllActiveModules();
        
        // Calculate statistics
        long totalModules = allModules.size();
        int totalHours = moduleService.getTotalEstimatedHours();
        
        // Group by difficulty
        long beginnerCount = allModules.stream()
            .filter(m -> m.getDifficultyLevel() == LearningModule.DifficultyLevel.BEGINNER)
            .count();
        long intermediateCount = allModules.stream()
            .filter(m -> m.getDifficultyLevel() == LearningModule.DifficultyLevel.INTERMEDIATE)
            .count();
        long advancedCount = allModules.stream()
            .filter(m -> m.getDifficultyLevel() == LearningModule.DifficultyLevel.ADVANCED)
            .count();
        long expertCount = allModules.stream()
            .filter(m -> m.getDifficultyLevel() == LearningModule.DifficultyLevel.EXPERT)
            .count();
        
        // Group by category
        var categoryStats = allModules.stream()
            .collect(java.util.stream.Collectors.groupingBy(
                LearningModule::getCategory,
                java.util.stream.Collectors.counting()
            ));
        
        LearningStatsResponse response = new LearningStatsResponse(
            totalModules,
            totalHours,
            beginnerCount,
            intermediateCount,
            advancedCount,
            expertCount,
            categoryStats
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Create new learning module (Admin only)
     * POST /api/modules
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ModuleResponse>> createModule(@Valid @RequestBody CreateModuleRequest request) {
        log.info("POST /api/modules - Creating new module: {}", request.getName());
        
        try {
            LearningModule module = moduleService.createModule(
                request.getName(),
                request.getDescription(),
                request.getDetailedContent(),
                request.getCategory(),
                request.getDifficultyLevel(),
                request.getEstimatedHours()
            );
            
            ModuleResponse moduleResponse = convertToModuleResponse(module);
            ApiResponse<ModuleResponse> response = new ApiResponse<>(
                true,
                "Module created successfully",
                moduleResponse
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create module: {}", e.getMessage());
            
            ApiResponse<ModuleResponse> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Update existing module (Admin only)
     * PUT /api/modules/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> updateModule(
            @PathVariable Long id,
            @Valid @RequestBody UpdateModuleRequest request) {
        
        log.info("PUT /api/modules/{} - Updating module", id);
        
        try {
            LearningModule module = moduleService.updateModule(
                id,
                request.getName(),
                request.getDescription(),
                request.getDetailedContent(),
                request.getEstimatedHours()
            );
            
            ModuleResponse moduleResponse = convertToModuleResponse(module);
            ApiResponse<ModuleResponse> response = new ApiResponse<>(
                true,
                "Module updated successfully",
                moduleResponse
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to update module: {}", e.getMessage());
            
            ApiResponse<ModuleResponse> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Toggle module status (Admin only)
     * PUT /api/modules/{id}/toggle-status
     */
    @PutMapping("/{id}/toggle-status")
    public ResponseEntity<ApiResponse<Void>> toggleModuleStatus(@PathVariable Long id) {
        log.info("PUT /api/modules/{}/toggle-status - Toggling module status", id);
        
        try {
            moduleService.toggleModuleStatus(id);
            
            ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Module status updated successfully",
                null
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to toggle module status: {}", e.getMessage());
            
            ApiResponse<Void> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Helper methods for response conversion
    
    private ModuleResponse convertToModuleResponse(LearningModule module) {
        return new ModuleResponse(
            module.getId(),
            module.getName(),
            module.getDescription(),
            module.getCategory().getDisplayName(),
            module.getDifficultyLevel().getDisplayName(),
            module.getEstimatedHours(),
            module.getSortOrder(),
            module.getTopicCount(),
            calculateCompletionRate(module), // This would integrate with user progress
            module.getCreatedAt(),
            module.getUpdatedAt()
        );
    }
    
    private DetailedModuleResponse convertToDetailedModuleResponse(LearningModule module) {
        return new DetailedModuleResponse(
            module.getId(),
            module.getName(),
            module.getDescription(),
            module.getDetailedContent(),
            module.getCategory().getDisplayName(),
            module.getDifficultyLevel().getDisplayName(),
            module.getEstimatedHours(),
            module.getSortOrder(),
            module.getTopics().stream()
                .map(topic -> new TopicSummary(
                    topic.getId(),
                    topic.getTitle(),
                    topic.getDescription(),
                    topic.getTopicType().getDisplayName(),
                    topic.getEstimatedMinutes(),
                    topic.getSortOrder()
                ))
                .toList(),
            calculateCompletionRate(module),
            getPrerequisites(module),
            getNextRecommendations(module),
            module.getCreatedAt(),
            module.getUpdatedAt()
        );
    }
    
    private ModuleWithStatsResponse convertToModuleWithStatsResponse(LearningModuleService.ModuleWithStats mws) {
        LearningModule module = mws.getModule();
        return new ModuleWithStatsResponse(
            convertToModuleResponse(module),
            mws.getTopicCount(),
            calculateAverageRating(module),
            getEnrollmentCount(module)
        );
    }
    
    // Helper methods for enhanced features
    
    private double calculateCompletionRate(LearningModule module) {
        // This would integrate with UserProgressService
        // For now, return a placeholder
        return 0.0;
    }
    
    private List<String> getPrerequisites(LearningModule module) {
        // Logic to determine prerequisites based on difficulty and category
        return switch (module.getDifficultyLevel()) {
            case BEGINNER -> List.of();
            case INTERMEDIATE -> List.of("Complete beginner modules in " + module.getCategory().getDisplayName());
            case ADVANCED -> List.of("Complete intermediate modules", "Strong foundation in core concepts");
            case EXPERT -> List.of("Complete advanced modules", "Professional experience recommended");
        };
    }
    
    private List<String> getNextRecommendations(LearningModule module) {
        // Logic to recommend next modules
        return List.of(
            "Continue with advanced topics in " + module.getCategory().getDisplayName(),
            "Explore related interview questions",
            "Practice coding exercises"
        );
    }
    
    private double calculateAverageRating(LearningModule module) {
        // This would integrate with user ratings
        return 4.5; // Placeholder
    }
    
    private long getEnrollmentCount(LearningModule module) {
        // This would integrate with user progress tracking
        return 1250; // Placeholder
    }
    
    private String getCategoryDescription(LearningModule.Category category) {
        return switch (category) {
            case JAVA_FUNDAMENTALS -> "Master Java programming from basics to advanced concepts including OOP, collections, and concurrency.";
            case SPRING_FRAMEWORK -> "Learn the Spring ecosystem including Spring Boot, Security, Data, and Cloud technologies.";
            case REACT_DEVELOPMENT -> "Modern React development with hooks, context, performance optimization, and testing.";
            case DATA_STRUCTURES -> "Fundamental and advanced data structures with implementation and complexity analysis.";
            case ALGORITHMS -> "Algorithmic problem-solving techniques including sorting, searching, and dynamic programming.";
            case SYSTEM_DESIGN -> "Design scalable, distributed systems with focus on real-world architecture patterns.";
            case SQL_FUNDAMENTALS -> "Complete SQL mastery from basic queries to advanced database operations.";
            case NOSQL_DATABASES -> "Modern NoSQL databases including MongoDB, Redis, Cassandra, and DynamoDB.";
            case DATABASE_DESIGN -> "Database design principles, normalization, and optimization techniques.";
            case MICROSERVICES -> "Microservices architecture patterns, implementation, and best practices.";
            default -> "Comprehensive learning module for professional development.";
        };
    }
    
    // Response DTOs
    
    public static class ModuleResponse {
        private Long id;
        private String name;
        private String description;
        private String category;
        private String difficultyLevel;
        private Integer estimatedHours;
        private Integer sortOrder;
        private Integer topicCount;
        private Double completionRate;
        private java.time.LocalDateTime createdAt;
        private java.time.LocalDateTime updatedAt;
        
        public ModuleResponse(Long id, String name, String description, String category, 
                            String difficultyLevel, Integer estimatedHours, Integer sortOrder,
                            Integer topicCount, Double completionRate,
                            java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
            this.id = id;
            this.name = name;
            this.description = description;
            this.category = category;
            this.difficultyLevel = difficultyLevel;
            this.estimatedHours = estimatedHours;
            this.sortOrder = sortOrder;
            this.topicCount = topicCount;
            this.completionRate = completionRate;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
        }
        
        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getCategory() { return category; }
        public String getDifficultyLevel() { return difficultyLevel; }
        public Integer getEstimatedHours() { return estimatedHours; }
        public Integer getSortOrder() { return sortOrder; }
        public Integer getTopicCount() { return topicCount; }
        public Double getCompletionRate() { return completionRate; }
        public java.time.LocalDateTime getCreatedAt() { return createdAt; }
        public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
    }
    
    public static class DetailedModuleResponse extends ModuleResponse {
        private String detailedContent;
        private List<TopicSummary> topics;
        private List<String> prerequisites;
        private List<String> nextRecommendations;
        
        public DetailedModuleResponse(Long id, String name, String description, String detailedContent,
                                    String category, String difficultyLevel, Integer estimatedHours,
                                    Integer sortOrder, List<TopicSummary> topics, Double completionRate,
                                    List<String> prerequisites, List<String> nextRecommendations,
                                    java.time.LocalDateTime createdAt, java.time.LocalDateTime updatedAt) {
            super(id, name, description, category, difficultyLevel, estimatedHours, sortOrder,
                  topics.size(), completionRate, createdAt, updatedAt);
            this.detailedContent = detailedContent;
            this.topics = topics;
            this.prerequisites = prerequisites;
            this.nextRecommendations = nextRecommendations;
        }
        
        // Additional getters
        public String getDetailedContent() { return detailedContent; }
        public List<TopicSummary> getTopics() { return topics; }
        public List<String> getPrerequisites() { return prerequisites; }
        public List<String> getNextRecommendations() { return nextRecommendations; }
    }
    
    public static class TopicSummary {
        private Long id;
        private String title;
        private String description;
        private String topicType;
        private Integer estimatedMinutes;
        private Integer sortOrder;
        
        public TopicSummary(Long id, String title, String description, String topicType,
                          Integer estimatedMinutes, Integer sortOrder) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.topicType = topicType;
            this.estimatedMinutes = estimatedMinutes;
            this.sortOrder = sortOrder;
        }
        
        // Getters
        public Long getId() { return id; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getTopicType() { return topicType; }
        public Integer getEstimatedMinutes() { return estimatedMinutes; }
        public Integer getSortOrder() { return sortOrder; }
    }
    
    // Additional response classes for enhanced features...
    
    public static class LearningPathResponse {
        private String title;
        private String description;
        private List<ModuleResponse> modules;
        private Integer totalEstimatedHours;
        
        public LearningPathResponse(String title, String description, List<ModuleResponse> modules, Integer totalEstimatedHours) {
            this.title = title;
            this.description = description;
            this.modules = modules;
            this.totalEstimatedHours = totalEstimatedHours;
        }
        
        // Getters
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public List<ModuleResponse> getModules() { return modules; }
        public Integer getTotalEstimatedHours() { return totalEstimatedHours; }
    }
    
    public static class CategoryModulesResponse {
        private String categoryName;
        private String categoryDescription;
        private List<ModuleWithStatsResponse> modules;
        
        public CategoryModulesResponse(String categoryName, String categoryDescription, List<ModuleWithStatsResponse> modules) {
            this.categoryName = categoryName;
            this.categoryDescription = categoryDescription;
            this.modules = modules;
        }
        
        // Getters
        public String getCategoryName() { return categoryName; }
        public String getCategoryDescription() { return categoryDescription; }
        public List<ModuleWithStatsResponse> getModules() { return modules; }
    }
    
    public static class ModuleWithStatsResponse {
        private ModuleResponse module;
        private long topicCount;
        private double averageRating;
        private long enrollmentCount;
        
        public ModuleWithStatsResponse(ModuleResponse module, long topicCount, double averageRating, long enrollmentCount) {
            this.module = module;
            this.topicCount = topicCount;
            this.averageRating = averageRating;
            this.enrollmentCount = enrollmentCount;
        }
        
        // Getters
        public ModuleResponse getModule() { return module; }
        public long getTopicCount() { return topicCount; }
        public double getAverageRating() { return averageRating; }
        public long getEnrollmentCount() { return enrollmentCount; }
    }
    
    public static class LearningStatsResponse {
        private long totalModules;
        private int totalHours;
        private long beginnerModules;
        private long intermediateModules;
        private long advancedModules;
        private long expertModules;
        private java.util.Map<LearningModule.Category, Long> categoryStats;
        
        public LearningStatsResponse(long totalModules, int totalHours, long beginnerModules,
                                   long intermediateModules, long advancedModules, long expertModules,
                                   java.util.Map<LearningModule.Category, Long> categoryStats) {
            this.totalModules = totalModules;
            this.totalHours = totalHours;
            this.beginnerModules = beginnerModules;
            this.intermediateModules = intermediateModules;
            this.advancedModules = advancedModules;
            this.expertModules = expertModules;
            this.categoryStats = categoryStats;
        }
        
        // Getters
        public long getTotalModules() { return totalModules; }
        public int getTotalHours() { return totalHours; }
        public long getBeginnerModules() { return beginnerModules; }
        public long getIntermediateModules() { return intermediateModules; }
        public long getAdvancedModules() { return advancedModules; }
        public long getExpertModules() { return expertModules; }
        public java.util.Map<LearningModule.Category, Long> getCategoryStats() { return categoryStats; }
    }
    
    // Request DTOs
    
    public static class CreateModuleRequest {
        private String name;
        private String description;
        private String detailedContent;
        private LearningModule.Category category;
        private LearningModule.DifficultyLevel difficultyLevel;
        private Integer estimatedHours;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getDetailedContent() { return detailedContent; }
        public void setDetailedContent(String detailedContent) { this.detailedContent = detailedContent; }
        public LearningModule.Category getCategory() { return category; }
        public void setCategory(LearningModule.Category category) { this.category = category; }
        public LearningModule.DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
        public void setDifficultyLevel(LearningModule.DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }
        public Integer getEstimatedHours() { return estimatedHours; }
        public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
    }
    
    public static class UpdateModuleRequest {
        private String name;
        private String description;
        private String detailedContent;
        private Integer estimatedHours;
        
        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public String getDetailedContent() { return detailedContent; }
        public void setDetailedContent(String detailedContent) { this.detailedContent = detailedContent; }
        public Integer getEstimatedHours() { return estimatedHours; }
        public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }
    }
    
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        
        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        
        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public T getData() { return data; }
    }
}