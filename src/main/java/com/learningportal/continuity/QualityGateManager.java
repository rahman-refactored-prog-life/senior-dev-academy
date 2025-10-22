package com.learningportal.continuity;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Automated Quality Gates System that implements compilation, documentation, 
 * progress, and quality assurance gates with emergency bypass procedures.
 */
@Service
public class QualityGateManager {
    
    private static final Logger logger = LoggerFactory.getLogger(QualityGateManager.class);
    
    private final TechnicalEnvironmentTracker environmentTracker;
    private final DocumentationSynchronizer documentationSynchronizer;
    
    public QualityGateManager(TechnicalEnvironmentTracker environmentTracker,
                             DocumentationSynchronizer documentationSynchronizer) {
        this.environmentTracker = environmentTracker;
        this.documentationSynchronizer = documentationSynchronizer;
    }
    
    /**
     * Runs all quality gates in sequence and returns comprehensive results.
     * 
     * @param sessionState Current session state for validation
     * @return Complete quality gate results with pass/fail status
     */
    public QualityGateResults runAllGates(SessionState sessionState) {
        return runAllQualityGates(sessionState);
    }
    
    /**
     * Runs all quality gates in sequence and returns comprehensive results.
     * 
     * @param sessionState Current session state for validation
     * @return Complete quality gate results with pass/fail status
     */
    public QualityGateResults runAllQualityGates(SessionState sessionState) {
        logger.info("Running all quality gates for session: {}", sessionState.getSessionId());
        
        QualityGateResults results = new QualityGateResults();
        results.setExecutionTime(LocalDateTime.now());
        results.setSessionId(sessionState.getSessionId());
        
        List<GateResult> gateResults = new ArrayList<>();
        
        // Run each quality gate
        gateResults.add(runCompilationGate());
        gateResults.add(runDocumentationGate(sessionState));
        gateResults.add(runProgressGate(sessionState));
        gateResults.add(runQualityGate(sessionState));
        
        results.setGateResults(gateResults);
        results.setOverallPassed(calculateOverallResult(gateResults));
        results.setPassedGates(countPassedGates(gateResults));
        results.setTotalGates(gateResults.size());
        
        // Generate recommendations
        results.setRecommendations(generateRecommendations(gateResults));
        
        logger.info("Quality gates completed: {}/{} passed, Overall: {}", 
                   results.getPassedGates(), results.getTotalGates(), 
                   results.isOverallPassed() ? "PASSED" : "FAILED");
        
        return results;
    }
    
    /**
     * Runs compilation quality gate with automated compilation checking.
     * 
     * @return Compilation gate result with detailed error reporting
     */
    public GateResult runCompilationGate() {
        logger.info("Running compilation quality gate");
        
        GateResult result = new GateResult();
        result.setGateName("Compilation Gate");
        result.setExecutionTime(LocalDateTime.now());
        
        try {
            // Capture current compilation status
            CompilationStatus compilationStatus = environmentTracker.captureCompilationStatus();
            
            result.setPassed(compilationStatus.isSuccessful());
            result.setScore(compilationStatus.isSuccessful() ? 100.0 : 0.0);
            
            List<String> issues = new ArrayList<>();
            List<String> recommendations = new ArrayList<>();
            
            if (!compilationStatus.isSuccessful()) {
                issues.add("Compilation failed with " + compilationStatus.getErrorCount() + " errors");
                
                if (compilationStatus.getErrors() != null) {
                    for (CompilationError error : compilationStatus.getErrors()) {
                        issues.add("Error in " + error.getFileName() + ": " + error.getErrorMessage());
                    }
                }
                
                recommendations.add("Fix all compilation errors before proceeding");
                recommendations.add("Review error messages and resolve syntax issues");
                recommendations.add("Ensure all dependencies are properly imported");
            } else {
                if (compilationStatus.getWarningCount() > 0) {
                    issues.add("Compilation successful but " + compilationStatus.getWarningCount() + " warnings found");
                    recommendations.add("Consider addressing compilation warnings for code quality");
                }
            }
            
            result.setIssues(issues);
            result.setRecommendations(recommendations);
            result.setDetails("Compilation status: " + (compilationStatus.isSuccessful() ? "SUCCESS" : "FAILED") +
                            ", Errors: " + compilationStatus.getErrorCount() +
                            ", Warnings: " + compilationStatus.getWarningCount());
            
        } catch (Exception e) {
            logger.error("Error running compilation gate", e);
            result.setPassed(false);
            result.setScore(0.0);
            result.setIssues(List.of("Error executing compilation gate: " + e.getMessage()));
            result.setRecommendations(List.of("Check build environment and try again"));
        }
        
        logger.info("Compilation gate completed: {}", result.isPassed() ? "PASSED" : "FAILED");
        return result;
    }
    
