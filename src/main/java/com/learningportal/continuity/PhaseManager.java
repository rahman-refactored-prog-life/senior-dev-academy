package com.learningportal.continuity;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Phase Management System that provides clear phase-by-phase implementation guidance
 * with specific deliverables, time estimates, and automated progress tracking.
 */
@Service
public class PhaseManager {
    
    private static final Logger logger = LoggerFactory.getLogger(PhaseManager.class);
    
    private final TechnicalEnvironmentTracker environmentTracker;
    private final QualityGateManager qualityGateManager;
    
    public PhaseManager(TechnicalEnvironmentTracker environmentTracker,
                       QualityGateManager qualityGateManager) {
        this.environmentTracker = environmentTracker;
        this.qualityGateManager = qualityGateManager;
    }
    
    /**
     * Creates clear phase definitions with specific deliverables and time estimates.
     * 
     * @return List of all defined phases with detailed specifications
     */
    public List<PhaseDefinition> createPhaseDefinitions() {
        logger.info("Creating comprehensive phase definitions for systematic implementation");
        
        List<PhaseDefinition> phases = new ArrayList<>();
        
        // Phase 1: Foundation & Infrastructure
        phases.add(createPhase(
            "1", "Foundation & Infrastructure",
            "Establish core project foundation with enterprise practices and PostgreSQL setup",
            Arrays.asList(
                "Complete backend technical debt resolution",
                "Implement session continuity automation",
                "Set up enterprise AWS deployment infrastructure",
                "Create REST API with Swagger documentation"
            ),
            120, // 2 hours estimated
            Arrays.asList("Compilation successful", "Database connected", "APIs documented", "Quality gates passing")
        ));
        
        // Phase 2: Learning Methodology & UI
        phases.add(createPhase(
            "2", "Learning Methodology & UI Foundation",
            "Implement zero-experience learning methodology and user interface",
            Arrays.asList(
                "Create real-world analogies and beginner-friendly approach",
                "Build AWS-inspired responsive UI design",
                "Implement navigation and user experience patterns",
                "Create distraction-free learning environment"
            ),
            90, // 1.5 hours estimated
            Arrays.asList("UI components functional", "Learning methodology validated", "User experience tested")
        ));
        
        // Phase 3: Interactive Learning Features
        phases.add(createPhase(
            "3", "Interactive Learning Features",
            "Implement Monaco code editor, visualizations, and multi-language support",
            Arrays.asList(
                "Integrate Monaco Editor with multi-language execution",
                "Create interactive visualizations and animations",
                "Implement multi-language code examples",
                "Build rich note-taking system with embedded + central features"
            ),
            150, // 2.5 hours estimated
            Arrays.asList("Code editor functional", "Visualizations working", "Notes system operational")
        ));
        
        // Phase 4: Core Technologies
        phases.add(createPhase(
            "4", "Core Backend & Frontend Technologies",
            "Complete Java ecosystem and React mastery implementation",
            Arrays.asList(
                "Implement Java + Spring + Hibernate (beginner to expert)",
                "Create comprehensive database systems (SQL + NoSQL)",
                "Build React complete mastery (beginner to Amazon Senior SDE)",
                "Implement Node.js complete mastery (25 topics, 700+ questions)"
            ),
            180, // 3 hours estimated
            Arrays.asList("Java ecosystem complete", "Database systems functional", "React components working", "Node.js examples executable")
        ));
        
        // Phase 5: Computer Science Fundamentals
        phases.add(createPhase(
            "5", "Computer Science Fundamentals",
            "Implement data structures, algorithms, and system design coverage",
            Arrays.asList(
                "Create every data structure with visualizations",
                "Implement all algorithm categories with optimization techniques",
                "Build scalable systems design with real-world cases",
                "Create zero-experience learning methodology integration"
            ),
            200, // 3.3 hours estimated
            Arrays.asList("All data structures implemented", "Algorithm categories complete", "System design functional")
        ));
        
        // Phase 6: Interview Preparation
        phases.add(createPhase(
            "6", "Interview Preparation System",
            "Implement 8000+ FAANG questions with dual organization system",
            Arrays.asList(
                "Create 8000+ questions database with company attribution",
                "Implement dual organization (embedded + central)",
                "Build Amazon Leadership Principles mastery",
                "Create mock interview and assessment system"
            ),
            160, // 2.7 hours estimated
            Arrays.asList("Questions database populated", "Dual organization working", "Leadership principles integrated")
        ));
        
        // Phase 7: Cloud Certifications
        phases.add(createPhase(
            "7", "AWS Cloud Certifications",
            "Complete AWS certification preparation modules",
            Arrays.asList(
                "AWS Cloud Practitioner complete preparation",
                "AWS Solutions Architect Associate preparation",
                "AWS Machine Learning Engineer certification prep",
                "Integration with practical AWS deployment"
            ),
            120, // 2 hours estimated
            Arrays.asList("All AWS modules complete", "Certification readiness validated", "Practical deployment working")
        ));
        
        // Phase 8: Final Integration
        phases.add(createPhase(
            "8", "World-Class Platform Integration",
            "Master platform integration with world-class standards",
            Arrays.asList(
                "Integrate all learning modules seamlessly",
                "Implement world-class learning standards",
                "Optimize performance and user experience",
                "Complete comprehensive testing and validation"
            ),
            100, // 1.7 hours estimated
            Arrays.asList("All modules integrated", "Performance optimized", "Quality standards met", "Testing complete")
        ));
        
        logger.info("Created {} phase definitions with total estimated time: {} minutes", 
                   phases.size(), phases.stream().mapToInt(PhaseDefinition::getEstimatedMinutes).sum());
        
        return phases;
    }
    
