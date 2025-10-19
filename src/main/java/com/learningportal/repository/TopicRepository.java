package com.learningportal.repository;

import com.learningportal.model.Topic;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Tag(name = "Topic Repository", description = "Data access layer for learning topics")
public interface TopicRepository extends JpaRepository<Topic, Long> {

    /**
     * Find topics by module ID
     */
    List<Topic> findByModuleIdOrderBySortOrderAsc(Long moduleId);

    /**
     * Find topics by module ID with pagination
     */
    Page<Topic> findByModuleIdOrderBySortOrderAsc(Long moduleId, Pageable pageable);

    /**
     * Find topics by title containing (case-insensitive search)
     */
    Page<Topic> findByTitleContainingIgnoreCaseOrderBySortOrderAsc(String title, Pageable pageable);

    /**
     * Find topics by description containing (case-insensitive search)
     */
    Page<Topic> findByDescriptionContainingIgnoreCaseOrderBySortOrderAsc(String description, Pageable pageable);

    /**
     * Search topics by title, description, or content
     */
    @Query("SELECT t FROM Topic t WHERE " +
           "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(t.content) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "ORDER BY t.sortOrder ASC")
    Page<Topic> searchTopics(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find topics by module and search term
     */
    @Query("SELECT t FROM Topic t WHERE t.module.id = :moduleId AND " +
           "(UPPER(t.title) LIKE UPPER(CONCAT('%', :searchTerm, '%')) OR " +
           "UPPER(t.description) LIKE UPPER(CONCAT('%', :searchTerm, '%')) OR " +
           "UPPER(t.content) LIKE UPPER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY t.sortOrder ASC")
    Page<Topic> searchTopicsByModule(@Param("moduleId") Long moduleId, @Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find topic with module information
     */
    @Query("SELECT t FROM Topic t JOIN FETCH t.module WHERE t.id = :id")
    Optional<Topic> findByIdWithModule(@Param("id") Long id);

    /**
     * Find topics by estimated time range
     */
    @Query("SELECT t FROM Topic t WHERE t.estimatedMinutes BETWEEN :minMinutes AND :maxMinutes ORDER BY t.sortOrder ASC")
    List<Topic> findByEstimatedMinutesBetween(@Param("minMinutes") Integer minMinutes, @Param("maxMinutes") Integer maxMinutes);

    /**
     * Get topics statistics
     */
    @Query("SELECT " +
           "COUNT(t) as totalTopics, " +
           "AVG(t.estimatedMinutes) as avgMinutes, " +
           "SUM(t.estimatedMinutes) as totalMinutes, " +
           "COUNT(CASE WHEN t.content IS NOT NULL AND LENGTH(t.content) > 0 THEN 1 END) as topicsWithContent " +
           "FROM Topic t")
    Object[] getTopicsStatistics();

    /**
     * Find topics without content
     */
    @Query("SELECT t FROM Topic t WHERE t.content IS NULL OR LENGTH(TRIM(t.content)) = 0 ORDER BY t.sortOrder ASC")
    List<Topic> findTopicsWithoutContent();

    /**
     * Find topics with content
     */
    @Query("SELECT t FROM Topic t WHERE t.content IS NOT NULL AND LENGTH(TRIM(t.content)) > 0 ORDER BY t.sortOrder ASC")
    List<Topic> findTopicsWithContent();

    /**
     * Get longest topics by content length
     */
    @Query("SELECT t FROM Topic t WHERE t.content IS NOT NULL ORDER BY LENGTH(t.content) DESC")
    List<Topic> findLongestTopics(Pageable pageable);

    /**
     * Count topics by module
     */
    long countByModuleId(Long moduleId);

    /**
     * Check if topic exists by title in module
     */
    boolean existsByTitleIgnoreCaseAndModuleId(String title, Long moduleId);

    /**
     * Find next topic in module
     */
    @Query("SELECT t FROM Topic t WHERE t.module.id = :moduleId AND t.sortOrder > :currentSortOrder ORDER BY t.sortOrder ASC")
    Optional<Topic> findNextTopicInModule(@Param("moduleId") Long moduleId, @Param("currentSortOrder") Integer currentSortOrder);

    /**
     * Find previous topic in module
     */
    @Query("SELECT t FROM Topic t WHERE t.module.id = :moduleId AND t.sortOrder < :currentSortOrder ORDER BY t.sortOrder DESC")
    Optional<Topic> findPreviousTopicInModule(@Param("moduleId") Long moduleId, @Param("currentSortOrder") Integer currentSortOrder);

    /**
     * Get topics by module category
     */
    @Query("SELECT t FROM Topic t JOIN t.module m WHERE m.category = :category ORDER BY m.sortOrder ASC, t.sortOrder ASC")
    List<Topic> findByModuleCategory(@Param("category") com.learningportal.model.LearningModule.Category category);

    /**
     * Get topics by module difficulty level
     */
    @Query("SELECT t FROM Topic t JOIN t.module m WHERE m.difficultyLevel = :difficultyLevel ORDER BY m.sortOrder ASC, t.sortOrder ASC")
    List<Topic> findByModuleDifficultyLevel(@Param("difficultyLevel") com.learningportal.model.LearningModule.DifficultyLevel difficultyLevel);
}