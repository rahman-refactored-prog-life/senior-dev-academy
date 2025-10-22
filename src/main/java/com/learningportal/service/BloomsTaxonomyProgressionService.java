package com.learningportal.service;

import com.learningportal.model.BloomsTaxonomyProgression;
import com.learningportal.repository.BloomsTaxonomyProgressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for managing Bloom's Taxonomy Progression
 * 
 * Implements the progressive difficulty system for zero-experience learners,
 * providing systematic skill building aligned with Amazon competency levels
 * and evidence-based progression validation.
 */
@Service
@Transactional
public class BloomsTaxonomyProgressionService {

    @Autowired
    private BloomsTaxonomyProgressionRepository progressionRepository;

    /**
     * Create or get existing progression for user and content
     */
    public BloomsTaxonomyProgression getOrCreateProgression(Long userId, Long contentId) {
        return progressionRepository.findByUserIdAndContentId(userId, contentId)
            .orElseGet(() -> {
                BloomsTaxonomyProgression progression = new BloomsTaxonomyProgression(userId, contentId);
                progression.setNextLevelRequirements(generateInitialRequirements());
                return progressionRepository.save(progression);
            });
    }

    /**
     * Update progression score for a specific Bloom's level
     */
    public BloomsTaxonomyProgression updateLevelScore(Long userId, Long contentId, 
                                                     BloomsTaxonomyProgression.BloomsLevel level, 
                                                     Integer score) {
        BloomsTaxonomyProgression progression = getOrCreateProgression(userId, contentId);
        
        // Update the appropriate level score
        switch (level) {
            case REMEMBER:
                progression.setRememberLevelScore(Math.max(0, Math.min(100, score)));
                break;
            case UNDERSTAND:
                progression.setUnderstandLevelScore(Math.max(0, Math.min(100, score)));
                break;
            case APPLY:
                progression.setApplyLevelScore(Math.max(0, Math.min(100, score)));
                break;
            case ANALYZE:
                progression.setAnalyzeLevelScore(Math.max(0, Math.min(100, score)));
                break;
            case EVALUATE:
                progression.setEvaluateLevelScore(Math.max(0, Math.min(100, score)));
                break;
            case CREATE:
                progression.setCreateLevelScore(Math.max(0, Math.min(100, score)));
                break;
        }

        // Update current level if ready for advancement
        updateCurrentLevel(progression);
        
        // Update progression evidence
        updateProgressionEvidence(progression, level, score);
        
        // Update next level requirements
        updateNextLevelRequirements(progression);

        return progressionRepository.save(progression);
    }

    /**
     * Advance user to next Bloom's level if ready
     */
    public boolean advanceToNextLevel(Long userId, Long contentId) {
        BloomsTaxonomyProgression progression = getOrCreateProgression(userId, contentId);
        
        if (progression.isReadyForNextLevel()) {
            BloomsTaxonomyProgression.BloomsLevel nextLevel = progression.getNextLevel();
            if (nextLevel != progression.getCurrentLevel()) {
                progression.setCurrentLevel(nextLevel);
                updateProgressionEvidence(progression, nextLevel, null);
                updateNextLevelRequirements(progression);
                progressionRepository.save(progression);
                return true;
            }
        }
        return false;
    }

    /**
     * Get progression analytics for a user
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getUserProgressionAnalytics(Long userId) {
        List<BloomsTaxonomyProgression> progressions = progressionRepository.findByUserIdOrderByUpdatedAtDesc(userId);
        
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalProgressions", progressions.size());
        analytics.put("averageOverallScore", calculateAverageOverallScore(progressions));
        analytics.put("currentLevelDistribution", getCurrentLevelDistribution(progressions));
        analytics.put("amazonCompetencyReadiness", getAmazonCompetencyReadiness(progressions));
        analytics.put("learningVelocityAnalysis", getLearningVelocityAnalysis(progressions));
        analytics.put("strengthsAndWeaknesses", getStrengthsAndWeaknesses(progressions));
        analytics.put("recommendedNextSteps", getRecommendedNextSteps(progressions));
        
        return analytics;
    }

    /**
     * Get content analytics for progression tracking
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getContentProgressionAnalytics(Long contentId) {
        Object[] stats = progressionRepository.getProgressionStatisticsForContent(contentId);
        
        Map<String, Object> analytics = new HashMap<>();
        if (stats != null && stats.length > 0) {
            analytics.put("totalUsers", stats[0]);
            analytics.put("averageRememberScore", stats[1]);
            analytics.put("averageUnderstandScore", stats[2]);
            analytics.put("averageApplyScore", stats[3]);
            analytics.put("averageAnalyzeScore", stats[4]);
            analytics.put("averageEvaluateScore", stats[5]);
            analytics.put("averageCreateScore", stats[6]);
            analytics.put("overallAverageScore", stats[7]);
        }
        
        // Add level distribution for this content
        List<BloomsTaxonomyProgression> contentProgressions = progressionRepository
            .findByContentIdOrderByUpdatedAtDesc(contentId, Pageable.unpaged()).getContent();
        analytics.put("levelDistribution", getCurrentLevelDistribution(contentProgressions));
        analytics.put("difficultyAnalysis", analyzeDifficultyForContent(contentProgressions));
        
        return analytics;
    }

    /**
     * Get users ready for Amazon Senior SDE level
     */
    @Transactional(readOnly = true)
    public List<BloomsTaxonomyProgression> getAmazonSeniorSDECandidates() {
        return progressionRepository.findAmazonSeniorSDELevelUsers();
    }