    /**
     * Implements automated progress tracking with real-time metrics.
     * 
     * @param currentPhase The current phase being tracked
     * @param sessionState Current session state
     * @return Progress tracking result with detailed metrics
     */
    public ProgressTrackingResult trackProgress(String currentPhase, SessionState sessionState) {
        logger.info("Tracking progress for phase: {}", currentPhase);
        
        ProgressTrackingResult result = new ProgressTrackingResult();
        result.setCurrentPhase(currentPhase);
        result.setTrackingTime(LocalDateTime.now());
        
        // Get phase definitions
        List<PhaseDefinition> allPhases = createPhaseDefinitions();
        PhaseDefinition current = findPhaseById(allPhases, currentPhase);
        
        if (current != null) {
            // Calculate phase progress
            double phaseProgress = calculatePhaseProgress(current, sessionState);
            result.setPhaseProgress(phaseProgress);
            
            // Calculate overall progress
            double overallProgress = calculateOverallProgress(allPhases, currentPhase, phaseProgress);
            result.setOverallProgress(overallProgress);
            
            // Calculate time metrics
            result.setTimeSpent((int) sessionState.getDurationMinutes());
            result.setEstimatedTimeRemaining(calculateRemainingTime(allPhases, currentPhase, phaseProgress));
            
            // Track deliverables completion
            result.setCompletedDeliverables(countCompletedDeliverables(current, sessionState));
            result.setTotalDeliverables(current.getDeliverables().size());
            
            // Generate next actions
            result.setNextActions(generateNextActions(current, sessionState));
            
            // Assess phase readiness
            result.setPhaseComplete(isPhaseComplete(current, sessionState));
            result.setReadyForNextPhase(result.isPhaseComplete() && isPhaseValidationComplete(current, sessionState));
            
        } else {
            logger.warn("Phase not found: {}", currentPhase);
            result.setPhaseProgress(0.0);
            result.setOverallProgress(0.0);
        }
        
        logger.info("Progress tracking completed: Phase {}% complete, Overall {}% complete", 
                   String.format("%.1f", result.getPhaseProgress()), 
                   String.format("%.1f", result.getOverallProgress()));
        
        return result;
    }
    
