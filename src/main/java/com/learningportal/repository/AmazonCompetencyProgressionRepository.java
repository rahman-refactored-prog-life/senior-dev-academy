package com.learningportal.repository;

import com.learningportal.model.AmazonCompetencyProgression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for Amazon Competency Progression entities
 * 
 * Provides data access methods for managing Amazon competency progression
 * with L3-L6 tracking and Leadership Principles integration.
 */
@Repository
public interface AmazonCompetencyProgressionRepository extends JpaRepository<AmazonCompetencyProgression, Long> {

    /**
     * Find progression record for a specific user
     */
    Optional<AmazonCompetencyProgression> findByUserId(Long userId);

    /**
     * Find progressions by current Amazon level
     */
    List<AmazonCompetencyProgression> findByCurrentAmazonLevelOrderByUpdatedAtDesc(
        AmazonCompetencyProgression.AmazonLevel currentAmazonLevel);

    /**
     * Find progressions by target Amazon level
     */
    List<AmazonCompetencyProgression> findByTargetAmazonLevelOrderByUpdatedAtDesc(
        AmazonCompetencyProgression.AmazonLevel targetAmazonLevel);

    /**
     * Find users ready for target level
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore >= 80 AND acp.culturalFitScore >= 75 " +
           "ORDER BY acp.interviewReadinessScore DESC, acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findUsersReadyForTargetLevel();

    /**
     * Find users ready for promotion
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "(acp.currentAmazonLevel = 'L3' AND acp.interviewReadinessScore >= 70 AND acp.culturalFitScore >= 70) OR " +
           "(acp.currentAmazonLevel = 'L4' AND acp.interviewReadinessScore >= 80 AND acp.culturalFitScore >= 80) OR " +
           "(acp.currentAmazonLevel = 'L5' AND acp.interviewReadinessScore >= 90 AND acp.culturalFitScore >= 85) " +
           "ORDER BY acp.interviewReadinessScore DESC")
    List<AmazonCompetencyProgression> findUsersReadyForPromotion();

    /**
     * Find users exceeding Amazon hiring bar
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore >= 85 AND acp.culturalFitScore >= 80 " +
           "ORDER BY acp.interviewReadinessScore DESC, acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findUsersExceedingHiringBar();

    /**
     * Find users meeting Amazon hiring bar
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore >= 75 AND acp.culturalFitScore >= 70 " +
           "ORDER BY acp.interviewReadinessScore DESC, acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findUsersMeetingHiringBar();

    /**
     * Find users below Amazon hiring bar
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore < 75 OR acp.culturalFitScore < 70 " +
           "ORDER BY acp.interviewReadinessScore ASC, acp.culturalFitScore ASC")
    List<AmazonCompetencyProgression> findUsersBelowHiringBar();

    /**
     * Find progressions with high interview readiness
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE acp.interviewReadinessScore >= :minScore " +
           "ORDER BY acp.interviewReadinessScore DESC")
    List<AmazonCompetencyProgression> findHighInterviewReadiness(@Param("minScore") Integer minScore);

    /**
     * Find progressions with high cultural fit
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE acp.culturalFitScore >= :minScore " +
           "ORDER BY acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findHighCulturalFit(@Param("minScore") Integer minScore);

    /**
     * Find progressions needing assessment update
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE acp.lastAssessed < :cutoffDate " +
           "ORDER BY acp.lastAssessed ASC")
    List<AmazonCompetencyProgression> findStaleAssessments(@Param("cutoffDate") LocalDateTime cutoffDate);

    /**
     * Find progressions by interview readiness range
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore BETWEEN :minScore AND :maxScore " +
           "ORDER BY acp.interviewReadinessScore DESC")
    List<AmazonCompetencyProgression> findByInterviewReadinessRange(@Param("minScore") Integer minScore,
                                                                   @Param("maxScore") Integer maxScore);

    /**
     * Find progressions by cultural fit range
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.culturalFitScore BETWEEN :minScore AND :maxScore " +
           "ORDER BY acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findByCulturalFitRange(@Param("minScore") Integer minScore,
                                                            @Param("maxScore") Integer maxScore);

    /**
     * Get level distribution statistics
     */
    @Query("SELECT acp.currentAmazonLevel, COUNT(acp) FROM AmazonCompetencyProgression acp " +
           "GROUP BY acp.currentAmazonLevel ORDER BY acp.currentAmazonLevel")
    List<Object[]> getCurrentLevelDistribution();

    /**
     * Get target level distribution statistics
     */
    @Query("SELECT acp.targetAmazonLevel, COUNT(acp) FROM AmazonCompetencyProgression acp " +
           "GROUP BY acp.targetAmazonLevel ORDER BY acp.targetAmazonLevel")
    List<Object[]> getTargetLevelDistribution();

