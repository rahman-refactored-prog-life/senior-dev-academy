package com.learningportal.continuity.monitoring;

import java.time.LocalDateTime;

/**
 * Alert data class
 */
public class Alert {
    private AlertLevel level;
    private String title;
    private String message;
    private String component;
    private LocalDateTime timestamp;
    private boolean acknowledged = false;
    private String acknowledgedBy;
    private LocalDateTime acknowledgedAt;
    
    // Getters and setters
    public AlertLevel getLevel() { return level; }
    public void setLevel(AlertLevel level) { this.level = level; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public boolean isAcknowledged() { return acknowledged; }
    public void setAcknowledged(boolean acknowledged) { this.acknowledged = acknowledged; }
    
    public String getAcknowledgedBy() { return acknowledgedBy; }
    public void setAcknowledgedBy(String acknowledgedBy) { this.acknowledgedBy = acknowledgedBy; }
    
    public LocalDateTime getAcknowledgedAt() { return acknowledgedAt; }
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) { this.acknowledgedAt = acknowledgedAt; }
}