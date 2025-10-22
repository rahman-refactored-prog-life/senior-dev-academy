package com.learningportal.repository;

import com.learningportal.model.DualCodingApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Dual Coding Applications
 * 
 * Provides data access methods for Dual Coding Theory applications with visual,
 * verbal, spatial, and temporal learning components integrated with Amazon examples.
 */
@Repository
@Tag(name = "Dual Coding Repository", description = "Data access for Dual Coding Theory applications")
public interface DualCodingRepository extends JpaRepository<DualCodingApplication, Long> {

    /**
     * Find application by content ID and type
     */
    Optional<DualCodingApplication> findByContentIdAndContentType(Long contentId, String contentType);

    /**
     * Find all applications for specific content ID
     */
    List<DualCodingApplication> findByContentId(Long contentId);

    /**
     * Find applications by content type
     */
    List<DualCodingApplication> findByContentType(String contentType);

    /**
     * Find applications with visual information
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.visualInformation IS NOT NULL")
    List<DualCodingApplication> findWithVisualInformation();

    /**
     * Find applications with verbal information
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.verbalInformation IS NOT NULL")
    List<DualCodingApplication> findWithVerbalInformation();

    /**
     * Find applications with spatial relationships
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.spatialRelationships IS NOT NULL")
    List<DualCodingApplication> findWithSpatialRelationships();

    /**
     * Find applications with temporal sequences
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.temporalSequences IS NOT NULL")
    List<DualCodingApplication> findWithTemporalSequences();

    /**
     * Find applications with Amazon architecture examples
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.amazonArchitectureExamples IS NOT NULL")
    List<DualCodingApplication> findWithAmazonIntegration();

    /**
     * Find fully implemented dual coding applications (both visual and verbal)
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.visualInformation IS NOT NULL AND d.verbalInformation IS NOT NULL")
    List<DualCodingApplication> findFullyImplemented();

    /**
     * Find applications with high integration effectiveness
     */
    List<DualCodingApplication> findByIntegrationEffectivenessGreaterThanEqual(Integer minEffectiveness);

    /**
     * Find applications by effectiveness range
     */
    List<DualCodingApplication> findByIntegrationEffectivenessBetween(Integer minEffectiveness, Integer maxEffectiveness);

    /**
     * Find excellent effectiveness applications (90+)
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.integrationEffectiveness >= 90 ORDER BY d.integrationEffectiveness DESC")
    List<DualCodingApplication> findExcellentEffectivenessApplications();

    /**
     * Find applications needing improvement (effectiveness < 60)
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.integrationEffectiveness < 60 ORDER BY d.integrationEffectiveness ASC")
    List<DualCodingApplication> findApplicationsNeedingImprovement();

    /**
     * Count fully implemented applications
     */
    @Query("SELECT COUNT(d) FROM DualCodingApplication d WHERE d.visualInformation IS NOT NULL AND d.verbalInformation IS NOT NULL")
    long countFullyImplemented();

    /**
     * Count applications with Amazon integration
     */
    @Query("SELECT COUNT(d) FROM DualCodingApplication d WHERE d.amazonArchitectureExamples IS NOT NULL")
    long countWithAmazonIntegration();

    /**
     * Count applications by content type
     */
    long countByContentType(String contentType);

    /**
     * Get average integration effectiveness
     */
    @Query("SELECT AVG(d.integrationEffectiveness) FROM DualCodingApplication d")
    Double getAverageEffectiveness();

    /**
     * Get effectiveness statistics by content type
     */
    @Query("SELECT d.contentType, " +
           "COUNT(d) as total, " +
           "AVG(d.integrationEffectiveness) as averageEffectiveness, " +
           "COUNT(CASE WHEN d.visualInformation IS NOT NULL AND d.verbalInformation IS NOT NULL THEN 1 END) as fullyImplemented " +
           "FROM DualCodingApplication d " +
           "GROUP BY d.contentType")
    List<Object[]> getEffectivenessStatisticsByContentType();

