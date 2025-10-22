package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Analysis result for a single context source.
 */
public class SourceAnalysis {
    
    private String sourceName;
    private String sourceType;
    private boolean available;
    private double reliabilityScore;
    private String dataQuality;
    private LocalDateTime analysisTime;
    private Map<String, String> extractedData;
    private String errorMessage;
    
    public SourceAnalysis() {}
    
    public SourceAnalysis(String sourceName, String sourceType) {
        this.sourceName = sourceName;
        this.sourceType = sourceType;
        this.analysisTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getSourceName() {
        return sourceName;
    }
    
    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }
    
    public String getSourceType() {
        return sourceType;
    }
    
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public double getReliabilityScore() {
        return reliabilityScore;
    }
    
    public void setReliabilityScore(double reliabilityScore) {
        this.reliabilityScore = reliabilityScore;
    }
    
    public String getDataQuality() {
        return dataQuality;
    }
    
    public void setDataQuality(String dataQuality) {
        this.dataQuality = dataQuality;
    }
    
    public LocalDateTime getAnalysisTime() {
        return analysisTime;
    }
    
    public void setAnalysisTime(LocalDateTime analysisTime) {
        this.analysisTime = analysisTime;
    }
    
    public Map<String, String> getExtractedData() {
        return extractedData;
    }
    
    public void setExtractedData(Map<String, String> extractedData) {
        this.extractedData = extractedData;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    // Additional methods for compatibility
    public double getReliability() {
        return this.reliabilityScore;
    }
    
    public void setReliability(double reliability) {
        this.reliabilityScore = reliability;
    }
    
    public LocalDateTime getLastModified() {
        return this.analysisTime;
    }
    
    public void setLastModified(LocalDateTime lastModified) {
        this.analysisTime = lastModified;
    }
    
    public String getSessionId() {
        return this.extractedData != null ? this.extractedData.get("sessionId") : null;
    }
    
    public void setSessionId(String sessionId) {
        if (this.extractedData == null) {
            this.extractedData = new java.util.HashMap<>();
        }
        this.extractedData.put("sessionId", sessionId);
    }
    
    public LocalDateTime getAnalysisTimestamp() {
        return this.analysisTime;
    }
    
    public void setAnalysisTimestamp(LocalDateTime analysisTimestamp) {
        this.analysisTime = analysisTimestamp;
    }
    
    public void setDataQuality(double dataQuality) {
        this.dataQuality = String.valueOf(dataQuality);
    }
    
    @Override
    public String toString() {
        return "SourceAnalysis{" +
                "sourceName='" + sourceName + '\'' +
                ", sourceType='" + sourceType + '\'' +
                ", available=" + available +
                ", reliabilityScore=" + String.format("%.1f", reliabilityScore) +
                ", dataQuality='" + dataQuality + '\'' +
                ", extractedData=" + (extractedData != null ? extractedData.size() : 0) + " items" +
                '}';
    }
}