package com.learningportal.service;

import com.learningportal.model.AmazonCompetencyProgression;
import com.learningportal.repository.AmazonCompetencyProgressionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for managing Amazon Competency Progression
 * 
 * Implements L3-L6 competency mapping, Amazon hiring bar standards integration,
 * Leadership Principles contextual learning, and interview readiness scoring
 * with specific Amazon metrics.
 */
@Service
@Transactional
public class AmazonCompetencyService {

    @Autowired
    private AmazonCompetencyProgressionRepository competencyRepository;

    /**
     * Create or get existing competency progression for user
     */
    public AmazonCompetencyProgression getOrCreateProgression(Long userId) {
        return competencyRepository.findByUserId(userId)
            .orElseGet(() -> {
                AmazonCompetencyProgression progression = new AmazonCompetencyProgression(userId);
                progression.setCompetencyGaps(generateInitialCompetencyGaps());
                progression.setLeadershipPrinciplesProgress(generateInitialLeadershipPrinciplesProgress());
                progression.setTechnicalCompetencies(generateInitialTechnicalCompetencies());
                progression.setBehavioralCompetencies(generateInitialBehavioralCompetencies());
                progression.setProgressionTimeline(generateInitialProgressionTimeline());
                return competencyRepository.save(progression);
            });
    }

    /**
     * Update competency scores and assessment
     */
    public AmazonCompetencyProgression updateCompetencyAssessment(Long userId, 
                                                                Integer interviewReadinessScore,
                                                                Integer culturalFitScore) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        if (interviewReadinessScore != null) {
            progression.setInterviewReadinessScore(Math.max(0, Math.min(100, interviewReadinessScore)));
        }
        
        if (culturalFitScore != null) {
            progression.setCulturalFitScore(Math.max(0, Math.min(100, culturalFitScore)));
        }
        
        progression.setLastAssessed(LocalDateTime.now());
        
        // Update current level based on scores
        updateCurrentLevelBasedOnScores(progression);
        
        // Update competency gaps
        updateCompetencyGaps(progression);
        
