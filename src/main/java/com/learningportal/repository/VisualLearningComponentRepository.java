package com.learningportal.repository;

import com.learningportal.model.VisualLearningComponent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Visual Learning Component entities
 * 
 * Provides data access methods for managing visual learning components
 * including algorithm animations, infographics, mind maps, and Amazon architecture diagrams.
 */
@Repository
public interface VisualLearningComponentRepository extends JpaRepository<VisualLearningComponent, Long> {

    /**
     * Find visual components for specific content
     */
    List<VisualLearningComponent> findByContentIdAndContentTypeAndIsActiveTrueOrderByCreatedAtDesc(
        Long contentId, String contentType);

    /**
     * Find components by type
     */
    List<VisualLearningComponent> findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
        VisualLearningComponent.VisualComponentType componentType);

    /**
     * Find components by complexity level
     */
    List<VisualLearningComponent> findByComplexityLevelAndIsActiveTrueOrderByEffectivenessScoreDesc(
        VisualLearningComponent.ComplexityLevel complexityLevel);

    /**
     * Find beginner-friendly components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.complexityLevel = 'BEGINNER' AND vlc.cognitiveLoadLevel <= 4 " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findBeginnerFriendlyComponents();

    /**
     * Find highly effective components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.effectivenessScore >= :minScore ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findHighlyEffectiveComponents(@Param("minScore") Integer minScore);

    /**
     * Find components with low cognitive load
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.cognitiveLoadLevel <= :maxLoad ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findLowCognitiveLoadComponents(@Param("maxLoad") Integer maxLoad);

    /**
     * Find interactive components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.interactiveElements IS NOT NULL AND vlc.interactiveElements != '' " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findInteractiveComponents();

    /**
     * Find animated components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.animationConfig IS NOT NULL AND vlc.animationConfig != '' " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findAnimatedComponents();

    /**
     * Find components with Amazon context
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.amazonContext IS NOT NULL AND vlc.amazonContext != '' " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findComponentsWithAmazonContext();

    /**
     * Find mobile-optimized components
     */
    List<VisualLearningComponent> findByMobileOptimizedTrueAndIsActiveTrueOrderByEffectivenessScoreDesc();

    /**
     * Find accessible components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.accessibilityFeatures IS NOT NULL AND vlc.accessibilityFeatures != '' " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findAccessibleComponents();

    /**
     * Find most viewed components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true " +
           "ORDER BY vlc.viewCount DESC")
    List<VisualLearningComponent> findMostViewedComponents(Pageable pageable);

    /**
     * Find components with high engagement
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.averageViewDuration >= :minDuration ORDER BY vlc.averageViewDuration DESC")
    List<VisualLearningComponent> findHighEngagementComponents(@Param("minDuration") Integer minDuration);

    /**
     * Find components by effectiveness score range
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.effectivenessScore BETWEEN :minScore AND :maxScore " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findByEffectivenessScoreRange(@Param("minScore") Integer minScore,
                                                               @Param("maxScore") Integer maxScore);

    /**
     * Find components by cognitive load range
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.cognitiveLoadLevel BETWEEN :minLoad AND :maxLoad " +
           "ORDER BY vlc.effectivenessScore DESC")
    List<VisualLearningComponent> findByCognitiveLoadRange(@Param("minLoad") Integer minLoad,
                                                          @Param("maxLoad") Integer maxLoad);

    /**
     * Search components by title or description
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "(LOWER(vlc.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(vlc.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY vlc.effectivenessScore DESC")
    Page<VisualLearningComponent> searchComponents(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find recently created components
     */
    List<VisualLearningComponent> findByCreatedAtAfterAndIsActiveTrueOrderByCreatedAtDesc(LocalDateTime since);

    /**
     * Find recently updated components
     */
    List<VisualLearningComponent> findByUpdatedAtAfterAndIsActiveTrueOrderByUpdatedAtDesc(LocalDateTime since);

    /**
     * Get component type distribution
     */
    @Query("SELECT vlc.componentType, COUNT(vlc) FROM VisualLearningComponent vlc " +
           "WHERE vlc.isActive = true GROUP BY vlc.componentType ORDER BY COUNT(vlc) DESC")
    List<Object[]> getComponentTypeDistribution();

    /**
     * Get complexity level distribution
     */
    @Query("SELECT vlc.complexityLevel, COUNT(vlc) FROM VisualLearningComponent vlc " +
           "WHERE vlc.isActive = true GROUP BY vlc.complexityLevel ORDER BY vlc.complexityLevel")
    List<Object[]> getComplexityLevelDistribution();

    /**
     * Get effectiveness statistics
     */
    @Query("SELECT COUNT(vlc), AVG(vlc.effectivenessScore), MIN(vlc.effectivenessScore), " +
           "MAX(vlc.effectivenessScore) FROM VisualLearningComponent vlc WHERE vlc.isActive = true")
    Object[] getEffectivenessStatistics();

    /**
     * Get cognitive load statistics
     */
    @Query("SELECT COUNT(vlc), AVG(vlc.cognitiveLoadLevel), MIN(vlc.cognitiveLoadLevel), " +
           "MAX(vlc.cognitiveLoadLevel) FROM VisualLearningComponent vlc WHERE vlc.isActive = true")
    Object[] getCognitiveLoadStatistics();

    /**
     * Get view statistics
     */
    @Query("SELECT COUNT(vlc), SUM(vlc.viewCount), AVG(vlc.viewCount), " +
           "AVG(vlc.averageViewDuration) FROM VisualLearningComponent vlc WHERE vlc.isActive = true")
    Object[] getViewStatistics();

    /**
     * Find components needing improvement
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "(vlc.effectivenessScore < :minEffectiveness OR vlc.averageViewDuration < :minDuration) " +
           "ORDER BY vlc.effectivenessScore ASC, vlc.averageViewDuration ASC")
    List<VisualLearningComponent> findComponentsNeedingImprovement(@Param("minEffectiveness") Integer minEffectiveness,
                                                                  @Param("minDuration") Integer minDuration);

    /**
     * Find top performing components
     */
    @Query("SELECT vlc FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.effectivenessScore >= 80 AND vlc.averageViewDuration >= 120 " +
           "ORDER BY vlc.effectivenessScore DESC, vlc.averageViewDuration DESC")
    List<VisualLearningComponent> findTopPerformingComponents();

    /**
     * Count components by content
     */
    long countByContentIdAndContentTypeAndIsActiveTrue(Long contentId, String contentType);

    /**
     * Count components by type
     */
    long countByComponentTypeAndIsActiveTrue(VisualLearningComponent.VisualComponentType componentType);

    /**
     * Count interactive components
     */
    @Query("SELECT COUNT(vlc) FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.interactiveElements IS NOT NULL AND vlc.interactiveElements != ''")
    long countInteractiveComponents();

    /**
     * Count animated components
     */
    @Query("SELECT COUNT(vlc) FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.animationConfig IS NOT NULL AND vlc.animationConfig != ''")
    long countAnimatedComponents();

    /**
     * Count components with Amazon context
     */
    @Query("SELECT COUNT(vlc) FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.amazonContext IS NOT NULL AND vlc.amazonContext != ''")
    long countComponentsWithAmazonContext();

    /**
     * Count mobile-optimized components
     */
    long countByMobileOptimizedTrueAndIsActiveTrue();

    /**
     * Count accessible components
     */
    @Query("SELECT COUNT(vlc) FROM VisualLearningComponent vlc WHERE vlc.isActive = true AND " +
           "vlc.accessibilityFeatures IS NOT NULL AND vlc.accessibilityFeatures != ''")
    long countAccessibleComponents();

    /**
     * Find components for content with pagination
     */
    Page<VisualLearningComponent> findByContentIdAndContentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
        Long contentId, String contentType, Pageable pageable);

    /**
     * Find components by type with pagination
     */
    Page<VisualLearningComponent> findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
        VisualLearningComponent.VisualComponentType componentType, Pageable pageable);
}