    /**
     * Runs documentation quality gate requiring all files to be updated.
     * 
     * @param sessionState Session state for documentation validation
     * @return Documentation gate result with file update status
     */
    public GateResult runDocumentationGate(SessionState sessionState) {
        logger.info("Running documentation quality gate");
        
        GateResult result = new GateResult();
        result.setGateName("Documentation Gate");
        result.setExecutionTime(LocalDateTime.now());
        
        try {
            // Update all documentation files
            UpdateSummary updateSummary = documentationSynchronizer.updateAllFiles(sessionState);
            
            int successfulUpdates = updateSummary.getSuccessfulUpdates();
            double successRate = updateSummary.getSuccessRate();
            
            result.setPassed(successRate >= 80.0); // Require 80% success rate
            result.setScore(successRate);
            
            List<String> issues = new ArrayList<>();
            List<String> recommendations = new ArrayList<>();
            
            if (updateSummary.getFailedUpdates() > 0) {
                issues.add("Failed to update " + updateSummary.getFailedUpdates() + " files");
            }
            
            if (!result.isPassed()) {
                recommendations.add("Resolve file update failures before proceeding");
                recommendations.add("Check file permissions and disk space");
                recommendations.add("Validate documentation content format");
            }
            
            // Validate consistency
            ConsistencyReport consistencyReport = documentationSynchronizer.validateConsistency();
            if (!consistencyReport.isOverallConsistent()) {
                issues.add("Documentation consistency issues found: " + consistencyReport.getConsistencyIssues().size());
                recommendations.add("Resolve documentation consistency issues");
            }
            
            result.setIssues(issues);
            result.setRecommendations(recommendations);
            result.setDetails("Documentation updates: " + successfulUpdates + "/" + updateSummary.getTotalFiles() + 
                            " successful (" + String.format("%.1f", successRate) + "%)");
            
        } catch (Exception e) {
            logger.error("Error running documentation gate", e);
            result.setPassed(false);
            result.setScore(0.0);
            result.setIssues(List.of("Error executing documentation gate: " + e.getMessage()));
            result.setRecommendations(List.of("Check documentation system and try again"));
        }
        
        logger.info("Documentation gate completed: {}", result.isPassed() ? "PASSED" : "FAILED");
        return result;
    }
    
