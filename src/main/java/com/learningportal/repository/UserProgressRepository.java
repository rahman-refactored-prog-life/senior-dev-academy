package com.learningportal.repository;

import com.learningportal.model.UserProgress;
import com.learningportal.model.UserProgress.ProgressStatus;
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
 * Repository interface for UserProgress entity.
 * 
 * Provides data access methods for user progress tracking including:
 * - CRUD operations
 * - Progress analytics and statistics
 * - User-specific progress queries
 * - Module and topic progress tracking
 */
@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {

    /**
     * Find progress by user ID and module ID
     */
    Optional<UserProgress> findByUserIdAndModuleId(Long userId, Long moduleId);
    
    /**
     * Find progress by user ID and topic ID
     */
    Optional<UserProgress> findByUserIdAndTopicId(Long userId, Long topicId);
    
    /**
     * Find all progress records for a user
     */
    List<UserProgress> findByUserIdOrderByUpdatedAtDesc(Long userId);
    
    /**
     * Find all progress records for a user with pagination
     */
    Page<UserProgress> findByUserIdOrderByUpdatedAtDesc(Long userId, Pageable pageable);
    
    /**
     * Find progress records by user and status
     */
    List<UserProgress> findByUserIdAndStatus(Long userId, ProgressStatus status);
    
    /**
     * Find progress records by module
     */
    List<UserProgress> findByModuleIdOrderByProgressPercentageDesc(Long moduleId);
    
    /**
     * Find progress records by topic
     */
    List<UserProgress> findByTopicIdOrderByProgressPercentageDesc(Long topicId);
    
    /**
     * Find recently accessed progress (within last N days)
     */
    @Query("SELECT up FROM UserProgress up WHERE up.userId = :userId AND up.lastAccessedAt >= :since ORDER BY up.lastAccessedAt DESC")
    List<UserProgress> findRecentlyAccessedByUser(@Param("userId") Long userId, @Param("since") LocalDateTime since);
    
    /**
     * Find active learning sessions (in progress status)
     */
    @Query("SELECT up FROM UserProgress up WHERE up.userId = :userId AND up.status = 'IN_PROGRESS' ORDER BY up.lastAccessedAt DESC")
    List<UserProgress> findActiveSessionsByUser(@Param("userId") Long userId);
    
    /**
     * Get user's overall progress statistics
     */
    @Query("""
        SELECT 
            COUNT(up) as totalItems,
            AVG(up.progressPercentage) as averageProgress,
            SUM(up.timeSpentMinutes) as totalTimeSpent,
            COUNT(CASE WHEN up.status = 'COMPLETED' THEN 1 END) as completedItems,
            COUNT(CASE WHEN up.status = 'IN_PROGRESS' THEN 1 END) as inProgressItems
        FROM UserProgress up 
        WHERE up.userId = :userId
        """)
    Object[] getUserProgressStatistics(@Param("userId") Long userId);
    
    /**
     * Get module completion statistics
     */
    @Query("""
        SELECT 
            up.module.id,
            up.module.name,
            COUNT(up) as totalUsers,
            AVG(up.progressPercentage) as averageProgress,
            COUNT(CASE WHEN up.status = 'COMPLETED' THEN 1 END) as completedUsers,
            AVG(up.timeSpentMinutes) as averageTimeSpent
        FROM UserProgress up 
        WHERE up.module IS NOT NULL
        GROUP BY up.module.id, up.module.name
        ORDER BY averageProgress DESC
        """)
    List<Object[]> getModuleCompletionStatistics();
    
    /**
     * Get user's learning streak (consecutive days with activity)
     */
    @Query(value = """
        WITH RECURSIVE date_series AS (
            SELECT DATE(MAX(last_accessed_at)) as access_date, 0 as day_offset
            FROM user_progress 
            WHERE user_id = :userId
            UNION ALL
            SELECT DATE(access_date - INTERVAL 1 DAY), day_offset + 1
            FROM date_series
            WHERE day_offset < 365
            AND EXISTS (
                SELECT 1 FROM user_progress 
                WHERE user_id = :userId 
                AND DATE(last_accessed_at) = DATE(access_date - INTERVAL 1 DAY)
            )
        )
        SELECT COUNT(*) FROM date_series
        """, nativeQuery = true)
    Integer getUserLearningStreak(@Param("userId") Long userId);
    
    /**
     * Find users with similar progress patterns (for recommendations)
     */
    @Query("""
        SELECT DISTINCT up2.userId
        FROM UserProgress up1, UserProgress up2
        WHERE up1.userId = :userId 
        AND up2.userId != :userId
        AND up1.module = up2.module
        AND up1.status = 'COMPLETED'
        AND up2.status = 'COMPLETED'
        GROUP BY up2.userId
        HAVING COUNT(up2.userId) >= :minCommonModules
        ORDER BY COUNT(up2.userId) DESC
        """)
    List<Long> findUsersWithSimilarProgress(@Param("userId") Long userId, @Param("minCommonModules") int minCommonModules);
    
    /**
     * Get learning velocity trends (progress per time period)
     */
    @Query("""
        SELECT 
            DATE(up.updatedAt) as date,
            SUM(up.progressPercentage) as dailyProgress,
            SUM(up.timeSpentMinutes) as dailyTimeSpent,
            COUNT(up) as dailyActivities
        FROM UserProgress up
        WHERE up.userId = :userId 
        AND up.updatedAt >= :since
        GROUP BY DATE(up.updatedAt)
        ORDER BY date DESC
        """)
    List<Object[]> getUserLearningVelocityTrends(@Param("userId") Long userId, @Param("since") LocalDateTime since);
    
    /**
     * Find modules that need review (completed but not accessed recently)
     */
    @Query("""
        SELECT up FROM UserProgress up
        WHERE up.userId = :userId
        AND up.status = 'COMPLETED'
        AND up.lastAccessedAt < :reviewThreshold
        ORDER BY up.completedAt ASC
        """)
    List<UserProgress> findModulesNeedingReview(@Param("userId") Long userId, @Param("reviewThreshold") LocalDateTime reviewThreshold);
    
    /**
     * Get user's favorite modules (highest rated)
     */
    @Query("""
        SELECT up FROM UserProgress up
        WHERE up.userId = :userId
        AND up.userRating IS NOT NULL
        ORDER BY up.userRating DESC, up.progressPercentage DESC
        """)
    List<UserProgress> getUserFavoriteModules(@Param("userId") Long userId, Pageable pageable);
    
    /**
     * Calculate user's learning efficiency (progress per minute)
     */
    @Query("""
        SELECT 
            AVG(CASE WHEN up.timeSpentMinutes > 0 THEN up.progressPercentage / up.timeSpentMinutes ELSE 0 END)
        FROM UserProgress up
        WHERE up.userId = :userId
        AND up.timeSpentMinutes > 0
        """)
    Double getUserLearningEfficiency(@Param("userId") Long userId);
    
    /**
     * Find progress records that haven't been updated recently (stale)
     */
    @Query("""
        SELECT up FROM UserProgress up
        WHERE up.userId = :userId
        AND up.status = 'IN_PROGRESS'
        AND up.lastAccessedAt < :staleThreshold
        ORDER BY up.lastAccessedAt ASC
        """)
    List<UserProgress> findStaleProgressRecords(@Param("userId") Long userId, @Param("staleThreshold") LocalDateTime staleThreshold);
    
    /**
     * Get completion rate by difficulty level
     */
    @Query("""
        SELECT 
            m.difficultyLevel,
            COUNT(up) as totalAttempts,
            COUNT(CASE WHEN up.status = 'COMPLETED' THEN 1 END) as completions,
            AVG(up.progressPercentage) as averageProgress
        FROM UserProgress up
        JOIN up.module m
        WHERE up.userId = :userId
        GROUP BY m.difficultyLevel
        ORDER BY m.difficultyLevel
        """)
    List<Object[]> getCompletionRateByDifficulty(@Param("userId") Long userId);
    
    /**
     * Check if user has progress for a specific module
     */
    boolean existsByUserIdAndModuleId(Long userId, Long moduleId);
    
    /**
     * Check if user has progress for a specific topic
     */
    boolean existsByUserIdAndTopicId(Long userId, Long topicId);
    
    /**
     * Count total progress records for a user
     */
    long countByUserId(Long userId);
    
    /**
     * Count completed items for a user
     */
    long countByUserIdAndStatus(Long userId, ProgressStatus status);
}