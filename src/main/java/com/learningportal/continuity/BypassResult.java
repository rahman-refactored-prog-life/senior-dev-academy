package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Result of an emergency bypass procedure for quality gates.
 */
public class BypassResult {
    
    private String gateName;
    private String justification;
    private String remediationPlan;
    private boolean approved;
    private String reason;
    private LocalDateTime bypassTime;
    private List<String> conditions;
    private String approvedBy;
    
    public BypassResult() {}
    
    public BypassResult(String gateName, boolean approved) {
        this.gateName = gateName;
        this.approved = approved;
        this.bypassTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getGateName() {
        return gateName;
    }
    
    public void setGateName(String gateName) {
        this.gateName = gateName;
    }
    
    public String getJustification() {
        return justification;
    }
    
    public void setJustification(String justification) {
        this.justification = justification;
    }
    
    public String getRemediationPlan() {
        return remediationPlan;
    }
    
    public void setRemediationPlan(String remediationPlan) {
        this.remediationPlan = remediationPlan;
    }
    
    public boolean isApproved() {
        return approved;
    }
    
    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public LocalDateTime getBypassTime() {
        return bypassTime;
    }
    
    public void setBypassTime(LocalDateTime bypassTime) {
        this.bypassTime = bypassTime;
    }
    
    public List<String> getConditions() {
        return conditions;
    }
    
    public void setConditions(List<String> conditions) {
        this.conditions = conditions;
    }
    
    public String getApprovedBy() {
        return approvedBy;
    }
    
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }
    
    @Override
    public String toString() {
        return "BypassResult{" +
                "gateName='" + gateName + '\'' +
                ", approved=" + approved +
                ", reason='" + reason + '\'' +
                ", bypassTime=" + bypassTime +
                ", conditions=" + (conditions != null ? conditions.size() : 0) +
                '}';
    }
}