    /**
     * Runs progress tracking quality gate verifying accurate metrics.
     * 
     * @param sessionState Session state for progress validation
     * @return Progress gate result with accuracy assessment
     */
    public GateResult runProgressGate(SessionState sessionState) {
        logger.info("Running progress tracking quality gate");
        
        GateResult result = new GateResult();
        result.setGateName("Progress Gate");
        result.setExecutionTime(LocalDateTime.now());
        
        try {
            List<String> issues = new ArrayList<>();
            List<String> recommendations = new ArrayList<>();
            double score = 100.0;
            
            // Validate progress percentage
            if (sessionState.getProgressPercentage() < 0 || sessionState.getProgressPercentage() > 100) {
                issues.add("Invalid progress percentage: " + sessionState.getProgressPercentage());
                score -= 25.0;
            }
            
            // Validate task consistency
            int completedTasks = sessionState.getCompletedTasks() != null ? sessionState.getCompletedTasks().size() : 0;
            int inProgressTasks = sessionState.getInProgressTasks() != null ? sessionState.getInProgressTasks().size() : 0;
            
            if (completedTasks == 0 && sessionState.getProgressPercentage() > 10) {
                issues.add("Progress percentage inconsistent with completed tasks");
                score -= 20.0;
            }
            
            // Validate phase information
            if (sessionState.getLastCompletedPhase() == null || sessionState.getLastCompletedPhase().isEmpty()) {
                issues.add("Last completed phase is not specified");
                score -= 15.0;
            }
            
            // Validate next actions
            if (sessionState.getNextActions() == null || sessionState.getNextActions().isEmpty()) {
                issues.add("Next actions are not defined");
                score -= 10.0;
                recommendations.add("Define clear next actions for session continuation");
            }
            
            // Validate success criteria
            if (sessionState.getSuccessCriteria() == null || sessionState.getSuccessCriteria().isEmpty()) {
                issues.add("Success criteria are not defined");
                score -= 10.0;
                recommendations.add("Define measurable success criteria");
            }
            
            result.setPassed(score >= 70.0); // Require 70% score to pass
            result.setScore(Math.max(0, score));
            result.setIssues(issues);
            result.setRecommendations(recommendations);
            result.setDetails("Progress validation score: " + String.format("%.1f", score) + "%, " +
                            "Completed tasks: " + completedTasks + ", In progress: " + inProgressTasks);
            
        } catch (Exception e) {
            logger.error("Error running progress gate", e);
            result.setPassed(false);
            result.setScore(0.0);
            result.setIssues(List.of("Error executing progress gate: " + e.getMessage()));
            result.setRecommendations(List.of("Check progress tracking system and try again"));
        }
        
        logger.info("Progress gate completed: {}", result.isPassed() ? "PASSED" : "FAILED");
        return result;
    }
    
    /**
     * Runs comprehensive quality assurance gate with detailed checks.
     * 
     * @param sessionState Session state for quality validation
     * @return Quality gate result with comprehensive assessment
     */
    public GateResult runQualityGate(SessionState sessionState) {
        logger.info("Running comprehensive quality assurance gate");
        
        GateResult result = new GateResult();
        result.setGateName("Quality Assurance Gate");
        result.setExecutionTime(LocalDateTime.now());
        
        try {
            List<String> issues = new ArrayList<>();
            List<String> recommendations = new ArrayList<>();
            double score = 100.0;
            
            // Validate code execution
            CodeValidationResult codeValidation = environmentTracker.validateCodeExecution();
            if (!codeValidation.isOverallValid()) {
                issues.add("Code validation failed");
                score -= 30.0;
                recommendations.add("Fix code compilation and execution issues");
            }
            
            // Validate database connectivity
            DatabaseStatus dbStatus = environmentTracker.captureDatabaseStatus();
            if (!dbStatus.isConnected()) {
                issues.add("Database connection failed");
                score -= 20.0;
                recommendations.add("Restore database connectivity");
            }
            
            // Validate session completeness
            if (sessionState.getSessionId() == null || sessionState.getTimestamp() == null) {
                issues.add("Session state is incomplete");
                score -= 15.0;
                recommendations.add("Ensure complete session state capture");
            }
            
            // Validate technical environment
            JavaEnvironmentInfo javaInfo = environmentTracker.captureJavaEnvironment();
            if (javaInfo.getVersion() == null) {
                issues.add("Java environment information incomplete");
                score -= 10.0;
            }
            
            // Validate Maven status
            MavenStatus mavenStatus = environmentTracker.captureMavenStatus();
            if (!mavenStatus.isDependencyResolutionSuccessful()) {
                issues.add("Maven dependency resolution failed");
                score -= 15.0;
                recommendations.add("Resolve Maven dependency issues");
            }
            
            // Check for known issues
            if (sessionState.getKnownIssues() != null && !sessionState.getKnownIssues().isEmpty()) {
                int criticalIssues = (int) sessionState.getKnownIssues().stream()
                    .mapToLong(issue -> issue.getSeverity() == IssueSeverity.CRITICAL ? 1 : 0).sum();
                
                if (criticalIssues > 0) {
                    issues.add("Critical issues present: " + criticalIssues);
                    score -= criticalIssues * 10.0;
                    recommendations.add("Resolve all critical issues before proceeding");
                }
            }
            
            result.setPassed(score >= 75.0); // Require 75% score to pass
            result.setScore(Math.max(0, score));
            result.setIssues(issues);
            result.setRecommendations(recommendations);
            result.setDetails("Quality assessment score: " + String.format("%.1f", score) + "%, " +
                            "Code valid: " + codeValidation.isOverallValid() + 
                            ", DB connected: " + dbStatus.isConnected());
            
        } catch (Exception e) {
            logger.error("Error running quality gate", e);
            result.setPassed(false);
            result.setScore(0.0);
            result.setIssues(List.of("Error executing quality gate: " + e.getMessage()));
            result.setRecommendations(List.of("Check quality assurance system and try again"));
        }
        
        logger.info("Quality gate completed: {}", result.isPassed() ? "PASSED" : "FAILED");
        return result;
    }
    
