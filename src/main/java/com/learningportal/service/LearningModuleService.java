package com.learningportal.service;

import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Learning Module Service - Business logic for learning content management
 * 
 * This service demonstrates:
 * - Caching strategies for performance optimization
 * - Complex business logic for learning path management
 * - Hierarchical data handling (modules -> topics)
 * - Performance considerations for educational content
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LearningModuleService {
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    
    /**
     * Get all active learning modules ordered by sort order
     * Cached for performance since this is frequently accessed
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "learningModules", key = "'all-active'")
    public List<LearningModule> getAllActiveModules() {
        log.debug("Fetching all active learning modules");
        return moduleRepository.findByActiveTrueOrderBySortOrderAsc();
    }
    
    /**
     * Get modules by category
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "learningModules", key = "#category.name()")
    public List<LearningModule> getModulesByCategory(LearningModule.Category category) {
        log.debug("Fetching modules for category: {}", category);
        return moduleRepository.findActiveByCategoryOrdered(category);
    }
    
    /**
     * Get modules by difficulty level
     */
    @Transactional(readOnly = true)
    public List<LearningModule> getModulesByDifficulty(LearningModule.DifficultyLevel difficulty) {
        log.debug("Fetching modules for difficulty: {}", difficulty);
        return moduleRepository.findByDifficultyLevel(difficulty);
    }
    
    /**
     * Find module by ID with topics
     */
    @Transactional(readOnly = true)
    public Optional<LearningModule> findModuleWithTopics(Long moduleId) {
        log.debug("Fetching module with topics for ID: {}", moduleId);
        
        Optional<LearningModule> moduleOpt = moduleRepository.findById(moduleId);
        if (moduleOpt.isPresent()) {
            LearningModule module = moduleOpt.get();
            // Eagerly load topics to avoid lazy loading issues
            List<Topic> topics = topicRepository.findActiveTopicsByModuleId(moduleId);
            module.setTopics(topics);
        }
        
        return moduleOpt;
    }
    
    /**
     * Create a new learning module
     */
    public LearningModule createModule(String name, String description, String detailedContent,
                                     LearningModule.Category category, 
                                     LearningModule.DifficultyLevel difficultyLevel,
                                     Integer estimatedHours) {
        log.info("Creating new learning module: {}", name);
        
        validateModuleInput(name, description, category, difficultyLevel);
        
        LearningModule module = new LearningModule();
        module.setName(name);
        module.setDescription(description);
        module.setDetailedContent(detailedContent);
        module.setCategory(category);
        module.setDifficultyLevel(difficultyLevel);
        module.setEstimatedHours(estimatedHours);
        module.setActive(true);
        
        // Set sort order to be last
        List<LearningModule> existingModules = moduleRepository.findByActiveTrueOrderBySortOrderAsc();
        module.setSortOrder(existingModules.size() + 1);
        
        LearningModule savedModule = moduleRepository.save(module);
        log.info("Successfully created module with ID: {}", savedModule.getId());
        
        return savedModule;
    }
    
    /**
     * Update an existing module
     */
    public LearningModule updateModule(Long moduleId, String name, String description, 
                                     String detailedContent, Integer estimatedHours) {
        log.info("Updating module ID: {}", moduleId);
        
        LearningModule module = moduleRepository.findById(moduleId)
            .orElseThrow(() -> new IllegalArgumentException("Module not found with ID: " + moduleId));
        
        validateModuleUpdateInput(name, description);
        
        module.setName(name);
        module.setDescription(description);
        module.setDetailedContent(detailedContent);
        module.setEstimatedHours(estimatedHours);
        
        LearningModule updatedModule = moduleRepository.save(module);
        log.info("Successfully updated module ID: {}", moduleId);
        
        return updatedModule;
    }
    
    /**
     * Reorder modules
     */
    public void reorderModules(List<Long> moduleIds) {
        log.info("Reordering modules: {}", moduleIds);
        
        for (int i = 0; i < moduleIds.size(); i++) {
            Long moduleId = moduleIds.get(i);
            Optional<LearningModule> moduleOpt = moduleRepository.findById(moduleId);
            
            if (moduleOpt.isPresent()) {
                LearningModule module = moduleOpt.get();
                module.setSortOrder(i + 1);
                moduleRepository.save(module);
            }
        }
        
        log.info("Successfully reordered {} modules", moduleIds.size());
    }
    
    /**
     * Activate/Deactivate module
     */
    public void toggleModuleStatus(Long moduleId) {
        log.info("Toggling status for module ID: {}", moduleId);
        
        LearningModule module = moduleRepository.findById(moduleId)
            .orElseThrow(() -> new IllegalArgumentException("Module not found with ID: " + moduleId));
        
        module.setActive(!module.getActive());
        moduleRepository.save(module);
        
        log.info("Module ID {} is now {}", moduleId, module.getActive() ? "active" : "inactive");
    }
    
    /**
     * Get total estimated learning hours across all active modules
     */
    @Transactional(readOnly = true)
    @Cacheable(value = "learningStats", key = "'total-hours'")
    public Integer getTotalEstimatedHours() {
        log.debug("Calculating total estimated hours");
        Integer total = moduleRepository.getTotalEstimatedHours();
        return total != null ? total : 0;
    }
    
    /**
     * Get learning path recommendations based on difficulty progression
     */
    @Transactional(readOnly = true)
    public List<LearningModule> getRecommendedLearningPath() {
        log.debug("Generating recommended learning path");
        
        // Get all active modules ordered by difficulty and sort order
        List<LearningModule> allModules = moduleRepository.findByActiveTrueOrderBySortOrderAsc();
        
        // Filter and order by difficulty progression: BEGINNER -> INTERMEDIATE -> ADVANCED -> EXPERT
        return allModules.stream()
            .sorted((m1, m2) -> {
                int difficultyCompare = m1.getDifficultyLevel().compareTo(m2.getDifficultyLevel());
                if (difficultyCompare != 0) {
                    return difficultyCompare;
                }
                return m1.getSortOrder().compareTo(m2.getSortOrder());
            })
            .toList();
    }
    
    /**
     * Get modules by category with statistics
     */
    @Transactional(readOnly = true)
    public List<ModuleWithStats> getModulesWithStats(LearningModule.Category category) {
        log.debug("Fetching modules with statistics for category: {}", category);
        
        List<LearningModule> modules = moduleRepository.findActiveByCategoryOrdered(category);
        
        return modules.stream()
            .map(module -> {
                long topicCount = topicRepository.countActiveTopicsByModule(module);
                return new ModuleWithStats(module, topicCount);
            })
            .toList();
    }
    
    /**
     * Search modules by name or description
     */
    @Transactional(readOnly = true)
    public List<LearningModule> searchModules(String searchTerm) {
        log.debug("Searching modules with term: {}", searchTerm);
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return getAllActiveModules();
        }
        
        String lowerSearchTerm = searchTerm.toLowerCase();
        
        return getAllActiveModules().stream()
            .filter(module -> 
                module.getName().toLowerCase().contains(lowerSearchTerm) ||
                module.getDescription().toLowerCase().contains(lowerSearchTerm)
            )
            .toList();
    }
    
    /**
     * Validation methods
     */
    private void validateModuleInput(String name, String description, 
                                   LearningModule.Category category, 
                                   LearningModule.DifficultyLevel difficultyLevel) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Module name cannot be empty");
        }
        
        if (name.length() > 100) {
            throw new IllegalArgumentException("Module name cannot exceed 100 characters");
        }
        
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Module description cannot be empty");
        }
        
        if (description.length() > 500) {
            throw new IllegalArgumentException("Module description cannot exceed 500 characters");
        }
        
        if (category == null) {
            throw new IllegalArgumentException("Module category is required");
        }
        
        if (difficultyLevel == null) {
            throw new IllegalArgumentException("Module difficulty level is required");
        }
    }
    
    private void validateModuleUpdateInput(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Module name cannot be empty");
        }
        
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Module description cannot be empty");
        }
    }
    
    /**
     * Inner class for module statistics
     */
    public static class ModuleWithStats {
        private final LearningModule module;
        private final long topicCount;
        
        public ModuleWithStats(LearningModule module, long topicCount) {
            this.module = module;
            this.topicCount = topicCount;
        }
        
        public LearningModule getModule() { return module; }
        public long getTopicCount() { return topicCount; }
    }
}