        return competencyRepository.save(progression);
    }

    /**
     * Set target Amazon level
     */
    public AmazonCompetencyProgression setTargetLevel(Long userId, 
                                                    AmazonCompetencyProgression.AmazonLevel targetLevel) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        progression.setTargetAmazonLevel(targetLevel);
        
        // Update progression timeline based on new target
        updateProgressionTimeline(progression);
        
        return competencyRepository.save(progression);
    }

    /**
     * Update Leadership Principles progress
     */
    public AmazonCompetencyProgression updateLeadershipPrinciplesProgress(Long userId, 
                                                                        Map<String, Integer> principlesScores) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        // Convert map to JSON string (in real implementation, use proper JSON serialization)
        StringBuilder jsonBuilder = new StringBuilder("{");
        principlesScores.forEach((principle, score) -> 
            jsonBuilder.append("\"").append(principle).append("\":").append(score).append(","));
        if (jsonBuilder.length() > 1) {
            jsonBuilder.setLength(jsonBuilder.length() - 1); // Remove last comma
        }
        jsonBuilder.append("}");
        
        progression.setLeadershipPrinciplesProgress(jsonBuilder.toString());
        progression.setLastAssessed(LocalDateTime.now());
        
        // Update cultural fit score based on LP progress
        updateCulturalFitFromLeadershipPrinciples(progression, principlesScores);
        
        return competencyRepository.save(progression);
    }

    /**
     * Update technical competencies
     */
    public AmazonCompetencyProgression updateTechnicalCompetencies(Long userId, 
                                                                 Map<String, Object> competencies) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        // Convert map to JSON string (in real implementation, use proper JSON serialization)
        progression.setTechnicalCompetencies(competencies.toString());
        progression.setLastAssessed(LocalDateTime.now());
        
        return competencyRepository.save(progression);
    }

    /**
     * Get Amazon interview readiness assessment
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getInterviewReadinessAssessment(Long userId) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        Map<String, Object> assessment = new HashMap<>();
        assessment.put("overallReadiness", progression.getInterviewReadinessLevel());
        assessment.put("interviewReadinessScore", progression.getInterviewReadinessScore());
        assessment.put("culturalFitScore", progression.getCulturalFitScore());
        assessment.put("overallCompetencyScore", progression.getOverallCompetencyScore());
        assessment.put("hiringBarAlignment", progression.getAmazonHiringBarAlignment());
        assessment.put("currentLevel", progression.getCurrentAmazonLevel());
        assessment.put("targetLevel", progression.getTargetAmazonLevel());
        assessment.put("readyForTargetLevel", progression.isReadyForTargetLevel());
        assessment.put("readyForPromotion", progression.isReadyForPromotion());
        assessment.put("nextLevel", progression.getNextLevel());
        assessment.put("progressionVelocity", progression.getProgressionVelocity());
        assessment.put("developmentRecommendations", progression.generateDevelopmentRecommendations());
        assessment.put("daysSinceLastAssessment", progression.getDaysSinceLastAssessment());
        assessment.put("assessmentStale", progression.isAssessmentStale());
        
        return assessment;
    }

    /**
     * Get Leadership Principles assessment
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getLeadershipPrinciplesAssessment(Long userId) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        Map<String, Object> assessment = new HashMap<>();
        assessment.put("allPrinciples", Arrays.stream(AmazonCompetencyProgression.LeadershipPrinciple.values())
            .collect(Collectors.toMap(
                lp -> lp.name(),
                lp -> Map.of("name", lp.getName(), "description", lp.getDescription())
            )));
        
        assessment.put("userProgress", progression.getLeadershipPrinciplesProgress());
        assessment.put("culturalFitScore", progression.getCulturalFitScore());
        assessment.put("culturalFitLevel", progression.getCulturalFitLevel());
        
        // Generate specific LP recommendations
        assessment.put("recommendations", generateLeadershipPrinciplesRecommendations(progression));
        
        return assessment;
    }

    /**
     * Get competency gap analysis
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getCompetencyGapAnalysis(Long userId) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        Map<String, Object> analysis = new HashMap<>();
        analysis.put("currentLevel", progression.getCurrentAmazonLevel());
        analysis.put("targetLevel", progression.getTargetAmazonLevel());
        analysis.put("competencyGaps", progression.getCompetencyGaps());
        analysis.put("technicalCompetencies", progression.getTechnicalCompetencies());
        analysis.put("behavioralCompetencies", progression.getBehavioralCompetencies());
        
        // Calculate gap severity
        double gapSeverity = calculateGapSeverity(progression);
        analysis.put("gapSeverity", gapSeverity);
        analysis.put("gapSeverityLevel", getGapSeverityLevel(gapSeverity));
        
        // Generate targeted improvement plan
        analysis.put("improvementPlan", generateImprovementPlan(progression));
        
        return analysis;
    }

    /**
     * Get Amazon level progression roadmap
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getProgressionRoadmap(Long userId) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        Map<String, Object> roadmap = new HashMap<>();
        roadmap.put("currentLevel", progression.getCurrentAmazonLevel());
        roadmap.put("targetLevel", progression.getTargetAmazonLevel());
        roadmap.put("progressionTimeline", progression.getProgressionTimeline());
        
        // Generate level-specific requirements
        Map<String, Object> levelRequirements = new HashMap<>();
        for (AmazonCompetencyProgression.AmazonLevel level : AmazonCompetencyProgression.AmazonLevel.values()) {
            levelRequirements.put(level.name(), generateLevelRequirements(level));
        }
        roadmap.put("levelRequirements", levelRequirements);
        
        // Calculate estimated timeline to target level
        roadmap.put("estimatedTimeToTarget", calculateEstimatedTimeToTarget(progression));
        
        return roadmap;
    }

    /**
     * Get users ready for Amazon Senior SDE roles (L5+)
     */
    @Transactional(readOnly = true)
    public List<AmazonCompetencyProgression> getSeniorSDECandidates() {
        return competencyRepository.findSeniorSDECandidates();
    }

    /**
     * Get system-wide competency statistics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getSystemCompetencyStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        // Basic statistics
        Object[] basicStats = competencyRepository.getCompetencyStatistics();
        if (basicStats != null && basicStats.length > 0) {
            stats.put("totalUsers", basicStats[0]);
            stats.put("averageInterviewReadiness", basicStats[1]);
            stats.put("averageCulturalFit", basicStats[2]);
            stats.put("minInterviewReadiness", basicStats[3]);
            stats.put("maxInterviewReadiness", basicStats[4]);
            stats.put("minCulturalFit", basicStats[5]);
            stats.put("maxCulturalFit", basicStats[6]);
        }
        
        // Level distributions
        List<Object[]> currentLevelDist = competencyRepository.getCurrentLevelDistribution();
        stats.put("currentLevelDistribution", currentLevelDist.stream()
            .collect(Collectors.toMap(
                row -> row[0].toString(),
                row -> row[1]
            )));
        
        List<Object[]> targetLevelDist = competencyRepository.getTargetLevelDistribution();
        stats.put("targetLevelDistribution", targetLevelDist.stream()
            .collect(Collectors.toMap(
                row -> row[0].toString(),
                row -> row[1]
            )));
        
        // Hiring bar statistics
        stats.put("usersExceedingHiringBar", competencyRepository.countUsersExceedingHiringBar());
        stats.put("usersMeetingHiringBar", competencyRepository.countUsersMeetingHiringBar());
        stats.put("usersReadyForPromotion", competencyRepository.countUsersReadyForPromotion());
        
        // Average scores by level
        List<Object[]> avgScores = competencyRepository.getAverageScoresByLevel();
        Map<String, Map<String, Object>> scoresByLevel = new HashMap<>();
        for (Object[] row : avgScores) {
            Map<String, Object> levelScores = new HashMap<>();
            levelScores.put("averageInterviewReadiness", row[1]);
            levelScores.put("averageCulturalFit", row[2]);
            scoresByLevel.put(row[0].toString(), levelScores);
        }
        stats.put("averageScoresByLevel", scoresByLevel);
        
        return stats;
    }

    /**
     * Generate personalized Amazon career development plan
     */
    public Map<String, Object> generateCareerDevelopmentPlan(Long userId) {
        AmazonCompetencyProgression progression = getOrCreateProgression(userId);
        
        Map<String, Object> plan = new HashMap<>();
        plan.put("currentAssessment", getInterviewReadinessAssessment(userId));
        plan.put("gapAnalysis", getCompetencyGapAnalysis(userId));
        plan.put("progressionRoadmap", getProgressionRoadmap(userId));
        plan.put("leadershipPrinciplesAssessment", getLeadershipPrinciplesAssessment(userId));
        
        // Generate specific action items
        List<String> actionItems = generateActionItems(progression);
        plan.put("actionItems", actionItems);
        
        // Generate timeline milestones
        List<Map<String, Object>> milestones = generateMilestones(progression);
        plan.put("milestones", milestones);
        
        return plan;
    }

    // Private helper methods

    private void updateCurrentLevelBasedOnScores(AmazonCompetencyProgression progression) {
        double overallScore = progression.getOverallCompetencyScore();
        
        if (overallScore >= 87.5 && progression.getInterviewReadinessScore() >= 90) {
            progression.setCurrentAmazonLevel(AmazonCompetencyProgression.AmazonLevel.L6);
        } else if (overallScore >= 80 && progression.getInterviewReadinessScore() >= 80) {
            progression.setCurrentAmazonLevel(AmazonCompetencyProgression.AmazonLevel.L5);
        } else if (overallScore >= 70 && progression.getInterviewReadinessScore() >= 70) {
            progression.setCurrentAmazonLevel(AmazonCompetencyProgression.AmazonLevel.L4);
        } else {
            progression.setCurrentAmazonLevel(AmazonCompetencyProgression.AmazonLevel.L3);
        }
    }

    private void updateCompetencyGaps(AmazonCompetencyProgression progression) {
        List<String> gaps = new ArrayList<>();
        
        if (progression.getInterviewReadinessScore() < 70) {
            gaps.add("Technical interview skills");
        }
        if (progression.getCulturalFitScore() < 70) {
            gaps.add("Amazon Leadership Principles understanding");
        }
        if (progression.getInterviewReadinessScore() < 80 && 
            progression.getTargetAmazonLevel().ordinal() >= AmazonCompetencyProgression.AmazonLevel.L5.ordinal()) {
            gaps.add("Senior-level technical depth");
        }
        
        progression.setCompetencyGaps(gaps.toString());
    }

    private void updateProgressionTimeline(AmazonCompetencyProgression progression) {
        Map<String, Object> timeline = new HashMap<>();
        timeline.put("targetLevel", progression.getTargetAmazonLevel());
        timeline.put("estimatedMonths", calculateEstimatedTimeToTarget(progression));
        timeline.put("lastUpdated", LocalDateTime.now());
        
        progression.setProgressionTimeline(timeline.toString());
    }

    private void updateCulturalFitFromLeadershipPrinciples(AmazonCompetencyProgression progression, 
                                                          Map<String, Integer> principlesScores) {
        double averageScore = principlesScores.values().stream()
            .mapToInt(Integer::intValue)
            .average()
            .orElse(0.0);
        
        // Update cultural fit score based on LP progress
        int newCulturalFitScore = (int) Math.round(averageScore);
        progression.setCulturalFitScore(Math.max(0, Math.min(100, newCulturalFitScore)));
    }

    private String generateInitialCompetencyGaps() {
        return "[\"Technical interview preparation\", \"System design skills\", \"Amazon Leadership Principles application\"]";
    }

    private String generateInitialLeadershipPrinciplesProgress() {
        Map<String, Integer> initialProgress = new HashMap<>();
        for (AmazonCompetencyProgression.LeadershipPrinciple lp : AmazonCompetencyProgression.LeadershipPrinciple.values()) {
            initialProgress.put(lp.name(), 0);
        }
        return initialProgress.toString();
    }

    private String generateInitialTechnicalCompetencies() {
        return "{\"programming\": 0, \"systemDesign\": 0, \"algorithms\": 0, \"dataStructures\": 0}";
    }

    private String generateInitialBehavioralCompetencies() {
        return "{\"communication\": 0, \"leadership\": 0, \"problemSolving\": 0, \"teamwork\": 0}";
    }

    private String generateInitialProgressionTimeline() {
        return "{\"created\": \"" + LocalDateTime.now() + "\", \"estimatedMonths\": 12, \"milestones\": []}";
    }

    private List<String> generateLeadershipPrinciplesRecommendations(AmazonCompetencyProgression progression) {
        List<String> recommendations = new ArrayList<>();
        
        if (progression.getCulturalFitScore() < 70) {
            recommendations.add("Study all 16 Amazon Leadership Principles in depth");
            recommendations.add("Practice STAR method for behavioral interview questions");
            recommendations.add("Read Amazon leadership stories and case studies");
        }
        
        if (progression.getCulturalFitScore() < 80) {
            recommendations.add("Practice applying Leadership Principles to technical scenarios");
            recommendations.add("Develop specific examples for each Leadership Principle");
        }
        
        return recommendations;
    }

    private double calculateGapSeverity(AmazonCompetencyProgression progression) {
        double targetScore = switch (progression.getTargetAmazonLevel()) {
            case L4 -> 70.0;
            case L5 -> 80.0;
            case L6 -> 90.0;
            default -> 60.0;
        };
        
        double currentScore = progression.getOverallCompetencyScore();
        return Math.max(0, targetScore - currentScore);
    }

    private String getGapSeverityLevel(double gapSeverity) {
        if (gapSeverity <= 5) return "Minimal";
        if (gapSeverity <= 15) return "Moderate";
        if (gapSeverity <= 30) return "Significant";
        return "Critical";
    }

    private List<String> generateImprovementPlan(AmazonCompetencyProgression progression) {
        List<String> plan = new ArrayList<>();
        
        double gapSeverity = calculateGapSeverity(progression);
        
        if (gapSeverity > 20) {
            plan.add("Focus on fundamental technical skills development");
            plan.add("Complete comprehensive Amazon Leadership Principles training");
        } else if (gapSeverity > 10) {
            plan.add("Practice advanced technical interview questions");
            plan.add("Develop specific Leadership Principles examples");
        } else {
            plan.add("Fine-tune interview performance and presentation skills");
            plan.add("Practice system design at Amazon scale");
        }
        
        return plan;
    }

    private Map<String, Object> generateLevelRequirements(AmazonCompetencyProgression.AmazonLevel level) {
        Map<String, Object> requirements = new HashMap<>();
        
        switch (level) {
            case L3:
                requirements.put("interviewReadiness", 60);
                requirements.put("culturalFit", 60);
                requirements.put("technicalSkills", List.of("Basic programming", "Data structures", "Algorithms"));
                break;
            case L4:
                requirements.put("interviewReadiness", 70);
                requirements.put("culturalFit", 70);
                requirements.put("technicalSkills", List.of("Advanced programming", "System design basics", "Problem solving"));
                break;
            case L5:
                requirements.put("interviewReadiness", 80);
                requirements.put("culturalFit", 80);
                requirements.put("technicalSkills", List.of("Expert programming", "System design", "Leadership", "Mentoring"));
                break;
            case L6:
                requirements.put("interviewReadiness", 90);
                requirements.put("culturalFit", 85);
                requirements.put("technicalSkills", List.of("Architectural design", "Technical leadership", "Innovation", "Strategic thinking"));
                break;
        }
        
        return requirements;
    }

    private int calculateEstimatedTimeToTarget(AmazonCompetencyProgression progression) {
        double currentScore = progression.getOverallCompetencyScore();
        double targetScore = switch (progression.getTargetAmazonLevel()) {
            case L4 -> 70.0;
            case L5 -> 80.0;
            case L6 -> 90.0;
            default -> 60.0;
        };
        
        double gap = Math.max(0, targetScore - currentScore);
        
        // Estimate 2-3 points improvement per month with dedicated effort
        return (int) Math.ceil(gap / 2.5);
    }

    private List<String> generateActionItems(AmazonCompetencyProgression progression) {
        List<String> actionItems = new ArrayList<>();
        
        if (progression.getInterviewReadinessScore() < 70) {
            actionItems.add("Complete technical interview preparation course");
            actionItems.add("Practice coding problems daily (2-3 hours)");
        }
        
        if (progression.getCulturalFitScore() < 70) {
            actionItems.add("Study Amazon Leadership Principles with examples");
            actionItems.add("Practice behavioral interview questions using STAR method");
        }
        
        if (progression.isAssessmentStale()) {
            actionItems.add("Schedule competency reassessment");
        }
        
        return actionItems;
    }

    private List<Map<String, Object>> generateMilestones(AmazonCompetencyProgression progression) {
        List<Map<String, Object>> milestones = new ArrayList<>();
        
        LocalDateTime now = LocalDateTime.now();
        
        // 3-month milestone
        Map<String, Object> milestone1 = new HashMap<>();
        milestone1.put("timeframe", "3 months");
        milestone1.put("targetDate", now.plusMonths(3));
        milestone1.put("goals", List.of("Improve interview readiness by 15 points", "Complete LP training"));
        milestones.add(milestone1);
        
        // 6-month milestone
        Map<String, Object> milestone2 = new HashMap<>();
        milestone2.put("timeframe", "6 months");
        milestone2.put("targetDate", now.plusMonths(6));
        milestone2.put("goals", List.of("Achieve 75+ interview readiness", "Practice system design"));
        milestones.add(milestone2);
        
        // 12-month milestone
        Map<String, Object> milestone3 = new HashMap<>();
        milestone3.put("timeframe", "12 months");
        milestone3.put("targetDate", now.plusMonths(12));
        milestone3.put("goals", List.of("Ready for target Amazon level", "Interview preparation complete"));
        milestones.add(milestone3);
        
        return milestones;
    }
}