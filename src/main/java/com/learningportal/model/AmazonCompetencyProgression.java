package com.learningportal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Amazon Competency Progression Entity
 * 
 * Tracks user progression through Amazon competency levels (L3-L6)
 * with Leadership Principles integration and hiring bar standards.
 */
@Entity
@Table(name = "amazon_competency_progression")
@Schema(description = "Amazon competency progression tracking with L3-L6 alignment")
public class AmazonCompetencyProgression {

    @Schema(description = "Amazon competency levels")
    public enum AmazonLevel {
        L3("L3", "Junior SDE", "Entry-level software development engineer"),
        L4("L4", "SDE", "Software development engineer"),
        L5("L5", "Senior SDE", "Senior software development engineer"),
        L6("L6", "Principal SDE", "Principal software development engineer");

        private final String level;
        private final String title;
        private final String description;

        AmazonLevel(String level, String title, String description) {
            this.level = level;
            this.title = title;
            this.description = description;
        }

        public String getLevel() { return level; }
        public String getTitle() { return title; }
        public String getDescription() { return description; }
    }

    @Schema(description = "Amazon Leadership Principles")
    public enum LeadershipPrinciple {
        CUSTOMER_OBSESSION("Customer Obsession", "Leaders start with the customer and work backwards"),
        OWNERSHIP("Ownership", "Leaders are owners. They think long term and don't sacrifice long-term value for short-term results"),
        INVENT_AND_SIMPLIFY("Invent and Simplify", "Leaders expect and require innovation and invention from their teams"),
        ARE_RIGHT_A_LOT("Are Right, A Lot", "Leaders are right a lot. They have strong judgment and good instincts"),
        LEARN_AND_BE_CURIOUS("Learn and Be Curious", "Leaders are never done learning and always seek to improve themselves"),
        HIRE_AND_DEVELOP_THE_BEST("Hire and Develop the Best", "Leaders raise the performance bar with every hire and promotion"),
        INSIST_ON_THE_HIGHEST_STANDARDS("Insist on the Highest Standards", "Leaders have relentlessly high standards"),
        THINK_BIG("Think Big", "Thinking small is a self-fulfilling prophecy"),
        BIAS_FOR_ACTION("Bias for Action", "Speed matters in business. Many decisions and actions are reversible"),
        FRUGALITY("Frugality", "Accomplish more with less. Constraints breed resourcefulness"),
        EARN_TRUST("Earn Trust", "Leaders listen attentively, speak candidly, and treat others respectfully"),
        DIVE_DEEP("Dive Deep", "Leaders operate at all levels, stay connected to the details"),
        HAVE_BACKBONE_DISAGREE_AND_COMMIT("Have Backbone; Disagree and Commit", "Leaders are obligated to respectfully challenge decisions"),
        DELIVER_RESULTS("Deliver Results", "Leaders focus on the key inputs for their business and deliver them with the right quality"),
        STRIVE_TO_BE_EARTHS_BEST_EMPLOYER("Strive to be Earth's Best Employer", "Leaders work every day to create a safer, more productive, higher performing, more diverse, and more just work environment"),
        SUCCESS_AND_SCALE_BRING_BROAD_RESPONSIBILITY("Success and Scale Bring Broad Responsibility", "We started in a garage, but we're not there anymore");

        private final String name;
        private final String description;

