package com.learningportal.service;

import java.util.List;

/**
 * Compilation Validation Engine Interface
 * 
 * Provides automated compilation checking, error reporting, and resolution planning
 * for maintaining a bulletproof technical foundation.
 */
public interface CompilationValidationEngine {
    
    /**
     * Validates all source files for compilation success
     * @return CompilationResult with detailed status and metrics
     */
    CompilationResult validateAllSources();
    
    /**
     * Identifies specific compilation errors with file locations
     * @return List of compilation errors with detailed information
     */
    List<CompilationError> identifyCompilationErrors();
    
    /**
     * Generates a resolution plan for fixing compilation issues
     * @param errors List of compilation errors to resolve
     * @return ResolutionPlan with step-by-step fix instructions
     */
    ResolutionPlan generateResolutionPlan(List<CompilationError> errors);
    
    /**
     * Attempts to automatically apply compilation fixes
     * @param plan Resolution plan to execute
     * @return true if fixes were applied successfully, false otherwise
     */
    boolean executeCompilationFix(ResolutionPlan plan);
}