    /**
     * Executes emergency bypass procedure with explicit justification.
     * 
     * @param gateName Name of the gate to bypass
     * @param justification Explicit justification for bypass
     * @param remediationPlan Plan for addressing bypassed issues
     * @return Bypass result with approval status
     */
    public BypassResult executeEmergencyBypass(String gateName, String justification, String remediationPlan) {
        logger.warn("Emergency bypass requested for gate: {} with justification: {}", gateName, justification);
        
        BypassResult result = new BypassResult();
        result.setGateName(gateName);
        result.setJustification(justification);
        result.setRemediationPlan(remediationPlan);
        result.setBypassTime(LocalDateTime.now());
        
        // Validate bypass request
        if (justification == null || justification.trim().isEmpty()) {
            result.setApproved(false);
            result.setReason("Justification is required for emergency bypass");
            logger.error("Emergency bypass denied: No justification provided");
            return result;
        }
        
        if (remediationPlan == null || remediationPlan.trim().isEmpty()) {
            result.setApproved(false);
            result.setReason("Remediation plan is required for emergency bypass");
            logger.error("Emergency bypass denied: No remediation plan provided");
            return result;
        }
        
        // Approve bypass with conditions
        result.setApproved(true);
        result.setReason("Emergency bypass approved with mandatory remediation");
        result.setConditions(List.of(
            "Must address bypassed issues within 24 hours",
            "Must document all changes made during bypass period",
            "Must validate all quality gates before next major release"
        ));
        
        logger.warn("Emergency bypass APPROVED for gate: {} - Remediation required within 24 hours", gateName);
        return result;
    }
    
    // Helper methods
    
    private boolean calculateOverallResult(List<GateResult> gateResults) {
        return gateResults.stream().allMatch(GateResult::isPassed);
    }
    
    private int countPassedGates(List<GateResult> gateResults) {
        return (int) gateResults.stream().mapToLong(result -> result.isPassed() ? 1 : 0).sum();
    }
    
    private List<String> generateRecommendations(List<GateResult> gateResults) {
        List<String> recommendations = new ArrayList<>();
        
        for (GateResult result : gateResults) {
            if (!result.isPassed() && result.getRecommendations() != null) {
                recommendations.addAll(result.getRecommendations());
            }
        }
        
        if (recommendations.isEmpty()) {
            recommendations.add("All quality gates passed - proceed with confidence");
        }
        
        return recommendations;
    }
}