    /**
     * Get users needing additional support
     */
    @Transactional(readOnly = true)
    public List<BloomsTaxonomyProgression> getUsersNeedingSupport() {
        return progressionRepository.findUsersNeedingSupport(50.0); // Below 50% average
    }

    /**
     * Generate personalized learning recommendations
     */
    public List<String> generateLearningRecommendations(Long userId, Long contentId) {
        BloomsTaxonomyProgression progression = getOrCreateProgression(userId, contentId);
        List<String> recommendations = new ArrayList<>();
        
        // Analyze current performance
        BloomsTaxonomyProgression.BloomsLevel currentLevel = progression.getCurrentLevel();
        Integer currentScore = progression.getCurrentLevelScore();
        Double overallScore = progression.getOverallProgressionScore();
        
        // Generate level-specific recommendations
        if (currentScore < 60) {
            recommendations.add("Focus on mastering " + currentLevel.getLevelName() + 
                              " level concepts before advancing");
            recommendations.add("Review fundamental concepts and practice basic exercises");
        } else if (currentScore < 80) {
            recommendations.add("Continue practicing " + currentLevel.getLevelName() + 
                              " level skills to reach mastery threshold");
            recommendations.add("Seek additional examples and practice opportunities");
        } else {
            recommendations.add("Excellent progress at " + currentLevel.getLevelName() + 
                              " level! Ready to advance to " + progression.getNextLevel().getLevelName());
        }
        
        // Amazon competency recommendations
        String amazonReadiness = progression.getAmazonCompetencyReadiness();
        if (amazonReadiness.contains("L6")) {
            recommendations.add("Outstanding! You're ready for Amazon L6 Senior SDE roles");
        } else if (amazonReadiness.contains("L5")) {
            recommendations.add("Great progress! Focus on CREATE level skills for L6 readiness");
        } else if (amazonReadiness.contains("L4")) {
            recommendations.add("Good foundation! Work on ANALYZE and EVALUATE skills for L5 readiness");
        } else {
            recommendations.add("Build strong fundamentals in REMEMBER and UNDERSTAND levels");
        }
        
        // Learning velocity recommendations
        String velocity = progression.getLearningVelocity();
        if ("Very Slow".equals(velocity) || "Slow".equals(velocity)) {
            recommendations.add("Consider breaking learning into smaller, more manageable chunks");
            recommendations.add("Set daily learning goals and track progress consistently");
        }
        
        return recommendations;
    }

    /**
     * Validate prerequisite completion before level advancement
     */
    public boolean validatePrerequisites(Long userId, Long contentId, 
                                       BloomsTaxonomyProgression.BloomsLevel targetLevel) {
        BloomsTaxonomyProgression progression = getOrCreateProgression(userId, contentId);
        
        // Check if all previous levels meet minimum requirements
        switch (targetLevel) {
            case UNDERSTAND:
                return progression.getRememberLevelScore() >= 70;
            case APPLY:
                return progression.getRememberLevelScore() >= 70 && 
                       progression.getUnderstandLevelScore() >= 70;
            case ANALYZE:
                return progression.getRememberLevelScore() >= 70 && 
                       progression.getUnderstandLevelScore() >= 70 && 
                       progression.getApplyLevelScore() >= 70;
            case EVALUATE:
                return progression.getRememberLevelScore() >= 70 && 
                       progression.getUnderstandLevelScore() >= 70 && 
                       progression.getApplyLevelScore() >= 70 && 
                       progression.getAnalyzeLevelScore() >= 70;
            case CREATE:
                return progression.getRememberLevelScore() >= 70 && 
                       progression.getUnderstandLevelScore() >= 70 && 
                       progression.getApplyLevelScore() >= 70 && 
                       progression.getAnalyzeLevelScore() >= 70 && 
                       progression.getEvaluateLevelScore() >= 70;
            default:
                return true; // REMEMBER level has no prerequisites
        }
    }

