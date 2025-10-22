package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents the compilation status of the project with detailed error information.
 */
public class CompilationStatus {
    
    private boolean successful;
    private LocalDateTime lastCompilationTime;
    private int errorCount;
    private int warningCount;
    private List<CompilationError> errors;
    private List<CompilationWarning> warnings;
    private String mavenOutput;
    
    public CompilationStatus() {}
    
    public CompilationStatus(boolean successful) {
        this.successful = successful;
        this.lastCompilationTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public boolean isSuccessful() {
        return successful;
    }
    
    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
    
    public LocalDateTime getLastCompilationTime() {
        return lastCompilationTime;
    }
    
    public void setLastCompilationTime(LocalDateTime lastCompilationTime) {
        this.lastCompilationTime = lastCompilationTime;
    }
    
    public int getErrorCount() {
        return errorCount;
    }
    
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    
    public int getWarningCount() {
        return warningCount;
    }
    
    public void setWarningCount(int warningCount) {
        this.warningCount = warningCount;
    }
    
    public List<CompilationError> getErrors() {
        return errors;
    }
    
    public void setErrors(List<CompilationError> errors) {
        this.errors = errors;
    }
    
    public List<CompilationWarning> getWarnings() {
        return warnings;
    }
    
    public void setWarnings(List<CompilationWarning> warnings) {
        this.warnings = warnings;
    }
    
    public String getMavenOutput() {
        return mavenOutput;
    }
    
    public void setMavenOutput(String mavenOutput) {
        this.mavenOutput = mavenOutput;
    }
    
    // Additional methods for compatibility
    public boolean isSuccess() {
        return this.successful;
    }
    
    public void setSuccess(boolean success) {
        this.successful = success;
    }
    
    @Override
    public String toString() {
        return "CompilationStatus{" +
                "successful=" + successful +
                ", errorCount=" + errorCount +
                ", warningCount=" + warningCount +
                ", lastCompilationTime=" + lastCompilationTime +
                '}';
    }
}

class CompilationError {
    private String fileName;
    private int lineNumber;
    private String errorMessage;
    private String errorCode;
    
    public CompilationError() {}
    
    public CompilationError(String fileName, int lineNumber, String errorMessage) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.errorMessage = errorMessage;
    }
    
    // Getters and Setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public int getLineNumber() {
        return lineNumber;
    }
    
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}

class CompilationWarning {
    private String fileName;
    private int lineNumber;
    private String warningMessage;
    private String warningCode;
    
    public CompilationWarning() {}
    
    public CompilationWarning(String fileName, int lineNumber, String warningMessage) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.warningMessage = warningMessage;
    }
    
    // Getters and Setters
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public int getLineNumber() {
        return lineNumber;
    }
    
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
    
    public String getWarningMessage() {
        return warningMessage;
    }
    
    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
    
    public String getWarningCode() {
        return warningCode;
    }
    
    public void setWarningCode(String warningCode) {
        this.warningCode = warningCode;
    }
}