    /**
     * Develops phase completion validation against requirements.
     * 
     * @param phase The phase to validate
     * @param sessionState Current session state
     * @return Phase validation result with detailed assessment
     */
    public PhaseValidationResult validatePhaseCompletion(PhaseDefinition phase, SessionState sessionState) {
        logger.info("Validating completion for phase: {}", phase.getPhaseId());
        
        PhaseValidationResult result = new PhaseValidationResult();
        result.setPhaseId(phase.getPhaseId());
        result.setPhaseName(phase.getName());
        result.setValidationTime(LocalDateTime.now());
        
        List<String> validationIssues = new ArrayList<>();
        List<String> recommendations = new ArrayList<>();
        double validationScore = 100.0;
        
        // Validate deliverables completion
        for (String deliverable : phase.getDeliverables()) {
            boolean completed = isDeliverableCompleted(deliverable, sessionState);
            if (!completed) {
                validationIssues.add("Incomplete deliverable: " + deliverable);
                validationScore -= 15.0;
                recommendations.add("Complete " + deliverable + " before proceeding");
            }
        }
        
        // Validate success criteria
        for (String criterion : phase.getSuccessCriteria()) {
            boolean met = isSuccessCriteriaMet(criterion, sessionState);
            if (!met) {
                validationIssues.add("Success criteria not met: " + criterion);
                validationScore -= 10.0;
                recommendations.add("Ensure " + criterion + " is satisfied");
            }
        }
        
        // Validate technical requirements
        CompilationStatus compilationStatus = environmentTracker.captureCompilationStatus();
        if (!compilationStatus.isSuccessful()) {
            validationIssues.add("Compilation errors present");
            validationScore -= 20.0;
            recommendations.add("Resolve all compilation errors");
        }
        
        // Validate quality gates
        QualityGateResults qualityResults = qualityGateManager.runAllQualityGates(sessionState);
        if (!qualityResults.isOverallPassed()) {
            validationIssues.add("Quality gates not passing: " + 
                               (qualityResults.getTotalGates() - qualityResults.getPassedGates()) + " failed");
            validationScore -= 15.0;
            recommendations.add("Address quality gate failures before proceeding");
        }
        
        result.setValidationScore(Math.max(0, validationScore));
        result.setPhaseComplete(validationScore >= 80.0); // Require 80% to pass
        result.setValidationIssues(validationIssues);
        result.setRecommendations(recommendations);
        
        // Generate next phase recommendation
        if (result.isPhaseComplete()) {
            result.setNextPhaseRecommendation(generateNextPhaseRecommendation(phase));
        }
        
        logger.info("Phase validation completed: {}% score, Complete: {}", 
                   String.format("%.1f", validationScore), result.isPhaseComplete());
        
        return result;
    }
    
    /**
     * Generates recommendations for next phase based on current completion.
     * 
     * @param currentPhase The current completed phase
     * @return Next phase recommendation with preparation steps
     */
    public NextPhaseRecommendation generateNextPhaseRecommendation(PhaseDefinition currentPhase) {
        logger.info("Generating next phase recommendation after: {}", currentPhase.getName());
        
        NextPhaseRecommendation recommendation = new NextPhaseRecommendation();
        recommendation.setCurrentPhaseId(currentPhase.getPhaseId());
        recommendation.setRecommendationTime(LocalDateTime.now());
        
        // Find next phase
        List<PhaseDefinition> allPhases = createPhaseDefinitions();
        int currentIndex = findPhaseIndex(allPhases, currentPhase.getPhaseId());
        
        if (currentIndex >= 0 && currentIndex < allPhases.size() - 1) {
            PhaseDefinition nextPhase = allPhases.get(currentIndex + 1);
            
            recommendation.setNextPhaseId(nextPhase.getPhaseId());
            recommendation.setNextPhaseName(nextPhase.getName());
            recommendation.setEstimatedDuration(nextPhase.getEstimatedMinutes());
            
            // Generate preparation steps
            List<String> preparationSteps = Arrays.asList(
                "Review " + nextPhase.getName() + " requirements and deliverables",
                "Ensure all dependencies from " + currentPhase.getName() + " are complete",
                "Validate technical environment is ready for " + nextPhase.getName(),
                "Estimate time allocation for " + nextPhase.getEstimatedMinutes() + " minutes of work",
                "Prepare development environment for " + nextPhase.getName() + " tasks"
            );
            
            recommendation.setPreparationSteps(preparationSteps);
            recommendation.setReadyToProceed(true);
            
        } else if (currentIndex == allPhases.size() - 1) {
            // Last phase completed
            recommendation.setNextPhaseId("COMPLETE");
            recommendation.setNextPhaseName("Project Complete");
            recommendation.setEstimatedDuration(0);
            recommendation.setPreparationSteps(Arrays.asList(
                "Conduct final system validation and testing",
                "Prepare comprehensive project documentation",
                "Perform final quality assurance review",
                "Celebrate successful project completion!"
            ));
            recommendation.setReadyToProceed(true);
        } else {
            recommendation.setReadyToProceed(false);
            recommendation.setPreparationSteps(Arrays.asList("Unable to determine next phase"));
        }
        
        logger.info("Next phase recommendation: {} -> {}", 
                   currentPhase.getName(), recommendation.getNextPhaseName());
        
        return recommendation;
    }
    
    // Private helper methods
    
    private PhaseDefinition createPhase(String id, String name, String description, 
                                      List<String> deliverables, int estimatedMinutes, 
                                      List<String> successCriteria) {
        PhaseDefinition phase = new PhaseDefinition();
        phase.setPhaseId(id);
        phase.setName(name);
        phase.setDescription(description);
        phase.setDeliverables(deliverables);
        phase.setEstimatedMinutes(estimatedMinutes);
        phase.setSuccessCriteria(successCriteria);
        phase.setCreatedTime(LocalDateTime.now());
        return phase;
    }
    
