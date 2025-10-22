package com.learningportal.service;

import com.learningportal.dto.ModuleSummaryDto;
import com.learningportal.model.LearningModule;
import com.learningportal.repository.LearningModuleRepository;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service layer for Learning Module operations.
 * 
 * Provides business logic for managing learning modules including:
 * - CRUD operations with validation
 * - Search and filtering capabilities
 * - Caching for performance optimization
 * - Statistics and analytics
 */
@Service
@Transactional
public class LearningModuleService {

    private static final Logger log = LoggerFactory.getLogger(LearningModuleService.class);
    
    private final LearningModuleRepository moduleRepository;
    
    public LearningModuleService(LearningModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * Retrieve all learning modules with pagination and optional search.
     * 
     * @param pageable pagination parameters
     * @return paginated list of learning modules
     */
    @Cacheable(value = "learningModules", key = "#pageable.pageNumber + '_' + #pageable.pageSize")
    @Transactional(readOnly = true)
    public Page<LearningModule> getAllModules(Pageable pageable) {
        log.debug("Fetching all modules with pagination: {}", pageable);
        return moduleRepository.findAll(pageable);
    }

    /**
     * Search learning modules by name or description.
     * 
     * @param searchTerm search term to match against name or description
     * @param pageable pagination parameters
     * @return paginated list of matching learning modules
     */
    @Cacheable(value = "learningModules", key = "'search_' + #searchTerm + '_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    @Transactional(readOnly = true)
    public Page<LearningModule> searchModules(String searchTerm, Pageable pageable) {
        log.debug("Searching modules with term: '{}', pagination: {}", searchTerm, pageable);
        return moduleRepository.searchModules(searchTerm, pageable);
    }

    /**
     * Get a learning module by its ID.
     * 
     * @param id the module ID
     * @return the learning module
     * @throws EntityNotFoundException if module not found
     */
    @Cacheable(value = "learningModules", key = "#id")
    @Transactional(readOnly = true)
    public LearningModule getModuleById(Long id) {
        log.debug("Fetching module with ID: {}", id);
        return moduleRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Learning module not found with ID: " + id));
    }

    /**
     * Get a learning module by ID with topics loaded.
     * 
     * @param id the module ID
     * @return the learning module with topics
     * @throws EntityNotFoundException if module not found
     */
    @Cacheable(value = "learningModules", key = "'with_topics_' + #id")
    @Transactional(readOnly = true)
    public LearningModule getModuleByIdWithTopics(Long id) {
        log.debug("Fetching module with topics, ID: {}", id);
        return moduleRepository.findByIdWithTopics(id)
            .orElseThrow(() -> new EntityNotFoundException("Learning module not found with ID: " + id));
    }

    /**
     * Get a learning module by ID with interview questions loaded.
     * 
     * @param id the module ID
     * @return the learning module with questions
     * @throws EntityNotFoundException if module not found
     */
    @Cacheable(value = "learningModules", key = "'with_questions_' + #id")
    @Transactional(readOnly = true)
    public LearningModule getModuleByIdWithQuestions(Long id) {
        log.debug("Fetching module with questions, ID: {}", id);
        return moduleRepository.findByIdWithQuestions(id)
            .orElseThrow(() -> new EntityNotFoundException("Learning module not found with ID: " + id));
    }

    /**
     * Get modules by category.
     * 
     * @param category the module category
     * @return list of modules in the category
     */
    @Cacheable(value = "learningModules", key = "'category_' + #category")
    @Transactional(readOnly = true)
    public List<LearningModule> getModulesByCategory(LearningModule.Category category) {
        log.debug("Fetching modules by category: {}", category);
        return moduleRepository.findByCategoryOrderBySortOrderAsc(category);
    }

    /**
     * Get modules by difficulty level.
     * 
     * @param difficulty the difficulty level
     * @return list of modules with the specified difficulty
     */
    @Cacheable(value = "learningModules", key = "'difficulty_' + #difficulty")
    @Transactional(readOnly = true)
    public List<LearningModule> getModulesByDifficulty(LearningModule.DifficultyLevel difficulty) {
        log.debug("Fetching modules by difficulty: {}", difficulty);
        return moduleRepository.findByDifficultyLevelOrderBySortOrderAsc(difficulty);
    }

    /**
     * Get popular modules (most topics and questions).
     * 
     * @param pageable pagination parameters
     * @return list of popular modules
     */
    @Cacheable(value = "learningModules", key = "'popular_' + #pageable.pageSize")
    @Transactional(readOnly = true)
    public List<LearningModule> getPopularModules(Pageable pageable) {
        log.debug("Fetching popular modules with limit: {}", pageable.getPageSize());
        return moduleRepository.findPopularModules(pageable);
    }

    /**
     * Get comprehensive statistics about all learning modules.
     * 
     * @return statistics map with various metrics
     */
    @Cacheable(value = "learningStats", key = "'module_statistics'")
    @Transactional(readOnly = true)
    public Map<String, Object> getModulesStatistics() {
        log.debug("Fetching modules statistics");
        
        Object[] stats = moduleRepository.getModulesStatistics();
        
        return Map.of(
            "totalModules", stats[0] != null ? stats[0] : 0L,
            "totalTopics", stats[1] != null ? stats[1] : 0L,
            "totalQuestions", stats[2] != null ? stats[2] : 0L,
            "totalEstimatedHours", stats[3] != null ? stats[3] : 0L,
            "averageTopicsPerModule", stats[0] != null && (Long)stats[0] > 0 ? 
                ((Long)stats[1]) / ((Long)stats[0]) : 0L,
            "averageQuestionsPerModule", stats[0] != null && (Long)stats[0] > 0 ? 
                ((Long)stats[2]) / ((Long)stats[0]) : 0L
        );
    }

    /**
     * Get module summaries for overview display.
     * 
     * @return list of module summaries
     */
    @Cacheable(value = "learningModules", key = "'summaries'")
    @Transactional(readOnly = true)
    public List<ModuleSummaryDto> getModuleSummaries() {
        log.debug("Fetching module summaries");
        // For now, return empty list - this can be implemented later if needed
        return List.of();
    }

    /**
     * Create a new learning module.
     * 
     * @param module the module to create
     * @return the created module
     * @throws IllegalArgumentException if module with same name exists
     */
    @CacheEvict(value = {"learningModules", "learningStats"}, allEntries = true)
    public LearningModule createModule(LearningModule module) {
        log.info("Creating new module: {}", module.getName());
        
        // Validate unique name
        if (moduleRepository.existsByNameIgnoreCase(module.getName())) {
            throw new IllegalArgumentException("Module with name '" + module.getName() + "' already exists");
        }
        
        // Set creation timestamp if not set
        if (module.getCreatedAt() == null) {
            module.setCreatedAt(java.time.LocalDateTime.now());
        }
        
        LearningModule savedModule = moduleRepository.save(module);
        log.info("Created module with ID: {}", savedModule.getId());
        return savedModule;
    }

    /**
     * Update an existing learning module.
     * 
     * @param id the module ID
     * @param moduleUpdate the updated module data
     * @return the updated module
     * @throws EntityNotFoundException if module not found
     * @throws IllegalArgumentException if name conflicts with another module
     */
    @CacheEvict(value = {"learningModules", "learningStats"}, allEntries = true)
    public LearningModule updateModule(Long id, LearningModule moduleUpdate) {
        log.info("Updating module with ID: {}", id);
        
        LearningModule existingModule = getModuleById(id);
        
        // Check for name conflicts (excluding current module)
        if (!existingModule.getName().equalsIgnoreCase(moduleUpdate.getName()) &&
            moduleRepository.existsByNameIgnoreCase(moduleUpdate.getName())) {
            throw new IllegalArgumentException("Module with name '" + moduleUpdate.getName() + "' already exists");
        }
        
        // Update fields
        existingModule.setName(moduleUpdate.getName());
        existingModule.setDescription(moduleUpdate.getDescription());
        existingModule.setCategory(moduleUpdate.getCategory());
        existingModule.setDifficultyLevel(moduleUpdate.getDifficultyLevel());
        existingModule.setEstimatedHours(moduleUpdate.getEstimatedHours());
        existingModule.setSortOrder(moduleUpdate.getSortOrder());
        existingModule.setUpdatedAt(java.time.LocalDateTime.now());
        
        LearningModule savedModule = moduleRepository.save(existingModule);
        log.info("Updated module: {}", savedModule.getName());
        return savedModule;
    }

    /**
     * Delete a learning module.
     * 
     * @param id the module ID
     * @throws EntityNotFoundException if module not found
     */
    @CacheEvict(value = {"learningModules", "learningStats"}, allEntries = true)
    public void deleteModule(Long id) {
        log.info("Deleting module with ID: {}", id);
        
        if (!moduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Learning module not found with ID: " + id);
        }
        
        moduleRepository.deleteById(id);
        log.info("Deleted module with ID: {}", id);
    }

    /**
     * Check if a module exists by name (case-insensitive).
     * 
     * @param name the module name
     * @return true if module exists
     */
    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        return moduleRepository.existsByNameIgnoreCase(name);
    }
}