    // Private helper methods

    private void updateCurrentLevel(BloomsTaxonomyProgression progression) {
        // Determine the highest level where user has achieved mastery (80%+)
        if (progression.getCreateLevelScore() >= 80) {
            progression.setCurrentLevel(BloomsTaxonomyProgression.BloomsLevel.CREATE);
        } else if (progression.getEvaluateLevelScore() >= 80) {
            progression.setCurrentLevel(BloomsTaxonomyProgression.BloomsLevel.EVALUATE);
        } else if (progression.getAnalyzeLevelScore() >= 80) {
            progression.setCurrentLevel(BloomsTaxonomyProgression.BloomsLevel.ANALYZE);
        } else if (progression.getApplyLevelScore() >= 80) {
            progression.setCurrentLevel(BloomsTaxonomyProgression.BloomsLevel.APPLY);
        } else if (progression.getUnderstandLevelScore() >= 80) {
            progression.setCurrentLevel(BloomsTaxonomyProgression.BloomsLevel.UNDERSTAND);
        } else if (progression.getRememberLevelScore() >= 80) {
            progression.setCurrentLevel(BloomsTaxonomyProgression.BloomsLevel.REMEMBER);
        }
    }

    private void updateProgressionEvidence(BloomsTaxonomyProgression progression, 
                                         BloomsTaxonomyProgression.BloomsLevel level, 
                                         Integer score) {
        Map<String, Object> evidence = new HashMap<>();
        evidence.put("level", level.name());
        evidence.put("timestamp", LocalDateTime.now());
        if (score != null) {
            evidence.put("score", score);
        }
        evidence.put("overallProgress", progression.getProgressionCompletionPercentage());
        
        // Store as JSON string (in real implementation, use proper JSON serialization)
        progression.setProgressionEvidence(evidence.toString());
    }

    private void updateNextLevelRequirements(BloomsTaxonomyProgression progression) {
        BloomsTaxonomyProgression.BloomsLevel nextLevel = progression.getNextLevel();
        if (nextLevel != progression.getCurrentLevel()) {
            String requirements = generateRequirementsForLevel(nextLevel);
            progression.setNextLevelRequirements(requirements);
        } else {
            progression.setNextLevelRequirements("Maximum level achieved - focus on mastery and application");
        }
    }

    private String generateInitialRequirements() {
        return "Start with REMEMBER level: Focus on recalling facts and basic concepts. " +
               "Practice identifying key terms, definitions, and fundamental principles.";
    }

    private String generateRequirementsForLevel(BloomsTaxonomyProgression.BloomsLevel level) {
        switch (level) {
            case REMEMBER:
                return "Recall facts, terms, basic concepts, and answers";
            case UNDERSTAND:
                return "Demonstrate understanding by explaining concepts in your own words";
            case APPLY:
                return "Use learned information in new situations and solve problems";
            case ANALYZE:
                return "Break down complex information and identify relationships between parts";
            case EVALUATE:
                return "Make judgments about information and justify decisions";
            case CREATE:
                return "Combine elements to form new patterns or propose alternative solutions";
            default:
                return "Continue learning and practicing";
        }
    }

    private Double calculateAverageOverallScore(List<BloomsTaxonomyProgression> progressions) {
        return progressions.stream()
            .mapToDouble(BloomsTaxonomyProgression::getOverallProgressionScore)
            .average()
            .orElse(0.0);
    }

    private Map<String, Long> getCurrentLevelDistribution(List<BloomsTaxonomyProgression> progressions) {
        return progressions.stream()
            .collect(Collectors.groupingBy(
                p -> p.getCurrentLevel().name(),
                Collectors.counting()
            ));
    }

    private Map<String, Object> getAmazonCompetencyReadiness(List<BloomsTaxonomyProgression> progressions) {
        Map<String, Long> readinessDistribution = progressions.stream()
            .collect(Collectors.groupingBy(
                BloomsTaxonomyProgression::getAmazonCompetencyReadiness,
                Collectors.counting()
            ));
        
        long seniorSDEReady = progressions.stream()
            .mapToLong(p -> p.isAmazonSeniorSDELevel() ? 1 : 0)
            .sum();
        
        Map<String, Object> readiness = new HashMap<>();
        readiness.put("distribution", readinessDistribution);
        readiness.put("seniorSDEReadyCount", seniorSDEReady);
        readiness.put("seniorSDEReadyPercentage", 
                     progressions.isEmpty() ? 0.0 : (double) seniorSDEReady / progressions.size() * 100);
        
        return readiness;
    }

