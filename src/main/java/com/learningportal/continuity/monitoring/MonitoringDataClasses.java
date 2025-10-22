package com.learningportal.continuity.monitoring;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Data classes for the Session Continuity Monitoring System
 */

// Note: Enums moved to separate files for public access

// Core monitoring classes
class PerformanceMetric {
    private String name;
    private String unit;
    private double currentValue;
    private double threshold;
    private double averageValue;
    private double minValue = Double.MAX_VALUE;
    private double maxValue = Double.MIN_VALUE;
    private int sampleCount = 0;
    
    public PerformanceMetric(String name, String unit, double threshold) {
        this.name = name;
        this.unit = unit;
        this.threshold = threshold;
    }
    
    public void updateValue(double value) {
        this.currentValue = value;
        this.sampleCount++;
        
        // Update min/max
        this.minValue = Math.min(this.minValue, value);
        this.maxValue = Math.max(this.maxValue, value);
        
        // Update average (simple moving average)
        this.averageValue = ((this.averageValue * (sampleCount - 1)) + value) / sampleCount;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public double getCurrentValue() { return currentValue; }
    public void setCurrentValue(double currentValue) { this.currentValue = currentValue; }
    
    public double getThreshold() { return threshold; }
    public void setThreshold(double threshold) { this.threshold = threshold; }
    
    public double getAverageValue() { return averageValue; }
    public void setAverageValue(double averageValue) { this.averageValue = averageValue; }
    
    public double getMinValue() { return minValue; }
    public void setMinValue(double minValue) { this.minValue = minValue; }
    
    public double getMaxValue() { return maxValue; }
    public void setMaxValue(double maxValue) { this.maxValue = maxValue; }
    
    public int getSampleCount() { return sampleCount; }
    public void setSampleCount(int sampleCount) { this.sampleCount = sampleCount; }
}

// Note: Core classes moved to separate files for public access

// Note: Health report and dashboard classes moved to separate files

// Configuration classes
class MonitoringConfiguration {
    private int degradationAlertThresholdMinutes = 30;
    private double minimumSuccessRate = 0.8;
    private int failurePatternThreshold = 5;
    
    // Getters and setters
    public int getDegradationAlertThresholdMinutes() { return degradationAlertThresholdMinutes; }
    public void setDegradationAlertThresholdMinutes(int degradationAlertThresholdMinutes) { this.degradationAlertThresholdMinutes = degradationAlertThresholdMinutes; }
    
    public double getMinimumSuccessRate() { return minimumSuccessRate; }
    public void setMinimumSuccessRate(double minimumSuccessRate) { this.minimumSuccessRate = minimumSuccessRate; }
    
    public int getFailurePatternThreshold() { return failurePatternThreshold; }
    public void setFailurePatternThreshold(int failurePatternThreshold) { this.failurePatternThreshold = failurePatternThreshold; }
}

// Note: Configuration and statistics classes moved to separate files

// Slack integration classes
class SlackMessage {
    private String text;
    private String color;
    private List<SlackAttachment> attachments;
    
    // Getters and setters
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public List<SlackAttachment> getAttachments() { return attachments; }
    public void setAttachments(List<SlackAttachment> attachments) { this.attachments = attachments; }
}

class SlackAttachment {
    private String title;
    private String text;
    private String color;
    private List<SlackField> fields;
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public List<SlackField> getFields() { return fields; }
    public void setFields(List<SlackField> fields) { this.fields = fields; }
}

class SlackField {
    private String title;
    private String value;
    private boolean shortField;
    
    public SlackField(String title, String value, boolean shortField) {
        this.title = title;
        this.value = value;
        this.shortField = shortField;
    }
    
    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    
    public boolean isShortField() { return shortField; }
    public void setShortField(boolean shortField) { this.shortField = shortField; }
}

// Webhook integration classes
class WebhookPayload {
    private String alertId;
    private String title;
    private String message;
    private String level;
    private String component;
    private String timestamp;
    private String source;
    
    // Getters and setters
    public String getAlertId() { return alertId; }
    public void setAlertId(String alertId) { this.alertId = alertId; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
}

// PagerDuty integration classes
class PagerDutyEvent {
    private String routingKey;
    private String eventAction;
    private PagerDutyPayload payload;
    
    // Getters and setters
    public String getRoutingKey() { return routingKey; }
    public void setRoutingKey(String routingKey) { this.routingKey = routingKey; }
    
    public String getEventAction() { return eventAction; }
    public void setEventAction(String eventAction) { this.eventAction = eventAction; }
    
    public PagerDutyPayload getPayload() { return payload; }
    public void setPayload(PagerDutyPayload payload) { this.payload = payload; }
}

class PagerDutyPayload {
    private String summary;
    private String source;
    private String severity;
    private String component;
    private Map<String, Object> customDetails;
    
    // Getters and setters
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    public String getComponent() { return component; }
    public void setComponent(String component) { this.component = component; }
    
    public Map<String, Object> getCustomDetails() { return customDetails; }
    public void setCustomDetails(Map<String, Object> customDetails) { this.customDetails = customDetails; }
}

// OpsGenie integration classes
class OpsGenieAlert {
    private String message;
    private String description;
    private String priority;
    private String source;
    private Map<String, String> details;
    private List<String> tags;
    
    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public Map<String, String> getDetails() { return details; }
    public void setDetails(Map<String, String> details) { this.details = details; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}