package com.learningportal.repository;

import com.learningportal.model.LearningModule;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.QueryHint;

import java.util.List;
import java.util.Optional;

@Repository
@Tag(name = "Learning Module Repository", description = "Data access layer for learning modules")
public interface LearningModuleRepository extends JpaRepository<LearningModule, Long> {

    /**
     * Find modules by category
     */
    List<LearningModule> findByCategoryOrderBySortOrderAsc(LearningModule.Category category);

    /**
     * Find modules by difficulty level
     */
    List<LearningModule> findByDifficultyLevelOrderBySortOrderAsc(LearningModule.DifficultyLevel difficultyLevel);

    /**
     * Find modules by name containing (case-insensitive search)
     */
    Page<LearningModule> findByNameContainingIgnoreCaseOrderBySortOrderAsc(String name, Pageable pageable);

    /**
     * Find modules by description containing (case-insensitive search)
     */
    Page<LearningModule> findByDescriptionContainingIgnoreCaseOrderBySortOrderAsc(String description, Pageable pageable);

    /**
     * Search modules by name or description
     */
    @Query("SELECT m FROM LearningModule m WHERE " +
           "LOWER(m.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(m.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "ORDER BY m.sortOrder ASC")
    Page<LearningModule> searchModules(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find modules with topic count
     */
    @Query("SELECT m FROM LearningModule m LEFT JOIN FETCH m.topics WHERE m.id = :id")
    Optional<LearningModule> findByIdWithTopics(@Param("id") Long id);

    /**
     * Find modules with interview questions count
     */
    @Query("SELECT m FROM LearningModule m LEFT JOIN FETCH m.interviewQuestions WHERE m.id = :id")
    Optional<LearningModule> findByIdWithQuestions(@Param("id") Long id);

    /**
     * Find all modules with their topics and questions (for complete data)
     */
    @Query("SELECT DISTINCT m FROM LearningModule m " +
           "LEFT JOIN FETCH m.topics " +
           "LEFT JOIN FETCH m.interviewQuestions " +
           "ORDER BY m.sortOrder ASC")
    List<LearningModule> findAllWithTopicsAndQuestions();

    /**
     * Get modules statistics
     */
    @Query("SELECT " +
           "COUNT(m) as totalModules, " +
           "SUM(SIZE(m.topics)) as totalTopics, " +
           "SUM(SIZE(m.interviewQuestions)) as totalQuestions, " +
           "SUM(m.estimatedHours) as totalHours " +
           "FROM LearningModule m")
    Object[] getModulesStatistics();

    /**
     * Find modules by category and difficulty
     */
    List<LearningModule> findByCategoryAndDifficultyLevelOrderBySortOrderAsc(
        LearningModule.Category category, 
        LearningModule.DifficultyLevel difficultyLevel
    );

    /**
     * Find modules with estimated hours range
     */
    @Query("SELECT m FROM LearningModule m WHERE m.estimatedHours BETWEEN :minHours AND :maxHours ORDER BY m.sortOrder ASC")
    List<LearningModule> findByEstimatedHoursBetween(@Param("minHours") Integer minHours, @Param("maxHours") Integer maxHours);

    /**
     * Get popular modules (with most topics and questions)
     */
    @Query("SELECT m FROM LearningModule m WHERE SIZE(m.topics) > 0 AND SIZE(m.interviewQuestions) > 0 ORDER BY (SIZE(m.topics) + SIZE(m.interviewQuestions)) DESC")
    List<LearningModule> findPopularModules(Pageable pageable);

    /**
     * Check if module exists by name
     */
    boolean existsByNameIgnoreCase(String name);

    /**
     * Find modules ordered by sort order
     */
    List<LearningModule> findAllByOrderBySortOrderAsc();

    /**
     * Count modules by category
     */
    long countByCategory(LearningModule.Category category);

    /**
     * Count modules by difficulty level
     */
    long countByDifficultyLevel(LearningModule.DifficultyLevel difficultyLevel);

    /**
     * Find module by name (case-insensitive)
     */
    Optional<LearningModule> findByNameIgnoreCase(String name);

    /**
     * Performance-optimized queries with batch fetching
     */
    
    /**
     * Find modules with batch fetching for better performance
     */
    @Query("SELECT m FROM LearningModule m ORDER BY m.sortOrder ASC")
    @QueryHints({
        @QueryHint(name = "org.hibernate.fetchSize", value = "50"),
        @QueryHint(name = "org.hibernate.readOnly", value = "true")
    })
    List<LearningModule> findAllOptimized();

    /**
     * Find modules by category with optimized fetching
     */
    @Query("SELECT m FROM LearningModule m WHERE m.category = :category ORDER BY m.sortOrder ASC")
    @QueryHints({
        @QueryHint(name = "org.hibernate.fetchSize", value = "25"),
        @QueryHint(name = "org.hibernate.cacheable", value = "true")
    })
    List<LearningModule> findByCategoryOptimized(@Param("category") LearningModule.Category category);

    /**
     * Batch update sort orders for performance
     */
    @org.springframework.data.jpa.repository.Modifying
    @Query("UPDATE LearningModule m SET m.sortOrder = :sortOrder WHERE m.id = :id")
    int updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    /**
     * Get lightweight module summaries for performance
     */
    @Query("SELECT new com.learningportal.dto.ModuleSummaryDto(m.id, m.name, m.category, m.difficultyLevel, m.estimatedHours) " +
           "FROM LearningModule m ORDER BY m.sortOrder ASC")
    List<Object[]> findModuleSummaries();

    /**
     * Count total estimated hours efficiently
     */
    @Query("SELECT COALESCE(SUM(m.estimatedHours), 0) FROM LearningModule m")
    Integer getTotalEstimatedHours();

    /**
     * Find modules with pagination and sorting optimized
     */
    @Query(value = "SELECT m FROM LearningModule m ORDER BY m.sortOrder ASC",
           countQuery = "SELECT COUNT(m) FROM LearningModule m")
    Page<LearningModule> findAllOptimizedPaged(Pageable pageable);
}