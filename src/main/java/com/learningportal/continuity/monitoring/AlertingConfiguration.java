package com.learningportal.continuity.monitoring;

/**
 * Alerting configuration data class
 */
public class AlertingConfiguration {
    private boolean enabled;
    private boolean slackEnabled;
    private boolean emailEnabled;
    private boolean webhookEnabled;
    private boolean pagerDutyEnabled;
    private boolean opsGenieEnabled;
    private int rateLimitMinutes;
    
    // Getters and setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    
    public boolean isSlackEnabled() { return slackEnabled; }
    public void setSlackEnabled(boolean slackEnabled) { this.slackEnabled = slackEnabled; }
    
    public boolean isEmailEnabled() { return emailEnabled; }
    public void setEmailEnabled(boolean emailEnabled) { this.emailEnabled = emailEnabled; }
    
    public boolean isWebhookEnabled() { return webhookEnabled; }
    public void setWebhookEnabled(boolean webhookEnabled) { this.webhookEnabled = webhookEnabled; }
    
    public boolean isPagerDutyEnabled() { return pagerDutyEnabled; }
    public void setPagerDutyEnabled(boolean pagerDutyEnabled) { this.pagerDutyEnabled = pagerDutyEnabled; }
    
    public boolean isOpsGenieEnabled() { return opsGenieEnabled; }
    public void setOpsGenieEnabled(boolean opsGenieEnabled) { this.opsGenieEnabled = opsGenieEnabled; }
    
    public int getRateLimitMinutes() { return rateLimitMinutes; }
    public void setRateLimitMinutes(int rateLimitMinutes) { this.rateLimitMinutes = rateLimitMinutes; }
}