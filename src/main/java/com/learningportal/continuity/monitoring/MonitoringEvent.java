package com.learningportal.continuity.monitoring;

import java.time.LocalDateTime;

/**
 * Monitoring event data class
 */
public class MonitoringEvent {
    private MonitoringEventType type;
    private String message;
    private LocalDateTime timestamp;
    private String component;
    
    // Getters and setters
    public MonitoringEventType getType() { return type; }
    public void setType(MonitoringEventType type) { this.type = type; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
}