    private PhaseDefinition findPhaseById(List<PhaseDefinition> phases, String phaseId) {
        return phases.stream()
            .filter(phase -> phase.getPhaseId().equals(phaseId))
            .findFirst()
            .orElse(null);
    }
    
    private int findPhaseIndex(List<PhaseDefinition> phases, String phaseId) {
        for (int i = 0; i < phases.size(); i++) {
            if (phases.get(i).getPhaseId().equals(phaseId)) {
                return i;
            }
        }
        return -1;
    }
    
    private double calculatePhaseProgress(PhaseDefinition phase, SessionState sessionState) {
        int completedDeliverables = countCompletedDeliverables(phase, sessionState);
        return (double) completedDeliverables / phase.getDeliverables().size() * 100.0;
    }
    
    private double calculateOverallProgress(List<PhaseDefinition> allPhases, String currentPhaseId, double currentPhaseProgress) {
        int currentIndex = findPhaseIndex(allPhases, currentPhaseId);
        if (currentIndex < 0) return 0.0;
        
        // Calculate progress as: (completed phases + current phase progress) / total phases
        double completedPhases = currentIndex;
        double currentContribution = currentPhaseProgress / 100.0;
        
        return (completedPhases + currentContribution) / allPhases.size() * 100.0;
    }
    
    private int calculateRemainingTime(List<PhaseDefinition> allPhases, String currentPhaseId, double currentPhaseProgress) {
        int currentIndex = findPhaseIndex(allPhases, currentPhaseId);
        if (currentIndex < 0) return 0;
        
        // Time remaining in current phase
        PhaseDefinition currentPhase = allPhases.get(currentIndex);
        int currentPhaseRemaining = (int) (currentPhase.getEstimatedMinutes() * (100.0 - currentPhaseProgress) / 100.0);
        
        // Time for remaining phases
        int remainingPhasesTime = 0;
        for (int i = currentIndex + 1; i < allPhases.size(); i++) {
            remainingPhasesTime += allPhases.get(i).getEstimatedMinutes();
        }
        
        return currentPhaseRemaining + remainingPhasesTime;
    }
    
    private int countCompletedDeliverables(PhaseDefinition phase, SessionState sessionState) {
        // Simplified logic - in real implementation, would check actual deliverable completion
        int completed = 0;
        for (String deliverable : phase.getDeliverables()) {
            if (isDeliverableCompleted(deliverable, sessionState)) {
                completed++;
            }
        }
        return completed;
    }
    
    private boolean isDeliverableCompleted(String deliverable, SessionState sessionState) {
        // Simplified logic - check if deliverable appears in completed tasks or success criteria
        if (sessionState.getCompletedTasks() != null) {
            return sessionState.getCompletedTasks().stream()
                .anyMatch(task -> task.getDescription().toLowerCase().contains(deliverable.toLowerCase()));
        }
        return false;
    }
    
    private boolean isSuccessCriteriaMet(String criterion, SessionState sessionState) {
        // Simplified logic - check against compilation status and other indicators
        if (criterion.toLowerCase().contains("compilation") && sessionState.getCompilationStatus() != null) {
            return sessionState.getCompilationStatus().isSuccessful();
        }
        if (criterion.toLowerCase().contains("database") && sessionState.getDatabaseStatus() != null) {
            return sessionState.getDatabaseStatus().isConnected();
        }
        return true; // Default to true for other criteria
    }
    
    private List<String> generateNextActions(PhaseDefinition phase, SessionState sessionState) {
        List<String> actions = new ArrayList<>();
        
        // Find incomplete deliverables
        for (String deliverable : phase.getDeliverables()) {
            if (!isDeliverableCompleted(deliverable, sessionState)) {
                actions.add("Complete: " + deliverable);
                if (actions.size() >= 3) break; // Limit to top 3 actions
            }
        }
        
        if (actions.isEmpty()) {
            actions.add("Validate phase completion and prepare for next phase");
        }
        
        return actions;
    }
    
    private boolean isPhaseComplete(PhaseDefinition phase, SessionState sessionState) {
        return countCompletedDeliverables(phase, sessionState) >= phase.getDeliverables().size();
    }
    
    private boolean isPhaseValidationComplete(PhaseDefinition phase, SessionState sessionState) {
        PhaseValidationResult validation = validatePhaseCompletion(phase, sessionState);
        return validation.isPhaseComplete();
    }
}