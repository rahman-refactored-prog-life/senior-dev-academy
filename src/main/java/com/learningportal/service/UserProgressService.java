package com.learningportal.service;

import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.model.User;
import com.learningportal.model.UserProgress;
import com.learningportal.model.UserProgress.ProgressStatus;
import com.learningportal.repository.UserProgressRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service layer for User Progress operations.
 * 
 * Provides comprehensive progress tracking and analytics including:
 * - Progress CRUD operations
 * - Learning analytics and statistics
 * - Progress visualization data
 * - Learning recommendations
 */
@Service
@Transactional
public class UserProgressService {

    private static final Logger log = LoggerFactory.getLogger(UserProgressService.class);
    
    private final UserProgressRepository progressRepository;
    
    public UserProgressService(UserProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    /**
     * Get or create progress record for user and module.
     * 
     * @param userId the user ID
     * @param moduleId the module ID
     * @return the progress record
     */
    @Transactional
    public UserProgress getOrCreateModuleProgress(Long userId, Long moduleId) {
        log.debug("Getting or creating module progress for user {} and module {}", userId, moduleId);
        
        Optional<UserProgress> existingProgress = progressRepository.findByUserIdAndModuleId(userId, moduleId);
        if (existingProgress.isPresent()) {
            return existingProgress.get();
        }
        
        // Create new progress record
        UserProgress newProgress = new UserProgress();
        newProgress.setUser(createUserReference(userId));
        newProgress.setModule(createModuleReference(moduleId));
        newProgress.setProgressPercentage(0);
        newProgress.setStatus(ProgressStatus.NOT_STARTED);
        newProgress.setTimeSpentMinutes(0);
        newProgress.setAccessCount(0);
        
        UserProgress savedProgress = progressRepository.save(newProgress);
        log.info("Created new module progress record with ID: {}", savedProgress.getId());
        return savedProgress;
    }

    /**
     * Get or create progress record for user and topic.
     * 
     * @param userId the user ID
     * @param topicId the topic ID
     * @return the progress record
     */
    @Transactional
    public UserProgress getOrCreateTopicProgress(Long userId, Long topicId) {
        log.debug("Getting or creating topic progress for user {} and topic {}", userId, topicId);
        
        Optional<UserProgress> existingProgress = progressRepository.findByUserIdAndTopicId(userId, topicId);
        if (existingProgress.isPresent()) {
            return existingProgress.get();
        }
        
        // Create new progress record
        UserProgress newProgress = new UserProgress();
        newProgress.setUser(createUserReference(userId));
        newProgress.setTopic(createTopicReference(topicId));
        newProgress.setProgressPercentage(0);
        newProgress.setStatus(ProgressStatus.NOT_STARTED);
        newProgress.setTimeSpentMinutes(0);
        newProgress.setAccessCount(0);
        
        UserProgress savedProgress = progressRepository.save(newProgress);
        log.info("Created new topic progress record with ID: {}", savedProgress.getId());
        return savedProgress;
    }

    /**
     * Update progress for a user and module.
     * 
     * @param userId the user ID
     * @param moduleId the module ID
     * @param progressPercentage the new progress percentage
     * @param timeSpentMinutes additional time spent
     * @return the updated progress record
     */
    @CacheEvict(value = "userProgress", key = "#userId")
    @Transactional
    public UserProgress updateModuleProgress(Long userId, Long moduleId, Integer progressPercentage, Integer timeSpentMinutes) {
        log.info("Updating module progress for user {} and module {}: {}%", userId, moduleId, progressPercentage);
        
        UserProgress progress = getOrCreateModuleProgress(userId, moduleId);
        
        // Update progress using business logic methods
        if (progressPercentage != null) {
            progress.updateProgress(progressPercentage);
        }
        
        if (timeSpentMinutes != null && timeSpentMinutes > 0) {
            progress.addTimeSpent(timeSpentMinutes);
        }
        
        UserProgress savedProgress = progressRepository.save(progress);
        log.info("Updated progress record ID: {}", savedProgress.getId());
        return savedProgress;
    }

    /**
     * Update progress for a user and topic.
     * 
     * @param userId the user ID
     * @param topicId the topic ID
     * @param progressPercentage the new progress percentage
     * @param timeSpentMinutes additional time spent
     * @return the updated progress record
     */
    @CacheEvict(value = "userProgress", key = "#userId")
    @Transactional
    public UserProgress updateTopicProgress(Long userId, Long topicId, Integer progressPercentage, Integer timeSpentMinutes) {
        log.info("Updating topic progress for user {} and topic {}: {}%", userId, topicId, progressPercentage);
        
        UserProgress progress = getOrCreateTopicProgress(userId, topicId);
        
        // Update progress using business logic methods
        if (progressPercentage != null) {
            progress.updateProgress(progressPercentage);
        }
        
        if (timeSpentMinutes != null && timeSpentMinutes > 0) {
            progress.addTimeSpent(timeSpentMinutes);
        }
        
        UserProgress savedProgress = progressRepository.save(progress);
        log.info("Updated progress record ID: {}", savedProgress.getId());
        return savedProgress;
    }

    /**
     * Get all progress records for a user.
     * 
     * @param userId the user ID
     * @param pageable pagination parameters
     * @return paginated progress records
     */
    @Cacheable(value = "userProgress", key = "#userId + '_page_' + #pageable.pageNumber")
    @Transactional(readOnly = true)
    public Page<UserProgress> getUserProgress(Long userId, Pageable pageable) {
        log.debug("Fetching progress for user: {}", userId);
        return progressRepository.findByUserIdOrderByUpdatedAtDesc(userId, pageable);
    }

    /**
     * Get user's progress statistics.
     * 
     * @param userId the user ID
     * @return comprehensive progress statistics
     */
    @Cacheable(value = "userProgress", key = "#userId + '_stats'")
    @Transactional(readOnly = true)
    public Map<String, Object> getUserProgressStatistics(Long userId) {
        log.debug("Calculating progress statistics for user: {}", userId);
        
        Object[] stats = progressRepository.getUserProgressStatistics(userId);
        
        Map<String, Object> statistics = new HashMap<>();
        if (stats != null && stats.length > 0) {
            statistics.put("totalItems", stats[0] != null ? stats[0] : 0L);
            statistics.put("averageProgress", stats[1] != null ? Math.round(((Double) stats[1]) * 100.0) / 100.0 : 0.0);
            statistics.put("totalTimeSpent", stats[2] != null ? stats[2] : 0L);
            statistics.put("completedItems", stats[3] != null ? stats[3] : 0L);
            statistics.put("inProgressItems", stats[4] != null ? stats[4] : 0L);
            
            // Calculate completion rate
            Long totalItems = (Long) statistics.get("totalItems");
            Long completedItems = (Long) statistics.get("completedItems");
            double completionRate = totalItems > 0 ? (completedItems.doubleValue() / totalItems.doubleValue()) * 100 : 0.0;
            statistics.put("completionRate", Math.round(completionRate * 100.0) / 100.0);
            
            // Add learning streak
            Integer learningStreak = progressRepository.getUserLearningStreak(userId);
            statistics.put("learningStreak", learningStreak != null ? learningStreak : 0);
            
            // Add learning efficiency
            Double efficiency = progressRepository.getUserLearningEfficiency(userId);
            statistics.put("learningEfficiency", efficiency != null ? Math.round(efficiency * 10000.0) / 10000.0 : 0.0);
        } else {
            // Default values for new users
            statistics.put("totalItems", 0L);
            statistics.put("averageProgress", 0.0);
            statistics.put("totalTimeSpent", 0L);
            statistics.put("completedItems", 0L);
            statistics.put("inProgressItems", 0L);
            statistics.put("completionRate", 0.0);
            statistics.put("learningStreak", 0);
            statistics.put("learningEfficiency", 0.0);
        }
        
        return statistics;
    }

    /**
     * Get user's recent learning activity.
     * 
     * @param userId the user ID
     * @param days number of days to look back
     * @return list of recent progress records
     */
    @Transactional(readOnly = true)
    public List<UserProgress> getRecentActivity(Long userId, int days) {
        log.debug("Fetching recent activity for user {} (last {} days)", userId, days);
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        return progressRepository.findRecentlyAccessedByUser(userId, since);
    }

    /**
     * Get user's active learning sessions.
     * 
     * @param userId the user ID
     * @return list of in-progress items
     */
    @Transactional(readOnly = true)
    public List<UserProgress> getActiveSessions(Long userId) {
        log.debug("Fetching active sessions for user: {}", userId);
        return progressRepository.findActiveSessionsByUser(userId);
    }

    /**
     * Get learning velocity trends for visualization.
     * 
     * @param userId the user ID
     * @param days number of days to analyze
     * @return learning velocity data for charts
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getLearningVelocityTrends(Long userId, int days) {
        log.debug("Calculating learning velocity trends for user {} (last {} days)", userId, days);
        
        LocalDateTime since = LocalDateTime.now().minusDays(days);
        List<Object[]> trends = progressRepository.getUserLearningVelocityTrends(userId, since);
        
        return trends.stream()
            .map(trend -> {
                Map<String, Object> trendData = new HashMap<>();
                trendData.put("date", trend[0]);
                trendData.put("dailyProgress", trend[1] != null ? trend[1] : 0);
                trendData.put("dailyTimeSpent", trend[2] != null ? trend[2] : 0);
                trendData.put("dailyActivities", trend[3] != null ? trend[3] : 0);
                return trendData;
            })
            .toList();
    }

    /**
     * Get modules that need review.
     * 
     * @param userId the user ID
     * @param reviewThresholdDays days since last access to consider for review
     * @return list of modules needing review
     */
    @Transactional(readOnly = true)
    public List<UserProgress> getModulesNeedingReview(Long userId, int reviewThresholdDays) {
        log.debug("Finding modules needing review for user {} (threshold: {} days)", userId, reviewThresholdDays);
        LocalDateTime reviewThreshold = LocalDateTime.now().minusDays(reviewThresholdDays);
        return progressRepository.findModulesNeedingReview(userId, reviewThreshold);
    }

    /**
     * Get user's favorite modules (highest rated).
     * 
     * @param userId the user ID
     * @param limit maximum number of results
     * @return list of favorite modules
     */
    @Transactional(readOnly = true)
    public List<UserProgress> getFavoriteModules(Long userId, int limit) {
        log.debug("Fetching favorite modules for user {} (limit: {})", userId, limit);
        Pageable pageable = PageRequest.of(0, limit);
        return progressRepository.getUserFavoriteModules(userId, pageable);
    }

    /**
     * Rate a learning item.
     * 
     * @param progressId the progress record ID
     * @param rating the rating (1-5)
     * @param notes optional notes
     * @return the updated progress record
     */
    @CacheEvict(value = "userProgress", allEntries = true)
    @Transactional
    public UserProgress rateProgress(Long progressId, Integer rating, String notes) {
        log.info("Rating progress record {} with rating: {}", progressId, rating);
        
        UserProgress progress = progressRepository.findById(progressId)
            .orElseThrow(() -> new EntityNotFoundException("Progress record not found with ID: " + progressId));
        
        progress.setUserRating(rating);
        if (notes != null) {
            progress.setNotes(notes);
        }
        progress.updateLastAccessed();
        
        UserProgress savedProgress = progressRepository.save(progress);
        log.info("Updated rating for progress record ID: {}", savedProgress.getId());
        return savedProgress;
    }

    /**
     * Get completion rate by difficulty level.
     * 
     * @param userId the user ID
     * @return completion statistics by difficulty
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getCompletionRateByDifficulty(Long userId) {
        log.debug("Calculating completion rate by difficulty for user: {}", userId);
        
        List<Object[]> results = progressRepository.getCompletionRateByDifficulty(userId);
        
        return results.stream()
            .map(result -> {
                Map<String, Object> difficultyData = new HashMap<>();
                difficultyData.put("difficulty", result[0]);
                difficultyData.put("totalAttempts", result[1] != null ? result[1] : 0L);
                difficultyData.put("completions", result[2] != null ? result[2] : 0L);
                difficultyData.put("averageProgress", result[3] != null ? Math.round(((Double) result[3]) * 100.0) / 100.0 : 0.0);
                
                // Calculate completion rate
                Long total = (Long) difficultyData.get("totalAttempts");
                Long completed = (Long) difficultyData.get("completions");
                double rate = total > 0 ? (completed.doubleValue() / total.doubleValue()) * 100 : 0.0;
                difficultyData.put("completionRate", Math.round(rate * 100.0) / 100.0);
                
                return difficultyData;
            })
            .toList();
    }

    /**
     * Delete a progress record.
     * 
     * @param progressId the progress record ID
     */
    @CacheEvict(value = "userProgress", allEntries = true)
    @Transactional
    public void deleteProgress(Long progressId) {
        log.info("Deleting progress record with ID: {}", progressId);
        
        if (!progressRepository.existsById(progressId)) {
            throw new EntityNotFoundException("Progress record not found with ID: " + progressId);
        }
        
        progressRepository.deleteById(progressId);
        log.info("Deleted progress record with ID: {}", progressId);
    }

    // Helper methods to create entity references without full loading
    private User createUserReference(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
    
    private LearningModule createModuleReference(Long moduleId) {
        LearningModule module = new LearningModule();
        module.setId(moduleId);
        return module;
    }
    
    private Topic createTopicReference(Long topicId) {
        Topic topic = new Topic();
        topic.setId(topicId);
        return topic;
    }
}