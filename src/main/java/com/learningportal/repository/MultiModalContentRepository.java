package com.learningportal.repository;

import com.learningportal.model.MultiModalContent;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Multi-Modal Content
 * 
 * Provides data access methods for multi-modal learning content with visual, auditory,
 * kinesthetic, and reading/writing components, integrated with Amazon context.
 */
@Repository
@Tag(name = "Multi-Modal Content Repository", description = "Data access for multi-modal learning content")
public interface MultiModalContentRepository extends JpaRepository<MultiModalContent, Long> {

    /**
     * Find multi-modal content by content ID and type
     */
    Optional<MultiModalContent> findByContentIdAndContentType(Long contentId, MultiModalContent.ContentType contentType);

    /**
     * Find all multi-modal content for a specific content ID
     */
    List<MultiModalContent> findByContentId(Long contentId);

    /**
     * Find content by type
     */
    List<MultiModalContent> findByContentType(MultiModalContent.ContentType contentType);

    /**
     * Find content by type with pagination
     */
    Page<MultiModalContent> findByContentType(MultiModalContent.ContentType contentType, Pageable pageable);

    /**
     * Find content with visual components
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.visualComponent IS NOT NULL")
    List<MultiModalContent> findWithVisualComponents();

    /**
     * Find content with auditory components
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.auditoryComponent IS NOT NULL")
    List<MultiModalContent> findWithAuditoryComponents();

    /**
     * Find content with kinesthetic components
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.kinestheticComponent IS NOT NULL")
    List<MultiModalContent> findWithKinestheticComponents();

    /**
     * Find content with reading/writing components
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.readingWritingComponent IS NOT NULL")
    List<MultiModalContent> findWithReadingWritingComponents();

    /**
     * Find content with Amazon context integration
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.amazonContextIntegration IS NOT NULL")
    List<MultiModalContent> findWithAmazonContext();

    /**
     * Find truly multi-modal content (has 2+ modalities)
     */
    @Query("SELECT m FROM MultiModalContent m WHERE " +
           "(CASE WHEN m.visualComponent IS NOT NULL THEN 1 ELSE 0 END) + " +
           "(CASE WHEN m.auditoryComponent IS NOT NULL THEN 1 ELSE 0 END) + " +
           "(CASE WHEN m.kinestheticComponent IS NOT NULL THEN 1 ELSE 0 END) + " +
           "(CASE WHEN m.readingWritingComponent IS NOT NULL THEN 1 ELSE 0 END) >= 2")
    List<MultiModalContent> findTrulyMultiModalContent();

    /**
     * Find content by effectiveness score range
     */
    List<MultiModalContent> findByEffectivenessScoreBetween(Integer minScore, Integer maxScore);

    /**
     * Find high-effectiveness content (score >= 80)
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.effectivenessScore >= 80 ORDER BY m.effectivenessScore DESC")
    List<MultiModalContent> findHighEffectivenessContent();

    /**
     * Find content by cognitive load level
     */
    List<MultiModalContent> findByCognitiveLoadLevel(Integer cognitiveLoadLevel);

    /**
     * Find content by cognitive load range
     */
    List<MultiModalContent> findByCognitiveLoadLevelBetween(Integer minLevel, Integer maxLevel);

    /**
     * Find low cognitive load content (level <= 3)
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.cognitiveLoadLevel <= 3 ORDER BY m.cognitiveLoadLevel ASC")
    List<MultiModalContent> findLowCognitiveLoadContent();

    /**
     * Find content for specific learning style
     */
    @Query("SELECT m FROM MultiModalContent m WHERE " +
           "(:learningStyle = 'visual' AND m.visualComponent IS NOT NULL) OR " +
           "(:learningStyle = 'auditory' AND m.auditoryComponent IS NOT NULL) OR " +
           "(:learningStyle = 'kinesthetic' AND m.kinestheticComponent IS NOT NULL) OR " +
           "(:learningStyle = 'reading_writing' AND m.readingWritingComponent IS NOT NULL)")
    List<MultiModalContent> findByLearningStyle(@Param("learningStyle") String learningStyle);

    /**
     * Check if content has multi-modal support
     */
    boolean existsByContentIdAndContentType(Long contentId, MultiModalContent.ContentType contentType);

    /**
     * Count content by type
     */
    long countByContentType(MultiModalContent.ContentType contentType);

    /**
     * Count content with Amazon integration
     */
    @Query("SELECT COUNT(m) FROM MultiModalContent m WHERE m.amazonContextIntegration IS NOT NULL")
    long countWithAmazonIntegration();

    /**
     * Get multi-modal content statistics
     */
    @Query("SELECT " +
           "COUNT(m) as totalContent, " +
           "COUNT(CASE WHEN m.visualComponent IS NOT NULL THEN 1 END) as visualContent, " +
           "COUNT(CASE WHEN m.auditoryComponent IS NOT NULL THEN 1 END) as auditoryContent, " +
           "COUNT(CASE WHEN m.kinestheticComponent IS NOT NULL THEN 1 END) as kinestheticContent, " +
           "COUNT(CASE WHEN m.readingWritingComponent IS NOT NULL THEN 1 END) as readingWritingContent, " +
           "COUNT(CASE WHEN m.amazonContextIntegration IS NOT NULL THEN 1 END) as amazonIntegratedContent, " +
           "AVG(m.effectivenessScore) as averageEffectiveness, " +
           "AVG(m.cognitiveLoadLevel) as averageCognitiveLoad " +
           "FROM MultiModalContent m")
    Object[] getMultiModalStatistics();

    /**
     * Find content for Amazon competency level
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.amazonContextIntegration LIKE %:competencyLevel%")
    List<MultiModalContent> findByAmazonCompetencyLevel(@Param("competencyLevel") String competencyLevel);

    /**
     * Find content optimized for zero-experience learners (low cognitive load + high effectiveness)
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.cognitiveLoadLevel <= 5 AND m.effectivenessScore >= 70 ORDER BY m.effectivenessScore DESC, m.cognitiveLoadLevel ASC")
    List<MultiModalContent> findZeroExperienceOptimizedContent();

    /**
     * Find content by multiple content IDs
     */
    List<MultiModalContent> findByContentIdIn(List<Long> contentIds);

    /**
     * Find recent content (created in last N days)
     */
    @Query("SELECT m FROM MultiModalContent m WHERE m.createdAt >= :fromDate ORDER BY m.createdAt DESC")
    List<MultiModalContent> findRecentContent(@Param("fromDate") java.time.LocalDateTime fromDate);
}