package com.learningportal.repository;

import com.learningportal.model.CognitiveLearningComponent;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Cognitive Learning Components
 * 
 * Provides data access methods for cognitive science components like Feynman Technique,
 * Dual Coding Theory, Bloom's Taxonomy, and Spaced Repetition.
 */
@Repository
@Tag(name = "Cognitive Learning Component Repository", description = "Data access for cognitive learning components")
public interface CognitiveLearningComponentRepository extends JpaRepository<CognitiveLearningComponent, Long> {

    /**
     * Find components by type
     */
    List<CognitiveLearningComponent> findByComponentType(CognitiveLearningComponent.ComponentType componentType);

    /**
     * Find active components ordered by execution order
     */
    List<CognitiveLearningComponent> findByIsActiveTrueOrderByOrderIndexAsc();

    /**
     * Find components by type and active status
     */
    List<CognitiveLearningComponent> findByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType componentType);

    /**
     * Find components with Amazon integration
     */
    @Query("SELECT c FROM CognitiveLearningComponent c WHERE c.amazonIntegration IS NOT NULL AND c.isActive = true ORDER BY c.orderIndex ASC")
    List<CognitiveLearningComponent> findWithAmazonIntegration();

    /**
     * Find components with effectiveness metrics
     */
    @Query("SELECT c FROM CognitiveLearningComponent c WHERE c.effectivenessMetrics IS NOT NULL AND c.isActive = true ORDER BY c.orderIndex ASC")
    List<CognitiveLearningComponent> findWithEffectivenessMetrics();

    /**
     * Find component by type (single result expected)
     */
    Optional<CognitiveLearningComponent> findByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType componentType, boolean isActive);

    /**
     * Check if component type exists and is active
     */
    boolean existsByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType componentType);

    /**
     * Find components by order index range
     */
    List<CognitiveLearningComponent> findByOrderIndexBetweenAndIsActiveTrueOrderByOrderIndexAsc(Integer startIndex, Integer endIndex);

    /**
     * Get next order index for new component
     */
    @Query("SELECT COALESCE(MAX(c.orderIndex), 0) + 1 FROM CognitiveLearningComponent c")
    Integer getNextOrderIndex();

    /**
     * Find components with validation criteria
     */
    @Query("SELECT c FROM CognitiveLearningComponent c WHERE c.validationCriteria IS NOT NULL AND c.isActive = true ORDER BY c.orderIndex ASC")
    List<CognitiveLearningComponent> findWithValidationCriteria();

    /**
     * Count active components by type
     */
    long countByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType componentType);

    /**
     * Count total active components
     */
    long countByIsActiveTrue();

    /**
     * Find components for specific learning sequence
     */
    @Query("SELECT c FROM CognitiveLearningComponent c WHERE c.isActive = true AND c.orderIndex <= :maxOrder ORDER BY c.orderIndex ASC")
    List<CognitiveLearningComponent> findForLearningSequence(@Param("maxOrder") Integer maxOrder);

    /**
     * Get component statistics
     */
    @Query("SELECT " +
           "COUNT(c) as totalComponents, " +
           "COUNT(CASE WHEN c.isActive = true THEN 1 END) as activeComponents, " +
           "COUNT(CASE WHEN c.amazonIntegration IS NOT NULL THEN 1 END) as amazonIntegratedComponents, " +
           "COUNT(CASE WHEN c.effectivenessMetrics IS NOT NULL THEN 1 END) as componentsWithMetrics " +
           "FROM CognitiveLearningComponent c")
    Object[] getComponentStatistics();

    /**
     * Find components by implementation detail containing specific feature
     */
    @Query("SELECT c FROM CognitiveLearningComponent c WHERE c.implementationDetails LIKE %:feature% AND c.isActive = true ORDER BY c.orderIndex ASC")
    List<CognitiveLearningComponent> findByImplementationFeature(@Param("feature") String feature);

    /**
     * Find Feynman Technique component
     */
    default Optional<CognitiveLearningComponent> findFeynmanComponent() {
        return findByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType.FEYNMAN, true);
    }

    /**
     * Find Dual Coding component
     */
    default Optional<CognitiveLearningComponent> findDualCodingComponent() {
        return findByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType.DUAL_CODING, true);
    }

    /**
     * Find Bloom's Taxonomy component
     */
    default Optional<CognitiveLearningComponent> findBloomsComponent() {
        return findByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType.BLOOMS, true);
    }

    /**
     * Find Spaced Repetition component
     */
    default Optional<CognitiveLearningComponent> findSpacedRepetitionComponent() {
        return findByComponentTypeAndIsActiveTrue(CognitiveLearningComponent.ComponentType.SPACED_REPETITION, true);
    }
}