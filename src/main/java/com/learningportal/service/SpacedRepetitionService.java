package com.learningportal.service;

import com.learningportal.model.SpacedRepetitionSchedule;
import com.learningportal.repository.SpacedRepetitionScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for managing Spaced Repetition Scheduling
 * 
 * Implements scientifically-optimized review schedules for long-term retention
 * with Amazon interview preparation patterns and adaptive scheduling based on
 * individual learning patterns.
 */
@Service
@Transactional
public class SpacedRepetitionService {

    @Autowired
    private SpacedRepetitionScheduleRepository scheduleRepository;

    /**
     * Create or get existing spaced repetition schedule
     */
    public SpacedRepetitionSchedule getOrCreateSchedule(Long userId, Long contentId, String contentType) {
        return scheduleRepository.findByUserIdAndContentIdAndContentType(userId, contentId, contentType)
            .orElseGet(() -> {
                SpacedRepetitionSchedule schedule = new SpacedRepetitionSchedule(userId, contentId, contentType);
                return scheduleRepository.save(schedule);
            });
    }

    /**
     * Record a review session and update schedule
     */
    public SpacedRepetitionSchedule recordReview(Long userId, Long contentId, String contentType, 
                                               int performanceRating, int retentionScore) {
        SpacedRepetitionSchedule schedule = getOrCreateSchedule(userId, contentId, contentType);
        
        // Update retention score
        schedule.setRetentionScore(Math.max(0, Math.min(100, retentionScore)));
        
        // Calculate next interval based on performance
        schedule.calculateNextInterval(performanceRating);
        
        return scheduleRepository.save(schedule);
    }

    /**
     * Get due reviews for a user
     */
    @Transactional(readOnly = true)
    public List<SpacedRepetitionSchedule> getDueReviews(Long userId) {
        return scheduleRepository.findDueForReviewByUser(userId, LocalDateTime.now());
    }

    /**
     * Get next N reviews for a user
     */
    @Transactional(readOnly = true)
    public List<SpacedRepetitionSchedule> getNextReviews(Long userId, int count) {
        return scheduleRepository.findNextReviewsForUser(userId, PageRequest.of(0, count));
    }

    /**
     * Get overdue reviews for a user
     */
    @Transactional(readOnly = true)
    public List<SpacedRepetitionSchedule> getOverdueReviews(Long userId) {
        LocalDateTime overdueTime = LocalDateTime.now().minusDays(1);
        return scheduleRepository.findDueForReviewByUser(userId, overdueTime)
            .stream()
            .filter(schedule -> schedule.getNextReviewDate().isBefore(overdueTime))
            .collect(Collectors.toList());
    }

    /**
     * Get Amazon interview priority reviews
     */
    @Transactional(readOnly = true)
    public List<SpacedRepetitionSchedule> getAmazonInterviewPriorityReviews(Long userId) {
        return scheduleRepository.findByUserIdAndAmazonInterviewPriorityTrueOrderByNextReviewDateAsc(userId);
    }

    /**
     * Set Amazon interview priority for content
     */
    public SpacedRepetitionSchedule setAmazonInterviewPriority(Long userId, Long contentId, 
                                                             String contentType, boolean priority) {
        SpacedRepetitionSchedule schedule = getOrCreateSchedule(userId, contentId, contentType);
        schedule.setAmazonInterviewPriority(priority);
        
        // Adjust intervals for priority content
        if (priority && schedule.getRepetitionInterval() > 3) {
            schedule.setRepetitionInterval(Math.max(1, schedule.getRepetitionInterval() / 2));
            schedule.setNextReviewDate(LocalDateTime.now().plusDays(schedule.getRepetitionInterval()));
        }
        
        return scheduleRepository.save(schedule);
    }

