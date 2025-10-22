package com.learningportal.repository;

import com.learningportal.model.FeynmanTechniqueImplementation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Feynman Technique Implementations
 * 
 * Provides data access methods for Feynman Technique learning implementations
 * with simple explanations, knowledge gap tracking, and mastery validation.
 */
@Repository
@Tag(name = "Feynman Technique Repository", description = "Data access for Feynman Technique implementations")
public interface FeynmanTechniqueRepository extends JpaRepository<FeynmanTechniqueImplementation, Long> {

    /**
     * Find implementation by content ID and type
     */
    Optional<FeynmanTechniqueImplementation> findByContentIdAndContentType(Long contentId, String contentType);

    /**
     * Find all implementations for specific content ID
     */
    List<FeynmanTechniqueImplementation> findByContentId(Long contentId);

    /**
     * Find implementations by content type
     */
    List<FeynmanTechniqueImplementation> findByContentType(String contentType);

    /**
     * Find mastered implementations
     */
    List<FeynmanTechniqueImplementation> findByMasteryAchievedTrue();

    /**
     * Find implementations still in progress
     */
    List<FeynmanTechniqueImplementation> findByMasteryAchievedFalse();

    /**
     * Find implementations by iteration count
     */
    List<FeynmanTechniqueImplementation> findByIterationCount(Integer iterationCount);

    /**
     * Find implementations with high iteration count (struggling concepts)
     */
    List<FeynmanTechniqueImplementation> findByIterationCountGreaterThan(Integer iterationCount);

    /**
     * Find implementations with knowledge gaps
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.knowledgeGaps IS NOT NULL")
    List<FeynmanTechniqueImplementation> findWithKnowledgeGaps();

    /**
     * Find implementations with Amazon context
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.amazonScaleContext IS NOT NULL")
    List<FeynmanTechniqueImplementation> findWithAmazonContext();

    /**
     * Find implementations with source reinforcement
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.sourceReinforcement IS NOT NULL")
    List<FeynmanTechniqueImplementation> findWithSourceReinforcement();

    /**
     * Find implementations with analogies
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.simplificationAnalogies IS NOT NULL")
    List<FeynmanTechniqueImplementation> findWithAnalogies();

    /**
     * Count mastered implementations
     */
    long countByMasteryAchievedTrue();

    /**
     * Count implementations in progress
     */
    long countByMasteryAchievedFalse();

    /**
     * Count implementations by content type
     */
    long countByContentType(String contentType);

    /**
     * Get average iteration count
     */
    @Query("SELECT AVG(f.iterationCount) FROM FeynmanTechniqueImplementation f")
    Double getAverageIterationCount();

    /**
     * Get mastery rate by content type
     */
    @Query("SELECT f.contentType, " +
           "COUNT(f) as total, " +
           "COUNT(CASE WHEN f.masteryAchieved = true THEN 1 END) as mastered " +
           "FROM FeynmanTechniqueImplementation f " +
           "GROUP BY f.contentType")
    List<Object[]> getMasteryRateByContentType();

    /**
     * Find implementations ready for next iteration
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.masteryAchieved = false AND f.knowledgeGaps IS NOT NULL")
    List<FeynmanTechniqueImplementation> findReadyForNextIteration();

    /**
     * Find implementations by explanation complexity (length)
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE LENGTH(f.simpleExplanation) BETWEEN :minLength AND :maxLength")
    List<FeynmanTechniqueImplementation> findByExplanationLength(@Param("minLength") int minLength, @Param("maxLength") int maxLength);

    /**
     * Find simple explanations (short length)
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE LENGTH(f.simpleExplanation) <= 500")
    List<FeynmanTechniqueImplementation> findSimpleExplanations();

    /**
     * Find complex explanations (long length)
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE LENGTH(f.simpleExplanation) > 1000")
    List<FeynmanTechniqueImplementation> findComplexExplanations();

    /**
     * Get Feynman Technique statistics
     */
    @Query("SELECT " +
           "COUNT(f) as totalImplementations, " +
           "COUNT(CASE WHEN f.masteryAchieved = true THEN 1 END) as masteredImplementations, " +
           "AVG(f.iterationCount) as averageIterations, " +
           "COUNT(CASE WHEN f.knowledgeGaps IS NOT NULL THEN 1 END) as implementationsWithGaps, " +
           "COUNT(CASE WHEN f.amazonScaleContext IS NOT NULL THEN 1 END) as amazonIntegratedImplementations " +
           "FROM FeynmanTechniqueImplementation f")
    Object[] getFeynmanStatistics();

    /**
     * Find recent implementations
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.createdAt >= :fromDate ORDER BY f.createdAt DESC")
    List<FeynmanTechniqueImplementation> findRecentImplementations(@Param("fromDate") java.time.LocalDateTime fromDate);

    /**
     * Find implementations needing attention (high iterations, not mastered)
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.iterationCount >= 3 AND f.masteryAchieved = false ORDER BY f.iterationCount DESC")
    List<FeynmanTechniqueImplementation> findImplementationsNeedingAttention();

    /**
     * Find successful quick mastery implementations (mastered in 1-2 iterations)
     */
    @Query("SELECT f FROM FeynmanTechniqueImplementation f WHERE f.masteryAchieved = true AND f.iterationCount <= 2")
    List<FeynmanTechniqueImplementation> findQuickMasteryImplementations();

    /**
     * Check if content has Feynman implementation
     */
    boolean existsByContentIdAndContentType(Long contentId, String contentType);

    /**
     * Find implementations by multiple content IDs
     */
    List<FeynmanTechniqueImplementation> findByContentIdIn(List<Long> contentIds);
}