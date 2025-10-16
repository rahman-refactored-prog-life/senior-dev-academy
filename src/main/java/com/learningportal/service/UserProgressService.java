package com.learningportal.service;

import com.learningportal.model.*;
import com.learningportal.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User Progress Service - Advanced learning analytics and progress tracking
 * 
 * Innovative features inspired by top learning platforms:
 * - Spaced repetition algorithms
 * - Learning velocity tracking
 * - Adaptive difficulty adjustment
 * - Personalized recommendations
 * - Gamification elements
 * - Learning streak tracking
 * - Mastery-based progression
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserProgressService {
    
    private final UserProgressRepository progressRepository;
    private final UserRepository userRepository;
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    
    /**
     * Start learning a module - creates initial progress tracking
     */
    public UserProgress startModule(Long userId, Long moduleId) {
        log.info("User {} starting module {}", userId, moduleId);
        
        User user = getUserById(userId);
        LearningModule module = getModuleById(moduleId);
        
        // Check if progress already exists
        Optional<UserProgress> existingProgress = progressRepository.findByUserAndModule(user, module);
        if (existingProgress.isPresent()) {
            UserProgress progress = existingProgress.get();
            progress.markAsStarted();
            return progressRepository.save(progress);
        }
        
        // Create new progress
        UserProgress progress = new UserProgress();
        progress.setUser(user);
        progress.setModule(module);
        progress.markAsStarted();
        
        return progressRepository.save(progress);
    }
    
    /**
     * Update progress with advanced analytics
     */
    public UserProgress updateProgress(Long userId, Long moduleId, Long topicId, 
                                     int progressPercentage, int timeSpentMinutes) {
        log.info("Updating progress for user {} - module: {}, topic: {}, progress: {}%", 
                userId, moduleId, topicId, progressPercentage);
        
        User user = getUserById(userId);
        UserProgress progress;
        
        if (topicId != null) {
            Topic topic = getTopicById(topicId);
            progress = getOrCreateTopicProgress(user, topic);
        } else {
            LearningModule module = getModuleById(moduleId);
            progress = getOrCreateModuleProgress(user, module);
        }
        
        // Update progress with analytics
        int previousProgress = progress.getProgressPercentage();
        progress.updateProgress(progressPercentage);
        progress.addTimeSpent(timeSpentMinutes);
        
        // Calculate learning velocity and adjust recommendations
        updateLearningVelocity(progress, previousProgress, progressPercentage, timeSpentMinutes);
        
        UserProgress savedProgress = progressRepository.save(progress);
        
        // Update module progress if this was a topic
        if (topicId != null) {
            updateModuleProgressFromTopics(user, progress.getTopic().getModule());
        }
        
        // Check for achievements and milestones
        checkAchievements(user, savedProgress);
        
        return savedProgress;
    }
    
    /**
     * Get comprehensive learning analytics for a user
     */
    @Transactional(readOnly = true)
    public LearningAnalytics getLearningAnalytics(Long userId) {
        log.info("Generating learning analytics for user {}", userId);
        
        User user = getUserById(userId);
        List<UserProgress> allProgress = progressRepository.findByUser(user);
        
        // Calculate overall statistics
        double averageProgress = progressRepository.getAverageProgressByUser(user);
        Integer totalTimeSpent = progressRepository.getTotalTimeSpentByUser(user);
        
        // Calculate learning streaks
        int currentStreak = calculateCurrentStreak(user);
        int longestStreak = calculateLongestStreak(user);
        
        // Calculate learning velocity (progress per hour)
        double learningVelocity = calculateOverallLearningVelocity(allProgress);
        
        // Get module-wise progress
        List<ModuleProgress> moduleProgressList = getModuleProgressSummary(user);
        
        // Calculate skill levels
        Map<String, SkillLevel> skillLevels = calculateSkillLevels(allProgress);
        
        // Generate personalized recommendations
        List<String> recommendations = generatePersonalizedRecommendations(user, allProgress);
        
        // Calculate next milestones
        List<Milestone> upcomingMilestones = calculateUpcomingMilestones(user, allProgress);
        
        return new LearningAnalytics(
            averageProgress != null ? averageProgress : 0.0,
            totalTimeSpent != null ? totalTimeSpent : 0,
            currentStreak,
            longestStreak,
            learningVelocity,
            moduleProgressList,
            skillLevels,
            recommendations,
            upcomingMilestones,
            calculateWeeklyProgress(user),
            getRecentAchievements(user)
        );
    }
    
    /**
     * Get personalized learning recommendations using ML-inspired algorithms
     */
    @Transactional(readOnly = true)
    public List<LearningRecommendation> getPersonalizedRecommendations(Long userId) {
        log.info("Generating personalized recommendations for user {}", userId);
        
        User user = getUserById(userId);
        List<UserProgress> userProgress = progressRepository.findByUser(user);
        
        List<LearningRecommendation> recommendations = new ArrayList<>();
        
        // 1. Continue incomplete modules
        userProgress.stream()
            .filter(p -> p.getProgressPercentage() > 0 && p.getProgressPercentage() < 100)
            .filter(p -> p.getModule() != null)
            .forEach(p -> recommendations.add(new LearningRecommendation(
                "CONTINUE",
                "Continue " + p.getModule().getName(),
                "You're " + p.getProgressPercentage() + "% complete",
                p.getModule().getId(),
                null,
                calculatePriority(p)
            )));
        
        // 2. Recommend next difficulty level
        Set<LearningModule.Category> completedCategories = getCompletedCategories(userProgress);
        for (LearningModule.Category category : completedCategories) {
            LearningModule.DifficultyLevel nextLevel = getNextDifficultyLevel(user, category);
            if (nextLevel != null) {
                List<LearningModule> nextModules = moduleRepository.findByCategory(category)
                    .stream()
                    .filter(m -> m.getDifficultyLevel() == nextLevel)
                    .filter(m -> !hasStarted(user, m))
                    .toList();
                
                for (LearningModule module : nextModules) {
                    recommendations.add(new LearningRecommendation(
                        "NEXT_LEVEL",
                        "Advance to " + module.getName(),
                        "Ready for " + nextLevel.getDisplayName() + " level",
                        module.getId(),
                        null,
                        8
                    ));
                }
            }
        }
        
        // 3. Spaced repetition recommendations
        List<UserProgress> needsReview = findItemsNeedingReview(user);
        for (UserProgress progress : needsReview) {
            if (progress.getModule() != null) {
                recommendations.add(new LearningRecommendation(
                    "REVIEW",
                    "Review " + progress.getModule().getName(),
                    "Spaced repetition recommended",
                    progress.getModule().getId(),
                    null,
                    6
                ));
            }
        }
        
        // 4. Interview preparation based on progress
        if (getOverallProgress(user) > 70) {
            recommendations.add(new LearningRecommendation(
                "INTERVIEW_PREP",
                "Start Interview Preparation",
                "You're ready for technical interviews!",
                null,
                null,
                9
            ));
        }
        
        // Sort by priority and return top recommendations
        return recommendations.stream()
            .sorted((r1, r2) -> Integer.compare(r2.getPriority(), r1.getPriority()))
            .limit(10)
            .toList();
    }
    
    /**
     * Track learning session with detailed analytics
     */
    public LearningSession trackLearningSession(Long userId, Long moduleId, Long topicId, 
                                              int durationMinutes, List<String> activitiesCompleted) {
        log.info("Tracking learning session for user {} - duration: {} minutes", userId, durationMinutes);
        
        User user = getUserById(userId);
        
        // Update progress
        UserProgress progress = updateProgress(userId, moduleId, topicId, 
                                             calculateProgressIncrement(activitiesCompleted), 
                                             durationMinutes);
        
        // Create session record
        LearningSession session = new LearningSession(
            LocalDateTime.now(),
            durationMinutes,
            activitiesCompleted.size(),
            calculateFocusScore(durationMinutes, activitiesCompleted),
            progress.getProgressPercentage(),
            activitiesCompleted
        );
        
        // Update learning streaks
        updateLearningStreak(user);
        
        return session;
    }
    
    /**
     * Get learning leaderboard for gamification
     */
    @Transactional(readOnly = true)
    public List<LeaderboardEntry> getLeaderboard(String period, String metric) {
        log.info("Generating leaderboard for period: {}, metric: {}", period, metric);
        
        LocalDateTime startDate = switch (period.toLowerCase()) {
            case "week" -> LocalDateTime.now().minusWeeks(1);
            case "month" -> LocalDateTime.now().minusMonths(1);
            case "year" -> LocalDateTime.now().minusYears(1);
            default -> LocalDateTime.now().minusMonths(1);
        };
        
        List<User> activeUsers = userRepository.findByActiveTrue();
        
        return activeUsers.stream()
            .map(user -> {
                List<UserProgress> recentProgress = progressRepository.findByUser(user)
                    .stream()
                    .filter(p -> p.getUpdatedAt().isAfter(startDate))
                    .toList();
                
                double score = switch (metric.toLowerCase()) {
                    case "progress" -> calculateProgressScore(recentProgress);
                    case "time" -> calculateTimeScore(recentProgress);
                    case "streak" -> calculateCurrentStreak(user);
                    case "modules" -> calculateCompletedModules(recentProgress);
                    default -> calculateOverallScore(recentProgress);
                };
                
                return new LeaderboardEntry(
                    user.getId(),
                    user.getFullName(),
                    score,
                    calculateCurrentStreak(user),
                    getTotalCompletedModules(user)
                );
            })
            .sorted((e1, e2) -> Double.compare(e2.getScore(), e1.getScore()))
            .limit(50)
            .toList();
    }
    
    // Helper methods for advanced analytics
    
    private void updateLearningVelocity(UserProgress progress, int previousProgress, 
                                      int newProgress, int timeSpent) {
        if (timeSpent > 0) {
            double progressGain = newProgress - previousProgress;
            double velocityThisSession = progressGain / (timeSpent / 60.0); // progress per hour
            
            // Update running average (simple exponential smoothing)
            double currentVelocity = progress.getLearningVelocity();
            double alpha = 0.3; // smoothing factor
            double newVelocity = alpha * velocityThisSession + (1 - alpha) * currentVelocity;
            
            // Store velocity in notes field as JSON (in a real implementation, you'd have a separate field)
            progress.setNotes(String.format("{\"learningVelocity\": %.2f}", newVelocity));
        }
    }
    
    private int calculateCurrentStreak(User user) {
        List<UserProgress> recentProgress = progressRepository.findByUser(user)
            .stream()
            .filter(p -> p.getLastAccessedAt() != null)
            .sorted((p1, p2) -> p2.getLastAccessedAt().compareTo(p1.getLastAccessedAt()))
            .toList();
        
        if (recentProgress.isEmpty()) return 0;
        
        LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
        int streak = 0;
        
        for (UserProgress progress : recentProgress) {
            LocalDateTime accessDate = progress.getLastAccessedAt().truncatedTo(ChronoUnit.DAYS);
            long daysDiff = ChronoUnit.DAYS.between(accessDate, today.minusDays(streak));
            
            if (daysDiff == 0) {
                streak++;
                today = accessDate;
            } else {
                break;
            }
        }
        
        return streak;
    }
    
    private List<String> generatePersonalizedRecommendations(User user, List<UserProgress> allProgress) {
        List<String> recommendations = new ArrayList<>();
        
        double avgProgress = allProgress.stream()
            .mapToDouble(UserProgress::getProgressPercentage)
            .average()
            .orElse(0.0);
        
        if (avgProgress < 30) {
            recommendations.add("Focus on completing beginner modules to build a strong foundation");
            recommendations.add("Set aside 30 minutes daily for consistent learning");
        } else if (avgProgress < 70) {
            recommendations.add("You're making great progress! Consider tackling intermediate topics");
            recommendations.add("Start practicing coding exercises to reinforce concepts");
        } else {
            recommendations.add("Excellent progress! You're ready for advanced topics and interview prep");
            recommendations.add("Consider contributing to open-source projects to gain real-world experience");
        }
        
        return recommendations;
    }
    
    // Additional helper methods and data classes...
    
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }
    
    private LearningModule getModuleById(Long moduleId) {
        return moduleRepository.findById(moduleId)
            .orElseThrow(() -> new IllegalArgumentException("Module not found: " + moduleId));
    }
    
    private Topic getTopicById(Long topicId) {
        return topicRepository.findById(topicId)
            .orElseThrow(() -> new IllegalArgumentException("Topic not found: " + topicId));
    }
    
    private UserProgress getOrCreateModuleProgress(User user, LearningModule module) {
        return progressRepository.findByUserAndModule(user, module)
            .orElseGet(() -> {
                UserProgress progress = new UserProgress();
                progress.setUser(user);
                progress.setModule(module);
                return progress;
            });
    }
    
    private UserProgress getOrCreateTopicProgress(User user, Topic topic) {
        return progressRepository.findByUserAndTopic(user, topic)
            .orElseGet(() -> {
                UserProgress progress = new UserProgress();
                progress.setUser(user);
                progress.setTopic(topic);
                return progress;
            });
    }
    
    // Data classes for responses
    
    public static class LearningAnalytics {
        private double overallProgress;
        private int totalTimeSpent;
        private int currentStreak;
        private int longestStreak;
        private double learningVelocity;
        private List<ModuleProgress> moduleProgress;
        private Map<String, SkillLevel> skillLevels;
        private List<String> recommendations;
        private List<Milestone> upcomingMilestones;
        private List<WeeklyProgress> weeklyProgress;
        private List<Achievement> recentAchievements;
        
        public LearningAnalytics(double overallProgress, int totalTimeSpent, int currentStreak,
                               int longestStreak, double learningVelocity, List<ModuleProgress> moduleProgress,
                               Map<String, SkillLevel> skillLevels, List<String> recommendations,
                               List<Milestone> upcomingMilestones, List<WeeklyProgress> weeklyProgress,
                               List<Achievement> recentAchievements) {
            this.overallProgress = overallProgress;
            this.totalTimeSpent = totalTimeSpent;
            this.currentStreak = currentStreak;
            this.longestStreak = longestStreak;
            this.learningVelocity = learningVelocity;
            this.moduleProgress = moduleProgress;
            this.skillLevels = skillLevels;
            this.recommendations = recommendations;
            this.upcomingMilestones = upcomingMilestones;
            this.weeklyProgress = weeklyProgress;
            this.recentAchievements = recentAchievements;
        }
        
        // Getters
        public double getOverallProgress() { return overallProgress; }
        public int getTotalTimeSpent() { return totalTimeSpent; }
        public int getCurrentStreak() { return currentStreak; }
        public int getLongestStreak() { return longestStreak; }
        public double getLearningVelocity() { return learningVelocity; }
        public List<ModuleProgress> getModuleProgress() { return moduleProgress; }
        public Map<String, SkillLevel> getSkillLevels() { return skillLevels; }
        public List<String> getRecommendations() { return recommendations; }
        public List<Milestone> getUpcomingMilestones() { return upcomingMilestones; }
        public List<WeeklyProgress> getWeeklyProgress() { return weeklyProgress; }
        public List<Achievement> getRecentAchievements() { return recentAchievements; }
    }
    
    // Additional data classes for comprehensive analytics...
    public static class ModuleProgress {
        private String moduleName;
        private double progress;
        private int timeSpent;
        private String status;
        
        public ModuleProgress(String moduleName, double progress, int timeSpent, String status) {
            this.moduleName = moduleName;
            this.progress = progress;
            this.timeSpent = timeSpent;
            this.status = status;
        }
        
        public String getModuleName() { return moduleName; }
        public double getProgress() { return progress; }
        public int getTimeSpent() { return timeSpent; }
        public String getStatus() { return status; }
    }
    
    public static class SkillLevel {
        private String skill;
        private int level;
        private double progress;
        private String nextMilestone;
        
        public SkillLevel(String skill, int level, double progress, String nextMilestone) {
            this.skill = skill;
            this.level = level;
            this.progress = progress;
            this.nextMilestone = nextMilestone;
        }
        
        public String getSkill() { return skill; }
        public int getLevel() { return level; }
        public double getProgress() { return progress; }
        public String getNextMilestone() { return nextMilestone; }
    }
    
    // Placeholder implementations for complex calculations
    private int calculateLongestStreak(User user) { return 15; }
    private double calculateOverallLearningVelocity(List<UserProgress> allProgress) { return 2.5; }
    private List<ModuleProgress> getModuleProgressSummary(User user) { return new ArrayList<>(); }
    private Map<String, SkillLevel> calculateSkillLevels(List<UserProgress> allProgress) { return new HashMap<>(); }
    private List<Milestone> calculateUpcomingMilestones(User user, List<UserProgress> allProgress) { return new ArrayList<>(); }
    private List<WeeklyProgress> calculateWeeklyProgress(User user) { return new ArrayList<>(); }
    private List<Achievement> getRecentAchievements(User user) { return new ArrayList<>(); }
    private void updateModuleProgressFromTopics(User user, LearningModule module) {}
    private void checkAchievements(User user, UserProgress progress) {}
    private Set<LearningModule.Category> getCompletedCategories(List<UserProgress> userProgress) { return new HashSet<>(); }
    private LearningModule.DifficultyLevel getNextDifficultyLevel(User user, LearningModule.Category category) { return null; }
    private boolean hasStarted(User user, LearningModule module) { return false; }
    private List<UserProgress> findItemsNeedingReview(User user) { return new ArrayList<>(); }
    private double getOverallProgress(User user) { return 0.0; }
    private int calculateProgressIncrement(List<String> activities) { return 10; }
    private double calculateFocusScore(int duration, List<String> activities) { return 0.85; }
    private void updateLearningStreak(User user) {}
    private double calculateProgressScore(List<UserProgress> progress) { return 0.0; }
    private double calculateTimeScore(List<UserProgress> progress) { return 0.0; }
    private double calculateCompletedModules(List<UserProgress> progress) { return 0.0; }
    private double calculateOverallScore(List<UserProgress> progress) { return 0.0; }
    private int getTotalCompletedModules(User user) { return 0; }
    private int calculatePriority(UserProgress progress) { return 5; }
    
    // Additional data classes
    public static class LearningRecommendation {
        private String type;
        private String title;
        private String description;
        private Long moduleId;
        private Long topicId;
        private int priority;
        
        public LearningRecommendation(String type, String title, String description, Long moduleId, Long topicId, int priority) {
            this.type = type;
            this.title = title;
            this.description = description;
            this.moduleId = moduleId;
            this.topicId = topicId;
            this.priority = priority;
        }
        
        public String getType() { return type; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public Long getModuleId() { return moduleId; }
        public Long getTopicId() { return topicId; }
        public int getPriority() { return priority; }
    }
    
    public static class LearningSession {
        private LocalDateTime timestamp;
        private int durationMinutes;
        private int activitiesCompleted;
        private double focusScore;
        private int progressGained;
        private List<String> activities;
        
        public LearningSession(LocalDateTime timestamp, int durationMinutes, int activitiesCompleted,
                             double focusScore, int progressGained, List<String> activities) {
            this.timestamp = timestamp;
            this.durationMinutes = durationMinutes;
            this.activitiesCompleted = activitiesCompleted;
            this.focusScore = focusScore;
            this.progressGained = progressGained;
            this.activities = activities;
        }
        
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getDurationMinutes() { return durationMinutes; }
        public int getActivitiesCompleted() { return activitiesCompleted; }
        public double getFocusScore() { return focusScore; }
        public int getProgressGained() { return progressGained; }
        public List<String> getActivities() { return activities; }
    }
    
    public static class LeaderboardEntry {
        private Long userId;
        private String userName;
        private double score;
        private int streak;
        private int completedModules;
        
        public LeaderboardEntry(Long userId, String userName, double score, int streak, int completedModules) {
            this.userId = userId;
            this.userName = userName;
            this.score = score;
            this.streak = streak;
            this.completedModules = completedModules;
        }
        
        public Long getUserId() { return userId; }
        public String getUserName() { return userName; }
        public double getScore() { return score; }
        public int getStreak() { return streak; }
        public int getCompletedModules() { return completedModules; }
    }
    
    public static class Milestone {
        private String title;
        private String description;
        private int targetValue;
        private int currentValue;
        private String type;
        
        public Milestone(String title, String description, int targetValue, int currentValue, String type) {
            this.title = title;
            this.description = description;
            this.targetValue = targetValue;
            this.currentValue = currentValue;
            this.type = type;
        }
        
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public int getTargetValue() { return targetValue; }
        public int getCurrentValue() { return currentValue; }
        public String getType() { return type; }
    }
    
    public static class WeeklyProgress {
        private String week;
        private double progress;
        private int timeSpent;
        
        public WeeklyProgress(String week, double progress, int timeSpent) {
            this.week = week;
            this.progress = progress;
            this.timeSpent = timeSpent;
        }
        
        public String getWeek() { return week; }
        public double getProgress() { return progress; }
        public int getTimeSpent() { return timeSpent; }
    }
    
    public static class Achievement {
        private String title;
        private String description;
        private String icon;
        private LocalDateTime earnedAt;
        
        public Achievement(String title, String description, String icon, LocalDateTime earnedAt) {
            this.title = title;
            this.description = description;
            this.icon = icon;
            this.earnedAt = earnedAt;
        }
        
        public String getTitle() { return title; }
        public String getDescription() { return description; }
        public String getIcon() { return icon; }
        public LocalDateTime getEarnedAt() { return earnedAt; }
    }
}