    /**
     * Get average scores by current level
     */
    @Query("SELECT acp.currentAmazonLevel, AVG(acp.interviewReadinessScore), AVG(acp.culturalFitScore) " +
           "FROM AmazonCompetencyProgression acp GROUP BY acp.currentAmazonLevel " +
           "ORDER BY acp.currentAmazonLevel")
    List<Object[]> getAverageScoresByLevel();

    /**
     * Find top performers by overall competency score
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp " +
           "ORDER BY (acp.interviewReadinessScore + acp.culturalFitScore) DESC")
    List<AmazonCompetencyProgression> findTopPerformers();

    /**
     * Find users needing development support
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "(acp.interviewReadinessScore + acp.culturalFitScore) / 2.0 < :minScore " +
           "ORDER BY (acp.interviewReadinessScore + acp.culturalFitScore) ASC")
    List<AmazonCompetencyProgression> findUsersNeedingDevelopment(@Param("minScore") Double minScore);

    /**
     * Find progressions with competency gaps
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.competencyGaps IS NOT NULL AND acp.competencyGaps != '' " +
           "ORDER BY acp.updatedAt DESC")
    List<AmazonCompetencyProgression> findProgressionsWithGaps();

    /**
     * Find progressions with leadership principles progress
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.leadershipPrinciplesProgress IS NOT NULL AND acp.leadershipPrinciplesProgress != '' " +
           "ORDER BY acp.updatedAt DESC")
    List<AmazonCompetencyProgression> findProgressionsWithLeadershipProgress();

    /**
     * Find progressions with technical competencies
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.technicalCompetencies IS NOT NULL AND acp.technicalCompetencies != '' " +
           "ORDER BY acp.updatedAt DESC")
    List<AmazonCompetencyProgression> findProgressionsWithTechnicalCompetencies();

    /**
     * Find progressions with behavioral competencies
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.behavioralCompetencies IS NOT NULL AND acp.behavioralCompetencies != '' " +
           "ORDER BY acp.updatedAt DESC")
    List<AmazonCompetencyProgression> findProgressionsWithBehavioralCompetencies();

    /**
     * Find recently updated progressions
     */
    List<AmazonCompetencyProgression> findByUpdatedAtAfterOrderByUpdatedAtDesc(LocalDateTime since);

    /**
     * Find recently assessed progressions
     */
    List<AmazonCompetencyProgression> findByLastAssessedAfterOrderByLastAssessedDesc(LocalDateTime since);

    /**
     * Count progressions by current level
     */
    long countByCurrentAmazonLevel(AmazonCompetencyProgression.AmazonLevel currentAmazonLevel);

    /**
     * Count progressions by target level
     */
    long countByTargetAmazonLevel(AmazonCompetencyProgression.AmazonLevel targetAmazonLevel);

    /**
     * Count users ready for promotion
     */
    @Query("SELECT COUNT(acp) FROM AmazonCompetencyProgression acp WHERE " +
           "(acp.currentAmazonLevel = 'L3' AND acp.interviewReadinessScore >= 70 AND acp.culturalFitScore >= 70) OR " +
           "(acp.currentAmazonLevel = 'L4' AND acp.interviewReadinessScore >= 80 AND acp.culturalFitScore >= 80) OR " +
           "(acp.currentAmazonLevel = 'L5' AND acp.interviewReadinessScore >= 90 AND acp.culturalFitScore >= 85)")
    long countUsersReadyForPromotion();

    /**
     * Count users exceeding hiring bar
     */
    @Query("SELECT COUNT(acp) FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore >= 85 AND acp.culturalFitScore >= 80")
    long countUsersExceedingHiringBar();

    /**
     * Count users meeting hiring bar
     */
    @Query("SELECT COUNT(acp) FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore >= 75 AND acp.culturalFitScore >= 70")
    long countUsersMeetingHiringBar();

    /**
     * Get competency progression statistics
     */
    @Query("SELECT COUNT(acp), AVG(acp.interviewReadinessScore), AVG(acp.culturalFitScore), " +
           "MIN(acp.interviewReadinessScore), MAX(acp.interviewReadinessScore), " +
           "MIN(acp.culturalFitScore), MAX(acp.culturalFitScore) " +
           "FROM AmazonCompetencyProgression acp")
    Object[] getCompetencyStatistics();

    /**
     * Find L5+ ready candidates
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.interviewReadinessScore >= 80 AND acp.culturalFitScore >= 80 " +
           "AND (acp.currentAmazonLevel = 'L4' OR acp.targetAmazonLevel IN ('L5', 'L6')) " +
           "ORDER BY acp.interviewReadinessScore DESC, acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findL5PlusReadyCandidates();

    /**
     * Find senior SDE candidates (L5/L6)
     */
    @Query("SELECT acp FROM AmazonCompetencyProgression acp WHERE " +
           "acp.targetAmazonLevel IN ('L5', 'L6') AND " +
           "acp.interviewReadinessScore >= 85 AND acp.culturalFitScore >= 80 " +
           "ORDER BY acp.interviewReadinessScore DESC, acp.culturalFitScore DESC")
    List<AmazonCompetencyProgression> findSeniorSDECandidates();
}