    /**
     * Get daily review schedule for user
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getDailyReviewSchedule(Long userId, int days) {
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(days);
        
        List<SpacedRepetitionSchedule> schedules = scheduleRepository
            .findUserSchedulesInDateRange(userId, startDate, endDate);
        
        Map<String, List<SpacedRepetitionSchedule>> dailySchedule = schedules.stream()
            .collect(Collectors.groupingBy(
                schedule -> schedule.getNextReviewDate().toLocalDate().toString()
            ));
        
        Map<String, Object> result = new HashMap<>();
        result.put("dailySchedule", dailySchedule);
        result.put("totalReviews", schedules.size());
        result.put("averagePerDay", schedules.size() / (double) days);
        result.put("priorityReviews", schedules.stream()
            .mapToLong(s -> s.getAmazonInterviewPriority() ? 1 : 0).sum());
        
        return result;
    }

    /**
     * Get user learning analytics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getUserLearningAnalytics(Long userId) {
        Map<String, Object> analytics = new HashMap<>();
        
        // Basic statistics
        Object[] retentionStats = scheduleRepository.getRetentionStatisticsForUser(userId);
        Object[] repetitionStats = scheduleRepository.getRepetitionStatisticsForUser(userId);
        
        if (retentionStats != null && retentionStats.length > 0) {
            analytics.put("averageRetention", retentionStats[0]);
            analytics.put("minRetention", retentionStats[1]);
            analytics.put("maxRetention", retentionStats[2]);
            analytics.put("totalSchedules", retentionStats[3]);
        }
        
        if (repetitionStats != null && repetitionStats.length > 0) {
            analytics.put("averageRepetitions", repetitionStats[0]);
            analytics.put("totalRepetitions", repetitionStats[1]);
            analytics.put("averageInterval", repetitionStats[2]);
        }
        
        // Current status
        long dueCount = scheduleRepository.countDueForReviewByUser(userId, LocalDateTime.now());
        long overdueCount = scheduleRepository.countOverdueByUser(userId, LocalDateTime.now().minusDays(1));
        long amazonPriorityCount = scheduleRepository.countByUserIdAndAmazonInterviewPriorityTrue(userId);
        
        analytics.put("dueReviews", dueCount);
        analytics.put("overdueReviews", overdueCount);
        analytics.put("amazonPriorityItems", amazonPriorityCount);
        
        // Learning efficiency
        List<SpacedRepetitionSchedule> userSchedules = scheduleRepository.findByUserIdOrderByNextReviewDateAsc(userId);
        double averageEfficiency = userSchedules.stream()
            .mapToDouble(SpacedRepetitionSchedule::getLearningEfficiency)
            .average()
            .orElse(0.0);
        analytics.put("learningEfficiency", averageEfficiency);
        
        // Retention distribution
        Map<String, Long> retentionDistribution = userSchedules.stream()
            .collect(Collectors.groupingBy(
                SpacedRepetitionSchedule::getRetentionStrength,
                Collectors.counting()
            ));
        analytics.put("retentionDistribution", retentionDistribution);
        
        return analytics;
    }

    /**
     * Generate personalized review recommendations
     */
    public List<String> generateReviewRecommendations(Long userId) {
        List<String> recommendations = new ArrayList<>();
        
        List<SpacedRepetitionSchedule> userSchedules = scheduleRepository.findByUserIdOrderByNextReviewDateAsc(userId);
        
        if (userSchedules.isEmpty()) {
            recommendations.add("Start building your spaced repetition schedule by reviewing content");
            return recommendations;
        }
        
        // Check for overdue items
        long overdueCount = userSchedules.stream()
            .mapToLong(s -> s.isOverdue() ? 1 : 0)
            .sum();
        
        if (overdueCount > 0) {
            recommendations.add("You have " + overdueCount + " overdue reviews. Prioritize these to maintain retention.");
        }
        
        // Check for due items
        long dueCount = userSchedules.stream()
            .mapToLong(s -> s.isReviewDue() ? 1 : 0)
            .sum();
        
        if (dueCount > 0) {
            recommendations.add("You have " + dueCount + " reviews due today. Complete these to stay on track.");
        }
        
        // Check retention levels
        long lowRetentionCount = userSchedules.stream()
            .mapToLong(s -> s.getRetentionScore() < 70 ? 1 : 0)
            .sum();
        
        if (lowRetentionCount > 0) {
            recommendations.add("Focus on " + lowRetentionCount + " items with low retention scores for better mastery.");
        }
        
        // Amazon interview readiness
        long amazonReadyCount = userSchedules.stream()
            .mapToLong(s -> s.isAmazonInterviewReady() ? 1 : 0)
            .sum();
        
        long amazonPriorityCount = userSchedules.stream()
            .mapToLong(s -> s.getAmazonInterviewPriority() ? 1 : 0)
            .sum();
        
        if (amazonPriorityCount > 0) {
            double readinessPercentage = (double) amazonReadyCount / amazonPriorityCount * 100;
            recommendations.add(String.format("Amazon interview readiness: %.1f%% (%d/%d topics ready)", 
                                            readinessPercentage, amazonReadyCount, amazonPriorityCount));
        }
        
        // Learning efficiency recommendations
        double averageEfficiency = userSchedules.stream()
            .mapToDouble(SpacedRepetitionSchedule::getLearningEfficiency)
            .average()
            .orElse(0.0);
        
        if (averageEfficiency < 10) {
            recommendations.add("Consider reviewing study techniques - learning efficiency could be improved.");
        } else if (averageEfficiency > 20) {
            recommendations.add("Excellent learning efficiency! You're mastering content effectively.");
        }
        
        // Daily workload recommendations
        long todayReviews = userSchedules.stream()
            .mapToLong(s -> s.getDaysUntilNextReview() == 0 ? 1 : 0)
            .sum();
        
        if (todayReviews > 20) {
            recommendations.add("Heavy review day ahead (" + todayReviews + " items). Consider spreading reviews throughout the day.");
        }
        
        return recommendations;
    }

