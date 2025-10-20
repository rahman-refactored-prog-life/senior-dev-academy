package com.learningportal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Compilation Validation Engine Implementation
 * 
 * Provides automated compilation checking, error parsing, and resolution planning
 * using Maven build system integration.
 */
@Service
public class CompilationValidationEngineImpl implements CompilationValidationEngine {
    
    private static final Logger log = LoggerFactory.getLogger(CompilationValidationEngineImpl.class);
    
    // Regex patterns for parsing Maven compilation output
    private static final Pattern ERROR_PATTERN = Pattern.compile(
        "\\[ERROR\\]\\s+(.+?):(\\d+):(\\d+):\\s+(.+)"
    );
    
    private static final Pattern WARNING_PATTERN = Pattern.compile(
        "\\[WARNING\\]\\s+(.+?):(\\d+):(\\d+):\\s+(.+)"
    );
    
    @Override
    public CompilationResult validateAllSources() {
        log.info("🔍 Starting compilation validation...");
        
        long startTime = System.currentTimeMillis();
        
        try {
            // Execute Maven compile command
            ProcessBuilder processBuilder = new ProcessBuilder("mvn", "clean", "compile", "-q");
            processBuilder.redirectErrorStream(true);
            
            Process process = processBuilder.start();
            
            // Capture output
            List<String> output = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.add(line);
                }
            }
            
            int exitCode = process.waitFor();
            long compilationTime = System.currentTimeMillis() - startTime;
            
            // Parse results
            List<CompilationError> errors = parseErrors(output);
            List<CompilationWarning> warnings = parseWarnings(output);
            
            CompilationResult result;
            if (exitCode == 0 && errors.isEmpty()) {
                result = CompilationResult.success(compilationTime);
                log.info("✅ Compilation successful in {}ms", compilationTime);
            } else {
                result = CompilationResult.failure(errors, compilationTime);
                log.warn("❌ Compilation failed with {} errors, {} warnings in {}ms", 
                        errors.size(), warnings.size(), compilationTime);
            }
            
            result.setWarnings(warnings);
            result.setWarningCount(warnings.size());
            result.setJavaVersion(System.getProperty("java.version"));
            
