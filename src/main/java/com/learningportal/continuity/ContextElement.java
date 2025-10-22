package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Individual context element with source and confidence information.
 */
public class ContextElement {
    
    private String elementType;
    private String value;
    private String sourceName;
    private double confidence;
    private LocalDateTime extractionTime;
    private String description;
    
    public ContextElement() {}
    
    public ContextElement(String elementType, String value, String sourceName, double confidence) {
        this.elementType = elementType;
        this.value = value;
        this.sourceName = sourceName;
        this.confidence = confidence;
        this.extractionTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getElementType() {
        return elementType;
    }
    
    public void setElementType(String elementType) {
        this.elementType = elementType;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getSourceName() {
        return sourceName;
    }
    
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
    
    public double getConfidence() {
        return confidence;
    }
    
    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
    
    public LocalDateTime getExtractionTime() {
        return extractionTime;
    }
    
    public void setExtractionTime(LocalDateTime extractionTime) {
        this.extractionTime = extractionTime;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    // Additional methods for compatibility
    public String getType() {
        return this.elementType;
    }
    
    public void setType(String type) {
        this.elementType = type;
    }
    
    public String getSource() {
        return this.sourceName;
    }
    
    public void setSource(String source) {
        this.sourceName = source;
    }
    
    public LocalDateTime getTimestamp() {
        return this.extractionTime;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.extractionTime = timestamp;
    }
    
    @Override
    public String toString() {
        return "ContextElement{" +
                "elementType='" + elementType + '\'' +
                ", value='" + value + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", confidence=" + String.format("%.1f", confidence) + "%" +
                ", extractionTime=" + extractionTime +
                '}';
    }
}