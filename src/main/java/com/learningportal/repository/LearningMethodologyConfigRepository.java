package com.learningportal.repository;

import com.learningportal.model.LearningMethodologyConfig;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Learning Methodology Configuration
 * 
 * Provides data access methods for cognitive science-based learning methodology configurations
 * with Amazon competency alignment and multi-modal support.
 */
@Repository
@Tag(name = "Learning Methodology Config Repository", description = "Data access for learning methodology configurations")
public interface LearningMethodologyConfigRepository extends JpaRepository<LearningMethodologyConfig, Long> {

    /**
     * Find methodology configuration by name
     */
    Optional<LearningMethodologyConfig> findByMethodologyNameIgnoreCase(String methodologyName);

    /**
     * Find all active methodologies with multi-modal support
     */
    List<LearningMethodologyConfig> findByMultiModalSupportTrue();

    /**
     * Find all active methodologies with adaptive intelligence
     */
    List<LearningMethodologyConfig> findByAdaptiveIntelligenceTrue();

    /**
     * Find methodologies with both multi-modal and adaptive intelligence support
     */
    List<LearningMethodologyConfig> findByMultiModalSupportTrueAndAdaptiveIntelligenceTrue();

    /**
     * Check if methodology exists by name
     */
    boolean existsByMethodologyNameIgnoreCase(String methodologyName);

    /**
     * Find methodologies containing specific cognitive science principle
     */
    @Query("SELECT m FROM LearningMethodologyConfig m WHERE m.cognitiveSciencePrinciples LIKE %:principle%")
    List<LearningMethodologyConfig> findByPrincipleContaining(@Param("principle") String principle);

    /**
     * Find methodologies with Amazon competency alignment
     */
    @Query("SELECT m FROM LearningMethodologyConfig m WHERE m.amazonCompetencyAlignment IS NOT NULL")
    List<LearningMethodologyConfig> findWithAmazonAlignment();

    /**
     * Get methodology statistics
     */
    @Query("SELECT " +
           "COUNT(m) as totalMethodologies, " +
           "COUNT(CASE WHEN m.multiModalSupport = true THEN 1 END) as multiModalCount, " +
           "COUNT(CASE WHEN m.adaptiveIntelligence = true THEN 1 END) as adaptiveIntelligenceCount, " +
           "COUNT(CASE WHEN m.amazonCompetencyAlignment IS NOT NULL THEN 1 END) as amazonAlignedCount " +
           "FROM LearningMethodologyConfig m")
    Object[] getMethodologyStatistics();

    /**
     * Find the most recently created methodology
     */
    Optional<LearningMethodologyConfig> findTopByOrderByCreatedAtDesc();

    /**
     * Find methodologies created after a specific date
     */
    @Query("SELECT m FROM LearningMethodologyConfig m WHERE m.createdAt >= :fromDate ORDER BY m.createdAt DESC")
    List<LearningMethodologyConfig> findRecentMethodologies(@Param("fromDate") java.time.LocalDateTime fromDate);

    /**
     * Count methodologies with effectiveness metrics
     */
    @Query("SELECT COUNT(m) FROM LearningMethodologyConfig m WHERE m.effectivenessMetrics IS NOT NULL")
    long countWithEffectivenessMetrics();

    /**
     * Find default zero-experience methodology
     */
    @Query("SELECT m FROM LearningMethodologyConfig m WHERE m.methodologyName LIKE '%Zero-Experience%' ORDER BY m.createdAt DESC")
    Optional<LearningMethodologyConfig> findDefaultZeroExperienceMethodology();
}