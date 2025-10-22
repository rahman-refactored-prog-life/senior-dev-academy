package com.learningportal.repository;

import com.learningportal.model.BloomsTaxonomyProgression;
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
 * Repository for Bloom's Taxonomy Progression entities
 * 
 * Provides data access methods for managing Bloom's Taxonomy progression
 * tracking with Amazon competency alignment and zero-experience learning support.
 */
@Repository
public interface BloomsTaxonomyProgressionRepository extends JpaRepository<BloomsTaxonomyProgression, Long> {

    /**
     * Find progression record for a specific user and content
     */
    Optional<BloomsTaxonomyProgression> findByUserIdAndContentId(Long userId, Long contentId);

    /**
     * Find all progression records for a user
     */
    List<BloomsTaxonomyProgression> findByUserIdOrderByUpdatedAtDesc(Long userId);

    /**
     * Find progressions by current Bloom's level
     */
    List<BloomsTaxonomyProgression> findByCurrentLevelOrderByUpdatedAtDesc(
        BloomsTaxonomyProgression.BloomsLevel currentLevel);

    /**
     * Find users ready for next level advancement
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "CASE btp.currentLevel " +
           "WHEN 'REMEMBER' THEN btp.rememberLevelScore >= 80 " +
           "WHEN 'UNDERSTAND' THEN btp.understandLevelScore >= 80 " +
           "WHEN 'APPLY' THEN btp.applyLevelScore >= 80 " +
           "WHEN 'ANALYZE' THEN btp.analyzeLevelScore >= 80 " +
           "WHEN 'EVALUATE' THEN btp.evaluateLevelScore >= 80 " +
           "WHEN 'CREATE' THEN btp.createLevelScore >= 80 " +
           "ELSE false END = true " +
           "ORDER BY btp.updatedAt DESC")
    List<BloomsTaxonomyProgression> findUsersReadyForAdvancement();

    /**
     * Find progressions by Amazon competency alignment
     */
    List<BloomsTaxonomyProgression> findByAmazonCompetencyAlignmentOrderByUpdatedAtDesc(String amazonCompetencyAlignment);

    /**
     * Find users at Amazon Senior SDE level (L5+)
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "(btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0 >= 80 " +
           "AND btp.currentLevel IN ('EVALUATE', 'CREATE') " +
           "ORDER BY btp.updatedAt DESC")
    List<BloomsTaxonomyProgression> findAmazonSeniorSDELevelUsers();

    /**
     * Find progressions with high overall scores
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "(btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0 >= :minScore " +
           "ORDER BY (btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) DESC")
    List<BloomsTaxonomyProgression> findHighPerformers(@Param("minScore") Double minScore);

    /**
     * Find progressions that need attention (low scores)
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "(btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0 < :maxScore " +
           "ORDER BY btp.updatedAt DESC")
    List<BloomsTaxonomyProgression> findUsersNeedingSupport(@Param("maxScore") Double maxScore);

    /**
     * Find recent progressions (updated within specified time)
     */
    List<BloomsTaxonomyProgression> findByUpdatedAtAfterOrderByUpdatedAtDesc(LocalDateTime since);

    /**
     * Get progression statistics for a specific content
     */
    @Query("SELECT " +
           "COUNT(btp), " +
           "AVG(btp.rememberLevelScore), " +
           "AVG(btp.understandLevelScore), " +
           "AVG(btp.applyLevelScore), " +
           "AVG(btp.analyzeLevelScore), " +
           "AVG(btp.evaluateLevelScore), " +
           "AVG(btp.createLevelScore), " +
           "AVG((btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0) " +
           "FROM BloomsTaxonomyProgression btp WHERE btp.contentId = :contentId")
    Object[] getProgressionStatisticsForContent(@Param("contentId") Long contentId);

    /**
     * Get level distribution for analytics
     */
    @Query("SELECT btp.currentLevel, COUNT(btp) FROM BloomsTaxonomyProgression btp " +
           "GROUP BY btp.currentLevel ORDER BY btp.currentLevel")
    List<Object[]> getLevelDistribution();

    /**
     * Get Amazon competency alignment distribution
     */
    @Query("SELECT btp.amazonCompetencyAlignment, COUNT(btp) FROM BloomsTaxonomyProgression btp " +
           "WHERE btp.amazonCompetencyAlignment IS NOT NULL " +
           "GROUP BY btp.amazonCompetencyAlignment ORDER BY COUNT(btp) DESC")
    List<Object[]> getAmazonCompetencyDistribution();

    /**
     * Find progressions with progression evidence
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "btp.progressionEvidence IS NOT NULL AND btp.progressionEvidence != '' " +
           "ORDER BY btp.updatedAt DESC")
    List<BloomsTaxonomyProgression> findProgressionsWithEvidence();

    /**
     * Find progressions missing next level requirements
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "btp.nextLevelRequirements IS NULL OR btp.nextLevelRequirements = '' " +
           "ORDER BY btp.updatedAt DESC")
    List<BloomsTaxonomyProgression> findProgressionsMissingRequirements();

    /**
     * Count progressions by user
     */
    long countByUserId(Long userId);

    /**
     * Count progressions by content
     */
    long countByContentId(Long contentId);

    /**
     * Find top performers across all content
     */
    @Query("SELECT btp.userId, COUNT(btp), " +
           "AVG((btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0) " +
           "FROM BloomsTaxonomyProgression btp " +
           "GROUP BY btp.userId " +
           "HAVING AVG((btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0) >= :minAvgScore " +
           "ORDER BY AVG((btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0) DESC")
    List<Object[]> findTopPerformersAcrossContent(@Param("minAvgScore") Double minAvgScore);

    /**
     * Find progressions by learning velocity
     */
    @Query("SELECT btp FROM BloomsTaxonomyProgression btp WHERE " +
           "btp.createdAt IS NOT NULL AND btp.updatedAt IS NOT NULL " +
           "AND EXTRACT(DAY FROM (btp.updatedAt - btp.createdAt)) > 0 " +
           "AND ((btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0) / " +
           "EXTRACT(DAY FROM (btp.updatedAt - btp.createdAt)) >= :minVelocity " +
           "ORDER BY ((btp.rememberLevelScore + btp.understandLevelScore + btp.applyLevelScore + " +
           "btp.analyzeLevelScore + btp.evaluateLevelScore + btp.createLevelScore) / 6.0) / " +
           "EXTRACT(DAY FROM (btp.updatedAt - btp.createdAt)) DESC")
    List<BloomsTaxonomyProgression> findFastLearners(@Param("minVelocity") Double minVelocity);

    /**
     * Find progressions for content with pagination
     */
    Page<BloomsTaxonomyProgression> findByContentIdOrderByUpdatedAtDesc(Long contentId, Pageable pageable);

    /**
     * Find progressions for user with pagination
     */
    Page<BloomsTaxonomyProgression> findByUserIdOrderByUpdatedAtDesc(Long userId, Pageable pageable);
}