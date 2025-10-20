package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Compilation Result Data Class
 * 
 * Contains detailed information about compilation status, errors, warnings, and metrics.
 */
public class CompilationResult {
    
    private boolean success;
    private int errorCount;
    private int warningCount;
    private List<CompilationError> errors;
    private List<CompilationWarning> warnings;
    private long compilationTimeMs;
    private LocalDateTime timestamp;
    private String javaVersion;
    private String mavenVersion;
    
    // Constructors
    public CompilationResult() {
        this.timestamp = LocalDateTime.now();
    }
    
    public CompilationResult(boolean success, int errorCount, int warningCount, long compilationTimeMs) {
        this();
        this.success = success;
        this.errorCount = errorCount;
        this.warningCount = warningCount;
        this.compilationTimeMs = compilationTimeMs;
    }
    
    // Static factory methods
    public static CompilationResult success(long compilationTimeMs) {
        return new CompilationResult(true, 0, 0, compilationTimeMs);
    }
    
    public static CompilationResult failure(List<CompilationError> errors, long compilationTimeMs) {
        CompilationResult result = new CompilationResult(false, errors.size(), 0, compilationTimeMs);
        result.setErrors(errors);
        return result;
    }
    
    // Helper methods
    public boolean hasErrors() {
        return errorCount > 0;
    }
    
    public boolean hasWarnings() {
        return warningCount > 0;
    }
    
    public String getStatusSummary() {
        if (success) {
            return String.format("✅ Compilation successful in %dms", compilationTimeMs);
        } else {
            return String.format("❌ Compilation failed with %d errors, %d warnings in %dms", 
                               errorCount, warningCount, compilationTimeMs);
        }
    }
    
    // Standard getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public int getErrorCount() { return errorCount; }
    public void setErrorCount(int errorCount) { this.errorCount = errorCount; }
    
    public int getWarningCount() { return warningCount; }
    public void setWarningCount(int warningCount) { this.warningCount = warningCount; }
    
    public List<CompilationError> getErrors() { return errors; }
    public void setErrors(List<CompilationError> errors) { this.errors = errors; }
    
    public List<CompilationWarning> getWarnings() { return warnings; }
    public void setWarnings(List<CompilationWarning> warnings) { this.warnings = warnings; }
    
    public long getCompilationTimeMs() { return compilationTimeMs; }
    public void setCompilationTimeMs(long compilationTimeMs) { this.compilationTimeMs = compilationTimeMs; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getJavaVersion() { return javaVersion; }
    public void setJavaVersion(String javaVersion) { this.javaVersion = javaVersion; }
    
    public String getMavenVersion() { return mavenVersion; }
    public void setMavenVersion(String mavenVersion) { this.mavenVersion = mavenVersion; }
    
    @Override
    public String toString() {
        return "CompilationResult{" +
                "success=" + success +
                ", errorCount=" + errorCount +
                ", warningCount=" + warningCount +
                ", compilationTimeMs=" + compilationTimeMs +
                ", timestamp=" + timestamp +
                '}';
    }
}