package com.learningportal.service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Self Healing Result Data Class
 * 
 * Contains results of automated self-healing attempts.
 */
public class SelfHealingResult {
    
    private boolean healingSuccessful;
    private LocalDateTime healingTimestamp;
    private List<String> actionsPerformed;
    private List<String> issuesResolved;
    private List<String> remainingIssues;
    private String healingSummary;
    private long healingTimeMs;
    
    public SelfHealingResult() {
        this.healingTimestamp = LocalDateTime.now();
    }
    
    public boolean hasRemainingIssues() { 
        return remainingIssues != null && !remainingIssues.isEmpty(); 
    }
    
    public int getResolvedIssueCount() { 
        return issuesResolved != null ? issuesResolved.size() : 0; 
    }
    
    public int getRemainingIssueCount() { 
        return remainingIssues != null ? remainingIssues.size() : 0; 
    }
    
    public String getHealingSummary() {
        if (healingSuccessful) {
            return String.format("✅ Self-healing successful - %d issues resolved in %dms", 
                               getResolvedIssueCount(), healingTimeMs);
        } else {
            return String.format("⚠️ Self-healing partial - %d resolved, %d remaining", 
                               getResolvedIssueCount(), getRemainingIssueCount());
        }
    }
    
    // Getters and setters
    public boolean isHealingSuccessful() { return healingSuccessful; }
    public void setHealingSuccessful(boolean healingSuccessful) { this.healingSuccessful = healingSuccessful; }
    public LocalDateTime getHealingTimestamp() { return healingTimestamp; }
    public void setHealingTimestamp(LocalDateTime healingTimestamp) { this.healingTimestamp = healingTimestamp; }
    public List<String> getActionsPerformed() { return actionsPerformed; }
    public void setActionsPerformed(List<String> actionsPerformed) { this.actionsPerformed = actionsPerformed; }
    public List<String> getIssuesResolved() { return issuesResolved; }
    public void setIssuesResolved(List<String> issuesResolved) { this.issuesResolved = issuesResolved; }
    public List<String> getRemainingIssues() { return remainingIssues; }
    public void setRemainingIssues(List<String> remainingIssues) { this.remainingIssues = remainingIssues; }
    public void setHealingSummary(String healingSummary) { this.healingSummary = healingSummary; }
    public long getHealingTimeMs() { return healingTimeMs; }
    public void setHealingTimeMs(long healingTimeMs) { this.healingTimeMs = healingTimeMs; }
}