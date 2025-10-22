package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Technical Debt Prevention Report
 * 
 * Contains results from technical debt detection and prevention analysis
 */
public class TechnicalDebtPreventionReport {
    
    private LocalDateTime detectionTimestamp;
    private List<TechnicalDebtViolation> violations = new ArrayList<>();
    private int violationCount;
    private double technicalDebtScore;
    private List<String> recommendations = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public LocalDateTime getDetectionTimestamp() { return detectionTimestamp; }
    public void setDetectionTimestamp(LocalDateTime detectionTimestamp) { this.detectionTimestamp = detectionTimestamp; }
    
    public List<TechnicalDebtViolation> getViolations() { return violations; }
    public void setViolations(List<TechnicalDebtViolation> violations) { this.violations = violations; }
    
    public int getViolationCount() { return violationCount; }
    public void setViolationCount(int violationCount) { this.violationCount = violationCount; }
    
    public double getTechnicalDebtScore() { return technicalDebtScore; }
    public void setTechnicalDebtScore(double technicalDebtScore) { this.technicalDebtScore = technicalDebtScore; }
    
    public List<String> getRecommendations() { return recommendations; }
    public void setRecommendations(List<String> recommendations) { this.recommendations = recommendations; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}

/**
 * Quality Gate Result
 * 
 * Contains results from quality gate execution
 */
class QualityGateResult {
    
    private LocalDateTime executionTimestamp;
    private List<QualityGateCheck> qualityChecks = new ArrayList<>();
    private boolean gatesPassed;
    private List<String> failedGates = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public LocalDateTime getExecutionTimestamp() { return executionTimestamp; }
    public void setExecutionTimestamp(LocalDateTime executionTimestamp) { this.executionTimestamp = executionTimestamp; }
    
    public List<QualityGateCheck> getQualityChecks() { return qualityChecks; }
    public void setQualityChecks(List<QualityGateCheck> qualityChecks) { this.qualityChecks = qualityChecks; }
    
    public boolean isGatesPassed() { return gatesPassed; }
    public void setGatesPassed(boolean gatesPassed) { this.gatesPassed = gatesPassed; }
    
    public List<String> getFailedGates() { return failedGates; }
    public void setFailedGates(List<String> failedGates) { this.failedGates = failedGates; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}

/**
 * Technical Debt Violation
 * 
 * Represents a detected violation of technical debt prevention rules
 */
class TechnicalDebtViolation {
    
    private String ruleName;
    private String description;
    private TechnicalDebtSeverity severity;
    private LocalDateTime detectedAt;
    private String component;
    private String recommendation;
    
    public TechnicalDebtViolation(String ruleName, String description, TechnicalDebtSeverity severity, LocalDateTime detectedAt) {
        this.ruleName = ruleName;
        this.description = description;
        this.severity = severity;
        this.detectedAt = detectedAt;
    }
    
    // Getters and setters
    public String getRuleName() { return ruleName; }
    public void setRuleName(String ruleName) { this.ruleName = ruleName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public TechnicalDebtSeverity getSeverity() { return severity; }
    public void setSeverity(TechnicalDebtSeverity severity) { this.severity = severity; }
    
    public LocalDateTime getDetectedAt() { return detectedAt; }
    public void setDetectedAt(LocalDateTime detectedAt) { this.detectedAt = detectedAt; }
    
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    
    public String getRecommendation() { return recommendation; }
    public void setRecommendation(String recommendation) { this.recommendation = recommendation; }
}

/**
 * Technical Debt Severity Levels
 */
enum TechnicalDebtSeverity {
    LOW(1.0),
    MEDIUM(2.0),
    HIGH(3.0),
    CRITICAL(5.0);
    
    private final double weight;
    
    TechnicalDebtSeverity(double weight) {
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
}

/**
 * Technical Debt Rule
 * 
 * Defines a rule for detecting technical debt
 */
class TechnicalDebtRule {
    
    private String ruleName;
    private String description;
    private TechnicalDebtSeverity severity;
    private TechnicalDebtChecker checker;
    
    public TechnicalDebtRule(String ruleName, String description, TechnicalDebtSeverity severity, TechnicalDebtChecker checker) {
        this.ruleName = ruleName;
        this.description = description;
        this.severity = severity;
        this.checker = checker;
    }
    
    // Getters
    public String getRuleName() { return ruleName; }
    public String getDescription() { return description; }
    public TechnicalDebtSeverity getSeverity() { return severity; }
    public TechnicalDebtChecker getChecker() { return checker; }
}

/**
 * Functional interface for technical debt checking
 */
@FunctionalInterface
interface TechnicalDebtChecker {
    List<TechnicalDebtViolation> check() throws Exception;
}

/**
 * Quality Gate Check
 * 
 * Represents a single quality gate check result
 */
class QualityGateCheck {
    
    private String gateName;
    private boolean passed;
    private String description;
    private LocalDateTime checkedAt;
    
    public QualityGateCheck(String gateName, boolean passed, String description) {
        this.gateName = gateName;
        this.passed = passed;
        this.description = description;
        this.checkedAt = LocalDateTime.now();
    }
    
    // Getters
    public String getGateName() { return gateName; }
    public boolean isPassed() { return passed; }
    public String getDescription() { return description; }
    public LocalDateTime getCheckedAt() { return checkedAt; }
}

/**
 * Technical Debt Metric
 * 
 * Represents a metric for tracking technical debt
 */
class TechnicalDebtMetric {
    
    private String name;
    private Object value;
    private LocalDateTime timestamp;
    
    public TechnicalDebtMetric(String name, Object value) {
        this.name = name;
        this.value = value;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public String getName() { return name; }
    public Object getValue() { return value; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

/**
 * Technical Debt Alert
 * 
 * Represents an alert for critical technical debt issues
 */
class TechnicalDebtAlert {
    
    private String id;
    private String ruleName;
    private String message;
    private TechnicalDebtSeverity severity;
    private LocalDateTime createdAt;
    private boolean acknowledged;
    
    public TechnicalDebtAlert(String ruleName, String message, TechnicalDebtSeverity severity, LocalDateTime createdAt) {
        this.id = UUID.randomUUID().toString();
        this.ruleName = ruleName;
        this.message = message;
        this.severity = severity;
        this.createdAt = createdAt;
        this.acknowledged = false;
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getRuleName() { return ruleName; }
    public String getMessage() { return message; }
    public TechnicalDebtSeverity getSeverity() { return severity; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }
}