            return result;
            
        } catch (IOException | InterruptedException e) {
            log.error("Failed to execute compilation validation", e);
            CompilationResult result = new CompilationResult();
            result.setSuccess(false);
            result.setErrorCount(1);
            result.setCompilationTimeMs(System.currentTimeMillis() - startTime);
            
            CompilationError error = new CompilationError();
            error.setErrorMessage("Failed to execute Maven compilation: " + e.getMessage());
            error.setErrorType(CompilationError.ErrorType.GENERIC_ERROR);
            result.setErrors(List.of(error));
            
            return result;
        }
    }
    
    @Override
    public List<CompilationError> identifyCompilationErrors() {
        CompilationResult result = validateAllSources();
        return result.getErrors() != null ? result.getErrors() : new ArrayList<>();
    }
    
    @Override
    public ResolutionPlan generateResolutionPlan(List<CompilationError> errors) {
        log.info("📋 Generating resolution plan for {} errors", errors.size());
        
        List<ResolutionStep> steps = new ArrayList<>();
        long estimatedTime = 0;
        
        for (CompilationError error : errors) {
            ResolutionStep step = createResolutionStep(error);
            if (step != null) {
                steps.add(step);
                estimatedTime += estimateStepTime(step);
            }
        }
        
        ResolutionPlan plan = new ResolutionPlan(steps, estimatedTime, 
                                               determineAutomationLevel(steps));
        
        // Add prerequisites and risks
        plan.setPrerequisites(Arrays.asList(
            "Java 21 installed and configured",
            "Maven 3.6+ installed",
            "Write access to project files"
        ));
        
        plan.setRisks(Arrays.asList(
            "Code changes may affect functionality",
            "Dependencies updates may introduce breaking changes"
        ));
        
        plan.setRollbackPlan("Git reset to previous working commit");
        
        log.info("📋 Generated {} with {} steps", plan.getSummary(), steps.size());
        return plan;
    }
    
    @Override
    public boolean executeCompilationFix(ResolutionPlan plan) {
        log.info("🔧 Executing resolution plan with {} steps", plan.getStepCount());
        
        if (!plan.isFullyAutomated()) {
            log.warn("⚠️ Plan contains manual steps - cannot fully automate");
            return false;
        }
        
        for (ResolutionStep step : plan.getSteps()) {
            if (!executeStep(step)) {
                log.error("❌ Failed to execute step: {}", step.getDescription());
                return false;
            }
        }
        
        // Validate fix by running compilation again
        CompilationResult result = validateAllSources();
        boolean success = result.isSuccess();
        
        if (success) {
            log.info("✅ Resolution plan executed successfully");
        } else {
            log.error("❌ Resolution plan failed - {} errors remain", result.getErrorCount());
        }
        
        return success;
    }
    
    // Private helper methods
    
    private List<CompilationError> parseErrors(List<String> output) {
        List<CompilationError> errors = new ArrayList<>();
        
        for (String line : output) {
            Matcher matcher = ERROR_PATTERN.matcher(line);
            if (matcher.find()) {
                CompilationError error = new CompilationError();
                error.setFileName(matcher.group(1));
                error.setLineNumber(Integer.parseInt(matcher.group(2)));
                error.setColumnNumber(Integer.parseInt(matcher.group(3)));
                error.setErrorMessage(matcher.group(4));
                error.setErrorType(classifyError(matcher.group(4)));
                error.setSuggestedFix(generateSuggestedFix(error));
                errors.add(error);
            }
        }
        
        return errors;
    }
    
    private List<CompilationWarning> parseWarnings(List<String> output) {
        List<CompilationWarning> warnings = new ArrayList<>();
        
        for (String line : output) {
            Matcher matcher = WARNING_PATTERN.matcher(line);
            if (matcher.find()) {
                CompilationWarning warning = new CompilationWarning();
                warning.setFileName(matcher.group(1));
                warning.setLineNumber(Integer.parseInt(matcher.group(2)));
                warning.setColumnNumber(Integer.parseInt(matcher.group(3)));
                warning.setWarningMessage(matcher.group(4));
                warning.setWarningType(classifyWarning(matcher.group(4)));
                warnings.add(warning);
            }
        }
        
        return warnings;
    }
    
    private CompilationError.ErrorType classifyError(String errorMessage) {
        String lowerMessage = errorMessage.toLowerCase();
        
        if (lowerMessage.contains("cannot find symbol") || lowerMessage.contains("package does not exist")) {
            return CompilationError.ErrorType.MISSING_IMPORT;
        } else if (lowerMessage.contains("incompatible types") || lowerMessage.contains("cannot be applied")) {
            return CompilationError.ErrorType.TYPE_ERROR;
        } else if (lowerMessage.contains("expected") || lowerMessage.contains("illegal start")) {
            return CompilationError.ErrorType.SYNTAX_ERROR;
        } else if (lowerMessage.contains("annotation") || lowerMessage.contains("processor")) {
            return CompilationError.ErrorType.ANNOTATION_PROCESSING;
        } else {
            return CompilationError.ErrorType.GENERIC_ERROR;
        }
    }
    
    private CompilationWarning.WarningType classifyWarning(String warningMessage) {
        String lowerMessage = warningMessage.toLowerCase();
        
        if (lowerMessage.contains("deprecated")) {
            return CompilationWarning.WarningType.DEPRECATION;
        } else if (lowerMessage.contains("unchecked")) {
            return CompilationWarning.WarningType.UNCHECKED;
        } else if (lowerMessage.contains("unused")) {
            return CompilationWarning.WarningType.UNUSED;
        } else if (lowerMessage.contains("raw type")) {
            return CompilationWarning.WarningType.RAW_TYPES;
        } else {
            return CompilationWarning.WarningType.GENERIC;
        }
    }
    
    private String generateSuggestedFix(CompilationError error) {
        switch (error.getErrorType()) {
            case MISSING_IMPORT:
                return "Add missing import statement or check dependency in pom.xml";
            case TYPE_ERROR:
                return "Check type compatibility and casting";
            case SYNTAX_ERROR:
                return "Fix syntax error - check brackets, semicolons, and keywords";
            case ANNOTATION_PROCESSING:
                return "Check annotation processor configuration in pom.xml";
            default:
                return "Review error message and fix accordingly";
        }
    }
    
    private ResolutionStep createResolutionStep(CompilationError error) {
        ResolutionStep step = new ResolutionStep();
        step.setOrder(1);
        step.setFilePath(error.getFileName());
        
        switch (error.getErrorType()) {
            case MISSING_IMPORT:
                step.setDescription("Add missing import or dependency");
                step.setStepType(ResolutionStep.StepType.FILE_MODIFICATION);
                step.setAutomated(false); // Requires manual analysis
                break;
            case SYNTAX_ERROR:
                step.setDescription("Fix syntax error");
                step.setStepType(ResolutionStep.StepType.FILE_MODIFICATION);
                step.setAutomated(false); // Requires manual fix
                break;
            case ANNOTATION_PROCESSING:
                step.setDescription("Fix annotation processing configuration");
                step.setStepType(ResolutionStep.StepType.CONFIGURATION_CHANGE);
                step.setAutomated(true); // Can be automated
                step.setCommand("mvn clean compile -Dmaven.compiler.proc=none");
                break;
            default:
                step.setDescription("Fix compilation error: " + error.getErrorMessage());
                step.setStepType(ResolutionStep.StepType.FILE_MODIFICATION);
                step.setAutomated(false);
        }
        
        return step;
    }
    
    private long estimateStepTime(ResolutionStep step) {
        return step.isAutomated() ? 2 : 10; // 2 minutes for automated, 10 for manual
    }
    
    private ResolutionPlan.PlanType determineAutomationLevel(List<ResolutionStep> steps) {
        long automatedCount = steps.stream().filter(ResolutionStep::isAutomated).count();
        
        if (automatedCount == steps.size()) {
            return ResolutionPlan.PlanType.AUTOMATIC;
        } else if (automatedCount == 0) {
            return ResolutionPlan.PlanType.MANUAL;
        } else {
            return ResolutionPlan.PlanType.HYBRID;
        }
    }
    
    private boolean executeStep(ResolutionStep step) {
        if (!step.isAutomated() || !step.hasCommand()) {
            return false;
        }
        
        try {
            log.info("🔧 Executing: {}", step.getCommand());
            
            ProcessBuilder processBuilder = new ProcessBuilder(step.getCommand().split("\\s+"));
            Process process = processBuilder.start();
            int exitCode = process.waitFor();
            
            boolean success = exitCode == 0;
            log.info(success ? "✅ Step completed successfully" : "❌ Step failed with exit code {}", exitCode);
            
            return success;
            
        } catch (IOException | InterruptedException e) {
            log.error("Failed to execute step: {}", step.getCommand(), e);
            return false;
        }
    }
}