package com.learningportal.service;

/**
 * Compilation Error Data Class
 * 
 * Represents a specific compilation error with location and resolution information.
 */
public class CompilationError {
    
    private String fileName;
    private int lineNumber;
    private int columnNumber;
    private String errorMessage;
    private String errorCode;
    private ErrorType errorType;
    private String suggestedFix;
    
    public enum ErrorType {
        SYNTAX_ERROR("Syntax Error"),
        TYPE_ERROR("Type Error"),
        MISSING_IMPORT("Missing Import"),
        MISSING_DEPENDENCY("Missing Dependency"),
        ANNOTATION_PROCESSING("Annotation Processing"),
        GENERIC_ERROR("Generic Error");
        
        private final String displayName;
        
        ErrorType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public CompilationError() {}
    
    public CompilationError(String fileName, int lineNumber, String errorMessage, ErrorType errorType) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }
    
    // Helper methods
    public String getLocationString() {
        return String.format("%s:%d:%d", fileName, lineNumber, columnNumber);
    }
    
    public String getFormattedError() {
        return String.format("[%s] %s at %s", 
                           errorType.getDisplayName(), 
                           errorMessage, 
                           getLocationString());
    }
    
    public boolean hasSuggestedFix() {
        return suggestedFix != null && !suggestedFix.trim().isEmpty();
    }
    
    // Standard getters and setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
    
    public int getColumnNumber() { return columnNumber; }
    public void setColumnNumber(int columnNumber) { this.columnNumber = columnNumber; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    
    public ErrorType getErrorType() { return errorType; }
    public void setErrorType(ErrorType errorType) { this.errorType = errorType; }
    
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    
    @Override
    public String toString() {
        return "CompilationError{" +
                "fileName='" + fileName + '\'' +
                ", lineNumber=" + lineNumber +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorType=" + errorType +
                '}';
    }
}