    private Map<String, Object> getLearningVelocityAnalysis(List<BloomsTaxonomyProgression> progressions) {
        Map<String, Long> velocityDistribution = progressions.stream()
            .collect(Collectors.groupingBy(
                BloomsTaxonomyProgression::getLearningVelocity,
                Collectors.counting()
            ));
        
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("distribution", velocityDistribution);
        analysis.put("averageCompletionPercentage", 
                    progressions.stream()
                        .mapToDouble(BloomsTaxonomyProgression::getProgressionCompletionPercentage)
                        .average()
                        .orElse(0.0));
        
        return analysis;
    }

    private Map<String, Object> getStrengthsAndWeaknesses(List<BloomsTaxonomyProgression> progressions) {
        if (progressions.isEmpty()) {
            return new HashMap<>();
        }
        
        // Calculate average scores for each level
        double avgRemember = progressions.stream().mapToInt(BloomsTaxonomyProgression::getRememberLevelScore).average().orElse(0);
        double avgUnderstand = progressions.stream().mapToInt(BloomsTaxonomyProgression::getUnderstandLevelScore).average().orElse(0);
        double avgApply = progressions.stream().mapToInt(BloomsTaxonomyProgression::getApplyLevelScore).average().orElse(0);
        double avgAnalyze = progressions.stream().mapToInt(BloomsTaxonomyProgression::getAnalyzeLevelScore).average().orElse(0);
        double avgEvaluate = progressions.stream().mapToInt(BloomsTaxonomyProgression::getEvaluateLevelScore).average().orElse(0);
        double avgCreate = progressions.stream().mapToInt(BloomsTaxonomyProgression::getCreateLevelScore).average().orElse(0);
        
        Map<String, Double> levelAverages = new HashMap<>();
        levelAverages.put("REMEMBER", avgRemember);
        levelAverages.put("UNDERSTAND", avgUnderstand);
        levelAverages.put("APPLY", avgApply);
        levelAverages.put("ANALYZE", avgAnalyze);
        levelAverages.put("EVALUATE", avgEvaluate);
        levelAverages.put("CREATE", avgCreate);
        
        // Find strengths (highest scores) and weaknesses (lowest scores)
        String strongestLevel = levelAverages.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("NONE");
        
        String weakestLevel = levelAverages.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("NONE");
        
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("levelAverages", levelAverages);
        analysis.put("strongestLevel", strongestLevel);
        analysis.put("weakestLevel", weakestLevel);
        
        return analysis;
    }

    private List<String> getRecommendedNextSteps(List<BloomsTaxonomyProgression> progressions) {
        List<String> recommendations = new ArrayList<>();
        
        if (progressions.isEmpty()) {
            recommendations.add("Start your learning journey with foundational concepts");
            return recommendations;
        }
        
        // Analyze overall progress and provide recommendations
        double avgOverallScore = calculateAverageOverallScore(progressions);
        
        if (avgOverallScore < 50) {
            recommendations.add("Focus on building strong foundations in basic concepts");
            recommendations.add("Spend more time on REMEMBER and UNDERSTAND levels");
        } else if (avgOverallScore < 70) {
            recommendations.add("Good progress! Work on applying concepts in practical scenarios");
            recommendations.add("Practice APPLY level exercises to build competency");
        } else if (avgOverallScore < 85) {
            recommendations.add("Excellent foundation! Focus on higher-order thinking skills");
            recommendations.add("Develop ANALYZE and EVALUATE level capabilities");
        } else {
            recommendations.add("Outstanding progress! You're ready for advanced challenges");
            recommendations.add("Focus on CREATE level projects and Amazon Senior SDE preparation");
        }
        
        return recommendations;
    }

    private Map<String, Object> analyzeDifficultyForContent(List<BloomsTaxonomyProgression> progressions) {
        Map<String, Object> analysis = new HashMap<>();
        
        if (progressions.isEmpty()) {
            return analysis;
        }
        
        // Calculate difficulty indicators
        double avgOverallScore = calculateAverageOverallScore(progressions);
        long strugglingUsers = progressions.stream()
            .mapToLong(p -> p.getOverallProgressionScore() < 50 ? 1 : 0)
            .sum();
        
        String difficultyLevel;
        if (avgOverallScore >= 80) {
            difficultyLevel = "EASY";
        } else if (avgOverallScore >= 60) {
            difficultyLevel = "MODERATE";
        } else if (avgOverallScore >= 40) {
            difficultyLevel = "CHALLENGING";
        } else {
            difficultyLevel = "VERY_CHALLENGING";
        }
        
        analysis.put("averageScore", avgOverallScore);
        analysis.put("difficultyLevel", difficultyLevel);
        analysis.put("strugglingUsersCount", strugglingUsers);
        analysis.put("strugglingUsersPercentage", 
                    (double) strugglingUsers / progressions.size() * 100);
        
        return analysis;
    }
}