        LeadershipPrinciple(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the progression record", example = "1")
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    @Schema(description = "Reference to the user", example = "123")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_amazon_level")
    @Schema(description = "Current Amazon competency level")
    private AmazonLevel currentAmazonLevel = AmazonLevel.L3;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_amazon_level")
    @Schema(description = "Target Amazon competency level")
    private AmazonLevel targetAmazonLevel = AmazonLevel.L5;

    @Column(name = "competency_gaps", columnDefinition = "TEXT")
    @Schema(description = "JSON array of identified skill gaps")
    private String competencyGaps;

    @Column(name = "leadership_principles_progress", columnDefinition = "TEXT")
    @Schema(description = "JSON mapping of 16 Leadership Principles progress")
    private String leadershipPrinciplesProgress;

    @Column(name = "technical_competencies", columnDefinition = "TEXT")
    @Schema(description = "JSON technical skills mapping")
    private String technicalCompetencies;

    @Column(name = "behavioral_competencies", columnDefinition = "TEXT")
    @Schema(description = "JSON behavioral skills mapping")
    private String behavioralCompetencies;

    @Column(name = "progression_timeline", columnDefinition = "TEXT")
    @Schema(description = "JSON timeline with milestones")
    private String progressionTimeline;

    @Min(0) @Max(100)
    @Column(name = "interview_readiness_score")
    @Schema(description = "Interview readiness score (0-100)", example = "75")
    private Integer interviewReadinessScore = 0;

    @Min(0) @Max(100)
    @Column(name = "cultural_fit_score")
    @Schema(description = "Amazon culture alignment score (0-100)", example = "80")
    private Integer culturalFitScore = 0;

    @Column(name = "last_assessed")
    @Schema(description = "Last assessment timestamp")
    private LocalDateTime lastAssessed = LocalDateTime.now();

    @Column(name = "created_at")
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (lastAssessed == null) {
            lastAssessed = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Check if ready for target Amazon level
     */
    public boolean isReadyForTargetLevel() {
        return interviewReadinessScore >= 80 && 
               culturalFitScore >= 75 &&
               hasMinimumTechnicalCompetencies() &&
               hasMinimumLeadershipPrinciplesProgress();
    }

    /**
     * Check if ready for promotion to next level
     */
    public boolean isReadyForPromotion() {
        AmazonLevel nextLevel = getNextLevel();
        if (nextLevel == currentAmazonLevel) return false; // Already at highest level
        
        return switch (nextLevel) {
            case L4 -> interviewReadinessScore >= 70 && culturalFitScore >= 70;
            case L5 -> interviewReadinessScore >= 80 && culturalFitScore >= 80;
            case L6 -> interviewReadinessScore >= 90 && culturalFitScore >= 85;
            default -> false;
        };
    }

    /**
     * Get next Amazon level
     */
    public AmazonLevel getNextLevel() {
        return switch (currentAmazonLevel) {
            case L3 -> AmazonLevel.L4;
            case L4 -> AmazonLevel.L5;
            case L5 -> AmazonLevel.L6;
            case L6 -> AmazonLevel.L6; // Already at highest level
        };
    }

    /**
     * Get overall competency score
     */
    public double getOverallCompetencyScore() {
        return (interviewReadinessScore + culturalFitScore) / 2.0;
    }

    /**
     * Get competency level description
     */
    public String getCompetencyLevelDescription() {
        double score = getOverallCompetencyScore();
        if (score >= 90) return "Exceptional - Ready for L6";
        if (score >= 80) return "Strong - Ready for L5";
        if (score >= 70) return "Good - Ready for L4";
        if (score >= 60) return "Developing - L3 level";
        return "Needs Improvement - Below L3";
    }

    /**
     * Get days since last assessment
     */
    public long getDaysSinceLastAssessment() {
        return java.time.Duration.between(lastAssessed, LocalDateTime.now()).toDays();
    }

    /**
     * Check if assessment is stale
     */
    public boolean isAssessmentStale() {
        return getDaysSinceLastAssessment() > 30; // Stale after 30 days
    }

    /**
     * Get progression velocity
     */
    public String getProgressionVelocity() {
        if (createdAt == null || updatedAt == null) return "Unknown";
        
        long daysSinceCreation = java.time.Duration.between(createdAt, updatedAt).toDays();
        if (daysSinceCreation == 0) return "Just Started";
        
        double scoreImprovement = getOverallCompetencyScore();
        double velocityRate = scoreImprovement / daysSinceCreation;
        
        if (velocityRate >= 2.0) return "Very Fast";
        if (velocityRate >= 1.0) return "Fast";
        if (velocityRate >= 0.5) return "Moderate";
        if (velocityRate >= 0.2) return "Slow";
        return "Very Slow";
    }

    /**
     * Get Amazon hiring bar alignment
     */
    public String getAmazonHiringBarAlignment() {
        if (interviewReadinessScore >= 85 && culturalFitScore >= 80) {
            return "Exceeds Amazon Hiring Bar";
        } else if (interviewReadinessScore >= 75 && culturalFitScore >= 70) {
            return "Meets Amazon Hiring Bar";
        } else if (interviewReadinessScore >= 60 && culturalFitScore >= 60) {
            return "Approaching Amazon Hiring Bar";
        } else {
            return "Below Amazon Hiring Bar";
        }
    }

    /**
     * Get interview readiness level
     */
    public String getInterviewReadinessLevel() {
        if (interviewReadinessScore >= 90) return "Highly Ready";
        if (interviewReadinessScore >= 80) return "Ready";
        if (interviewReadinessScore >= 70) return "Nearly Ready";
        if (interviewReadinessScore >= 60) return "Preparing";
        return "Not Ready";
    }

    /**
     * Get cultural fit level
     */
    public String getCulturalFitLevel() {
        if (culturalFitScore >= 90) return "Excellent Fit";
        if (culturalFitScore >= 80) return "Strong Fit";
        if (culturalFitScore >= 70) return "Good Fit";
        if (culturalFitScore >= 60) return "Developing Fit";
        return "Poor Fit";
    }

    /**
     * Generate development recommendations
     */
    public String generateDevelopmentRecommendations() {
        StringBuilder recommendations = new StringBuilder();
        
        if (interviewReadinessScore < 70) {
            recommendations.append("Focus on technical interview preparation. ");
        }
        
        if (culturalFitScore < 70) {
            recommendations.append("Study Amazon Leadership Principles and practice behavioral scenarios. ");
        }
        
        if (isAssessmentStale()) {
            recommendations.append("Schedule a competency reassessment. ");
        }
        
        if (isReadyForPromotion()) {
            recommendations.append("Consider applying for ").append(getNextLevel().getTitle()).append(" roles. ");
        }
        
        return recommendations.length() > 0 ? recommendations.toString() : "Continue current development path.";
    }

    // Private helper methods
    private boolean hasMinimumTechnicalCompetencies() {
        // In a real implementation, this would parse the JSON and check specific competencies
        return technicalCompetencies != null && !technicalCompetencies.trim().isEmpty();
    }

    private boolean hasMinimumLeadershipPrinciplesProgress() {
        // In a real implementation, this would parse the JSON and check LP progress
        return leadershipPrinciplesProgress != null && !leadershipPrinciplesProgress.trim().isEmpty();
    }

    // Constructors
    public AmazonCompetencyProgression() {}

    public AmazonCompetencyProgression(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public AmazonLevel getCurrentAmazonLevel() { return currentAmazonLevel; }
    public void setCurrentAmazonLevel(AmazonLevel currentAmazonLevel) { this.currentAmazonLevel = currentAmazonLevel; }

    public AmazonLevel getTargetAmazonLevel() { return targetAmazonLevel; }
    public void setTargetAmazonLevel(AmazonLevel targetAmazonLevel) { this.targetAmazonLevel = targetAmazonLevel; }

    public String getCompetencyGaps() { return competencyGaps; }
    public void setCompetencyGaps(String competencyGaps) { this.competencyGaps = competencyGaps; }

    public String getLeadershipPrinciplesProgress() { return leadershipPrinciplesProgress; }
    public void setLeadershipPrinciplesProgress(String leadershipPrinciplesProgress) { 
        this.leadershipPrinciplesProgress = leadershipPrinciplesProgress; 
    }

    public String getTechnicalCompetencies() { return technicalCompetencies; }
    public void setTechnicalCompetencies(String technicalCompetencies) { this.technicalCompetencies = technicalCompetencies; }

    public String getBehavioralCompetencies() { return behavioralCompetencies; }
    public void setBehavioralCompetencies(String behavioralCompetencies) { this.behavioralCompetencies = behavioralCompetencies; }

    public String getProgressionTimeline() { return progressionTimeline; }
    public void setProgressionTimeline(String progressionTimeline) { this.progressionTimeline = progressionTimeline; }

    public Integer getInterviewReadinessScore() { return interviewReadinessScore; }
    public void setInterviewReadinessScore(Integer interviewReadinessScore) { 
        this.interviewReadinessScore = interviewReadinessScore; 
    }

    public Integer getCulturalFitScore() { return culturalFitScore; }
    public void setCulturalFitScore(Integer culturalFitScore) { this.culturalFitScore = culturalFitScore; }

    public LocalDateTime getLastAssessed() { return lastAssessed; }
    public void setLastAssessed(LocalDateTime lastAssessed) { this.lastAssessed = lastAssessed; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "AmazonCompetencyProgression{" +
                "id=" + id +
                ", userId=" + userId +
                ", currentLevel=" + currentAmazonLevel +
                ", targetLevel=" + targetAmazonLevel +
                ", interviewReadiness=" + interviewReadinessScore +
                ", culturalFit=" + culturalFitScore +
                ", overallScore=" + getOverallCompetencyScore() +
                ", hiringBarAlignment='" + getAmazonHiringBarAlignment() + '\'' +
                '}';
    }
}