    /**
     * Optimize review schedule for Amazon interview preparation
     */
    public void optimizeForAmazonInterview(Long userId, LocalDateTime targetDate) {
        List<SpacedRepetitionSchedule> amazonSchedules = scheduleRepository
            .findByUserIdAndAmazonInterviewPriorityTrueOrderByNextReviewDateAsc(userId);
        
        long daysUntilInterview = java.time.Duration.between(LocalDateTime.now(), targetDate).toDays();
        
        for (SpacedRepetitionSchedule schedule : amazonSchedules) {
            // Increase review frequency as interview approaches
            if (daysUntilInterview <= 30) {
                // Intensive review phase
                schedule.setDifficultyAdjustment(BigDecimal.valueOf(0.5)); // More frequent reviews
            } else if (daysUntilInterview <= 60) {
                // Preparation phase
                schedule.setDifficultyAdjustment(BigDecimal.valueOf(0.7));
            }
            
            // Ensure minimum retention score for interview readiness
            if (schedule.getRetentionScore() < 85) {
                schedule.setRepetitionInterval(Math.max(1, schedule.getRepetitionInterval() / 2));
                schedule.setNextReviewDate(LocalDateTime.now().plusDays(schedule.getRepetitionInterval()));
            }
            
            scheduleRepository.save(schedule);
        }
    }

    /**
     * Get content difficulty analysis
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getContentDifficultyAnalysis() {
        List<Object[]> contentStats = scheduleRepository.findContentWithBestRetention(5L);
        
        Map<String, Object> analysis = new HashMap<>();
        
        List<Map<String, Object>> easyContent = new ArrayList<>();
        List<Map<String, Object>> difficultContent = new ArrayList<>();
        
        for (Object[] stat : contentStats) {
            Map<String, Object> contentInfo = new HashMap<>();
            contentInfo.put("contentId", stat[0]);
            contentInfo.put("contentType", stat[1]);
            contentInfo.put("averageRetention", stat[2]);
            contentInfo.put("reviewCount", stat[3]);
            
            Double avgRetention = (Double) stat[2];
            if (avgRetention >= 80) {
                easyContent.add(contentInfo);
            } else if (avgRetention < 60) {
                difficultContent.add(contentInfo);
            }
        }
        
        analysis.put("easyContent", easyContent);
        analysis.put("difficultContent", difficultContent);
        analysis.put("totalAnalyzedContent", contentStats.size());
        
        return analysis;
    }

    /**
     * Bulk update schedules for content type
     */
    public void bulkUpdateContentType(String contentType, BigDecimal difficultyAdjustment, 
                                    Boolean amazonPriority) {
        List<SpacedRepetitionSchedule> schedules = scheduleRepository
            .findByContentTypeOrderByNextReviewDateAsc(contentType);
        
        for (SpacedRepetitionSchedule schedule : schedules) {
            if (difficultyAdjustment != null) {
                schedule.setDifficultyAdjustment(difficultyAdjustment);
                // Recalculate next review date
                int newInterval = (int) Math.round(schedule.getRepetitionInterval() * difficultyAdjustment.doubleValue());
                schedule.setRepetitionInterval(Math.max(1, newInterval));
                schedule.setNextReviewDate(LocalDateTime.now().plusDays(schedule.getRepetitionInterval()));
            }
            
            if (amazonPriority != null) {
                schedule.setAmazonInterviewPriority(amazonPriority);
            }
        }
        
        scheduleRepository.saveAll(schedules);
    }

    /**
     * Get system-wide spaced repetition statistics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalSchedules = scheduleRepository.count();
        long dueReviews = scheduleRepository.countDueForReview(LocalDateTime.now());
        long overdueReviews = scheduleRepository.findOverdueSchedules(LocalDateTime.now().minusDays(1)).size();
        
        stats.put("totalSchedules", totalSchedules);
        stats.put("dueReviews", dueReviews);
        stats.put("overdueReviews", overdueReviews);
        stats.put("reviewCompletionRate", totalSchedules > 0 ? 
                 (double) (totalSchedules - overdueReviews) / totalSchedules * 100 : 0);
        
        // Most reviewed content
        List<Object[]> mostReviewed = scheduleRepository.findMostReviewedContent();
        stats.put("mostReviewedContent", mostReviewed.stream()
            .limit(10)
            .map(row -> Map.of(
                "contentId", row[0],
                "contentType", row[1],
                "averageRepetitions", row[2],
                "averageRetention", row[3]
            ))
            .collect(Collectors.toList()));
        
        // Amazon interview ready users
        List<Object[]> readyUsers = scheduleRepository.findAmazonInterviewReadyUsers(10L);
        stats.put("amazonInterviewReadyUsers", readyUsers.size());
        
        return stats;
    }
}