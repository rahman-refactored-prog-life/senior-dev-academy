package com.learningportal.service;

/**
 * Compilation Warning Data Class
 * 
 * Represents a compilation warning with location and description information.
 */
public class CompilationWarning {
    
    private String fileName;
    private int lineNumber;
    private int columnNumber;
    private String warningMessage;
    private String warningCode;
    private WarningType warningType;
    
    public enum WarningType {
        DEPRECATION("Deprecation Warning"),
        UNCHECKED("Unchecked Warning"),
        UNUSED("Unused Warning"),
        RAW_TYPES("Raw Types Warning"),
        GENERIC("Generic Warning");
        
        private final String displayName;
        
        WarningType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public CompilationWarning() {}
    
    public CompilationWarning(String fileName, int lineNumber, String warningMessage, WarningType warningType) {
        this.fileName = fileName;
        this.lineNumber = lineNumber;
        this.warningMessage = warningMessage;
        this.warningType = warningType;
    }
    
    // Helper methods
    public String getLocationString() {
        return String.format("%s:%d:%d", fileName, lineNumber, columnNumber);
    }
    
    public String getFormattedWarning() {
        return String.format("[%s] %s at %s", 
                           warningType.getDisplayName(), 
                           warningMessage, 
                           getLocationString());
    }
    
    // Standard getters and setters
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public int getLineNumber() { return lineNumber; }
    public void setLineNumber(int lineNumber) { this.lineNumber = lineNumber; }
    
    public int getColumnNumber() { return columnNumber; }
    public void setColumnNumber(int columnNumber) { this.columnNumber = columnNumber; }
    
    public String getWarningMessage() { return warningMessage; }
    public void setWarningMessage(String warningMessage) { this.warningMessage = warningMessage; }
    
    public String getWarningCode() { return warningCode; }
    public void setWarningCode(String warningCode) { this.warningCode = warningCode; }
    
    public WarningType getWarningType() { return warningType; }
    public void setWarningType(WarningType warningType) { this.warningType = warningType; }
    
    @Override
    public String toString() {
        return "CompilationWarning{" +
                "fileName='" + fileName + '\'' +
                ", lineNumber=" + lineNumber +
                ", warningMessage='" + warningMessage + '\'' +
                ", warningType=" + warningType +
                '}';
    }
}