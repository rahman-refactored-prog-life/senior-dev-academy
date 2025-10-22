package com.learningportal.repository;

import com.learningportal.model.SpacedRepetitionSchedule;
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
 * Repository for Spaced Repetition Schedule entities
 * 
 * Provides data access methods for managing spaced repetition schedules
 * with scientifically-optimized review patterns and Amazon interview preparation.
 */
@Repository
public interface SpacedRepetitionScheduleRepository extends JpaRepository<SpacedRepetitionSchedule, Long> {

    /**
     * Find schedule for specific user and content
     */
    Optional<SpacedRepetitionSchedule> findByUserIdAndContentIdAndContentType(
        Long userId, Long contentId, String contentType);

    /**
     * Find all schedules for a user
     */
    List<SpacedRepetitionSchedule> findByUserIdOrderByNextReviewDateAsc(Long userId);

    /**
     * Find schedules due for review
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.nextReviewDate <= :currentTime " +
           "ORDER BY srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findDueForReview(@Param("currentTime") LocalDateTime currentTime);

    /**
     * Find overdue schedules
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.nextReviewDate < :overdueTime " +
           "ORDER BY srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findOverdueSchedules(@Param("overdueTime") LocalDateTime overdueTime);

    /**
     * Find schedules due for review for a specific user
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId " +
           "AND srs.nextReviewDate <= :currentTime ORDER BY srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findDueForReviewByUser(@Param("userId") Long userId, 
                                                          @Param("currentTime") LocalDateTime currentTime);

    /**
     * Find Amazon interview priority schedules
     */
    List<SpacedRepetitionSchedule> findByAmazonInterviewPriorityTrueOrderByNextReviewDateAsc();

    /**
     * Find Amazon interview priority schedules for user
     */
    List<SpacedRepetitionSchedule> findByUserIdAndAmazonInterviewPriorityTrueOrderByNextReviewDateAsc(Long userId);

    /**
     * Find schedules with low retention scores
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.retentionScore < :minScore " +
           "ORDER BY srs.retentionScore ASC, srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findLowRetentionSchedules(@Param("minScore") Integer minScore);

    /**
     * Find schedules with high retention scores
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.retentionScore >= :minScore " +
           "ORDER BY srs.retentionScore DESC")
    List<SpacedRepetitionSchedule> findHighRetentionSchedules(@Param("minScore") Integer minScore);

    /**
     * Find schedules by content type
     */
    List<SpacedRepetitionSchedule> findByContentTypeOrderByNextReviewDateAsc(String contentType);

    /**
     * Find schedules by content type for user
     */
    List<SpacedRepetitionSchedule> findByUserIdAndContentTypeOrderByNextReviewDateAsc(
        Long userId, String contentType);

    /**
     * Find schedules within date range
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.nextReviewDate BETWEEN :startDate AND :endDate " +
           "ORDER BY srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findSchedulesInDateRange(@Param("startDate") LocalDateTime startDate,
                                                            @Param("endDate") LocalDateTime endDate);

    /**
     * Find schedules for user within date range
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId " +
           "AND srs.nextReviewDate BETWEEN :startDate AND :endDate ORDER BY srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findUserSchedulesInDateRange(@Param("userId") Long userId,
                                                               @Param("startDate") LocalDateTime startDate,
                                                               @Param("endDate") LocalDateTime endDate);

    /**
     * Count schedules due for review
     */
    @Query("SELECT COUNT(srs) FROM SpacedRepetitionSchedule srs WHERE srs.nextReviewDate <= :currentTime")
    long countDueForReview(@Param("currentTime") LocalDateTime currentTime);

    /**
     * Count schedules due for review for user
     */
    @Query("SELECT COUNT(srs) FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId " +
           "AND srs.nextReviewDate <= :currentTime")
    long countDueForReviewByUser(@Param("userId") Long userId, @Param("currentTime") LocalDateTime currentTime);

    /**
     * Count overdue schedules for user
     */
    @Query("SELECT COUNT(srs) FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId " +
           "AND srs.nextReviewDate < :overdueTime")
    long countOverdueByUser(@Param("userId") Long userId, @Param("overdueTime") LocalDateTime overdueTime);

    /**
     * Get retention statistics for user
     */
    @Query("SELECT AVG(srs.retentionScore), MIN(srs.retentionScore), MAX(srs.retentionScore), " +
           "COUNT(srs) FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId")
    Object[] getRetentionStatisticsForUser(@Param("userId") Long userId);