    /**
     * Find applications with complete dual coding (all 4 components)
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE " +
           "d.visualInformation IS NOT NULL AND " +
           "d.verbalInformation IS NOT NULL AND " +
           "d.spatialRelationships IS NOT NULL AND " +
           "d.temporalSequences IS NOT NULL")
    List<DualCodingApplication> findCompleteApplications();

    /**
     * Find applications ready for Amazon-scale learning
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE " +
           "d.visualInformation IS NOT NULL AND " +
           "d.verbalInformation IS NOT NULL AND " +
           "d.amazonArchitectureExamples IS NOT NULL AND " +
           "d.integrationEffectiveness >= 70")
    List<DualCodingApplication> findAmazonScaleReadyApplications();

    /**
     * Find applications by coding channel count
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE " +
           "(CASE WHEN d.visualInformation IS NOT NULL THEN 1 ELSE 0 END) + " +
           "(CASE WHEN d.verbalInformation IS NOT NULL THEN 1 ELSE 0 END) + " +
           "(CASE WHEN d.spatialRelationships IS NOT NULL THEN 1 ELSE 0 END) + " +
           "(CASE WHEN d.temporalSequences IS NOT NULL THEN 1 ELSE 0 END) >= :minChannels")
    List<DualCodingApplication> findByMinimumCodingChannels(@Param("minChannels") int minChannels);

    /**
     * Get dual coding statistics
     */
    @Query("SELECT " +
           "COUNT(d) as totalApplications, " +
           "COUNT(CASE WHEN d.visualInformation IS NOT NULL THEN 1 END) as visualApplications, " +
           "COUNT(CASE WHEN d.verbalInformation IS NOT NULL THEN 1 END) as verbalApplications, " +
           "COUNT(CASE WHEN d.spatialRelationships IS NOT NULL THEN 1 END) as spatialApplications, " +
           "COUNT(CASE WHEN d.temporalSequences IS NOT NULL THEN 1 END) as temporalApplications, " +
           "COUNT(CASE WHEN d.amazonArchitectureExamples IS NOT NULL THEN 1 END) as amazonIntegratedApplications, " +
           "AVG(d.integrationEffectiveness) as averageEffectiveness " +
           "FROM DualCodingApplication d")
    Object[] getDualCodingStatistics();

    /**
     * Find applications for zero-experience learners (balanced and effective)
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE " +
           "d.visualInformation IS NOT NULL AND " +
           "d.verbalInformation IS NOT NULL AND " +
           "d.integrationEffectiveness >= 70 " +
           "ORDER BY d.integrationEffectiveness DESC")
    List<DualCodingApplication> findZeroExperienceOptimizedApplications();

    /**
     * Find recent applications
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE d.createdAt >= :fromDate ORDER BY d.createdAt DESC")
    List<DualCodingApplication> findRecentApplications(@Param("fromDate") java.time.LocalDateTime fromDate);

    /**
     * Check if content has dual coding application
     */
    boolean existsByContentIdAndContentType(Long contentId, String contentType);

    /**
     * Find applications by multiple content IDs
     */
    List<DualCodingApplication> findByContentIdIn(List<Long> contentIds);

    /**
     * Find applications with balanced visual-verbal implementation
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE " +
           "d.visualInformation IS NOT NULL AND " +
           "d.verbalInformation IS NOT NULL " +
           "ORDER BY d.integrationEffectiveness DESC")
    List<DualCodingApplication> findBalancedImplementations();

    /**
     * Find applications optimized for specific learning preferences
     */
    @Query("SELECT d FROM DualCodingApplication d WHERE " +
           "(:preferVisual = true AND d.visualInformation IS NOT NULL) OR " +
           "(:preferVerbal = true AND d.verbalInformation IS NOT NULL) OR " +
           "(:preferSpatial = true AND d.spatialRelationships IS NOT NULL) OR " +
           "(:preferTemporal = true AND d.temporalSequences IS NOT NULL)")
    List<DualCodingApplication> findByLearningPreferences(
        @Param("preferVisual") boolean preferVisual,
        @Param("preferVerbal") boolean preferVerbal,
        @Param("preferSpatial") boolean preferSpatial,
        @Param("preferTemporal") boolean preferTemporal
    );
}