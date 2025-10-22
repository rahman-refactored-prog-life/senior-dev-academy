package com.learningportal.continuity;

import java.time.LocalDateTime;

/**
 * Represents a known issue with resolution steps and tracking information.
 */
public class Issue {
    
    private String issueId;
    private String title;
    private String description;
    private IssueSeverity severity;
    private IssueStatus status;
    private String errorMessage;
    private String rootCause;
    private String resolutionSteps;
    private LocalDateTime discoveredTime;
    private LocalDateTime resolvedTime;
    private String assignedTo;
    
    public Issue() {}
    
    public Issue(String title, String description, IssueSeverity severity) {
        this.issueId = generateIssueId();
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = IssueStatus.OPEN;
        this.discoveredTime = LocalDateTime.now();
    }
    
    private String generateIssueId() {
        return "issue_" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getIssueId() {
        return issueId;
    }
    
    public void setIssueId(String issueId) {
        this.issueId = issueId;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public IssueSeverity getSeverity() {
        return severity;
    }
    
    public void setSeverity(IssueSeverity severity) {
        this.severity = severity;
    }
    
    public IssueStatus getStatus() {
        return status;
    }
    
    public void setStatus(IssueStatus status) {
        this.status = status;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getRootCause() {
        return rootCause;
    }
    
    public void setRootCause(String rootCause) {
        this.rootCause = rootCause;
    }
    
    public String getResolutionSteps() {
        return resolutionSteps;
    }
    
    public void setResolutionSteps(String resolutionSteps) {
        this.resolutionSteps = resolutionSteps;
    }
    
    public LocalDateTime getDiscoveredTime() {
        return discoveredTime;
    }
    
    public void setDiscoveredTime(LocalDateTime discoveredTime) {
        this.discoveredTime = discoveredTime;
    }
    
    public LocalDateTime getResolvedTime() {
        return resolvedTime;
    }
    
    public void setResolvedTime(LocalDateTime resolvedTime) {
        this.resolvedTime = resolvedTime;
    }
    
    public String getAssignedTo() {
        return assignedTo;
    }
    
    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }
    
    @Override
    public String toString() {
        return "Issue{" +
                "issueId='" + issueId + '\'' +
                ", title='" + title + '\'' +
                ", severity=" + severity +
                ", status=" + status +
                ", discoveredTime=" + discoveredTime +
                '}';
    }
}

enum IssueSeverity {
    CRITICAL,
    HIGH,
    MEDIUM,
    LOW,
    INFO
}

enum IssueStatus {
    OPEN,
    IN_PROGRESS,
    RESOLVED,
    CLOSED,
    DEFERRED
}