    /**
     * Get repetition statistics for user
     */
    @Query("SELECT AVG(srs.repetitionCount), SUM(srs.repetitionCount), " +
           "AVG(srs.repetitionInterval) FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId")
    Object[] getRepetitionStatisticsForUser(@Param("userId") Long userId);

    /**
     * Find most reviewed content
     */
    @Query("SELECT srs.contentId, srs.contentType, AVG(srs.repetitionCount), AVG(srs.retentionScore) " +
           "FROM SpacedRepetitionSchedule srs GROUP BY srs.contentId, srs.contentType " +
           "ORDER BY AVG(srs.repetitionCount) DESC")
    List<Object[]> findMostReviewedContent();

    /**
     * Find content with best retention
     */
    @Query("SELECT srs.contentId, srs.contentType, AVG(srs.retentionScore), COUNT(srs) " +
           "FROM SpacedRepetitionSchedule srs GROUP BY srs.contentId, srs.contentType " +
           "HAVING COUNT(srs) >= :minReviews ORDER BY AVG(srs.retentionScore) DESC")
    List<Object[]> findContentWithBestRetention(@Param("minReviews") Long minReviews);

    /**
     * Find users ready for Amazon interviews
     */
    @Query("SELECT srs.userId, COUNT(srs), AVG(srs.retentionScore) FROM SpacedRepetitionSchedule srs " +
           "WHERE srs.amazonInterviewPriority = true AND srs.retentionScore >= 85 " +
           "AND srs.repetitionCount >= 3 GROUP BY srs.userId " +
           "HAVING COUNT(srs) >= :minTopics ORDER BY AVG(srs.retentionScore) DESC")
    List<Object[]> findAmazonInterviewReadyUsers(@Param("minTopics") Long minTopics);

    /**
     * Find schedules needing attention (low retention or overdue)
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE " +
           "(srs.retentionScore < :minRetention OR srs.nextReviewDate < :overdueTime) " +
           "ORDER BY srs.retentionScore ASC, srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findSchedulesNeedingAttention(@Param("minRetention") Integer minRetention,
                                                                @Param("overdueTime") LocalDateTime overdueTime);

    /**
     * Find user's next N reviews
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.userId = :userId " +
           "ORDER BY srs.nextReviewDate ASC")
    List<SpacedRepetitionSchedule> findNextReviewsForUser(@Param("userId") Long userId, Pageable pageable);

    /**
     * Find schedules by ease factor range
     */
    @Query("SELECT srs FROM SpacedRepetitionSchedule srs WHERE srs.easeFactor BETWEEN :minEase AND :maxEase " +
           "ORDER BY srs.easeFactor DESC")
    List<SpacedRepetitionSchedule> findByEaseFactorRange(@Param("minEase") java.math.BigDecimal minEase,
                                                         @Param("maxEase") java.math.BigDecimal maxEase);

    /**
     * Find learning efficiency leaders
     */
    @Query("SELECT srs.userId, AVG(srs.retentionScore / NULLIF(srs.repetitionCount, 0)) as efficiency " +
           "FROM SpacedRepetitionSchedule srs WHERE srs.repetitionCount > 0 " +
           "GROUP BY srs.userId ORDER BY efficiency DESC")
    List<Object[]> findLearningEfficiencyLeaders();

    /**
     * Get daily review distribution for user
     */
    @Query("SELECT DATE(srs.nextReviewDate), COUNT(srs) FROM SpacedRepetitionSchedule srs " +
           "WHERE srs.userId = :userId AND srs.nextReviewDate BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(srs.nextReviewDate) ORDER BY DATE(srs.nextReviewDate)")
    List<Object[]> getDailyReviewDistribution(@Param("userId") Long userId,
                                              @Param("startDate") LocalDateTime startDate,
                                              @Param("endDate") LocalDateTime endDate);

    /**
     * Find schedules with specific repetition count
     */
    List<SpacedRepetitionSchedule> findByRepetitionCountOrderByRetentionScoreDesc(Integer repetitionCount);

    /**
     * Find recently created schedules
     */
    List<SpacedRepetitionSchedule> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime since);

    /**
     * Find recently updated schedules
     */
    List<SpacedRepetitionSchedule> findByUpdatedAtAfterOrderByUpdatedAtDesc(LocalDateTime since);

    /**
     * Count total schedules for user
     */
    long countByUserId(Long userId);

    /**
     * Count Amazon priority schedules for user
     */
    long countByUserIdAndAmazonInterviewPriorityTrue(Long userId);
}