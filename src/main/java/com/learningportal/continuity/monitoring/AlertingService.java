package com.learningportal.continuity.monitoring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive alerting service for the Session Continuity System.
 * Supports multiple notification channels including email, Slack, webhooks, and PagerDuty.
 */
@Service
public class AlertingService {
    
    private static final Logger logger = LoggerFactory.getLogger(AlertingService.class);
    
    @Value("${session-continuity.alerting.enabled:true}")
    private boolean alertingEnabled;
    
    @Value("${session-continuity.alerting.slack-webhook:}")
    private String slackWebhookUrl;
    
    @Value("${session-continuity.alerting.email-enabled:false}")
    private boolean emailEnabled;
    
    @Value("${session-continuity.alerting.webhook-url:}")
    private String webhookUrl;
    
    @Value("${session-continuity.alerting.pagerduty-key:}")
    private String pagerDutyKey;
    
    @Value("${session-continuity.alerting.opsgenie-key:}")
    private String opsGenieKey;
    
    @Value("${session-continuity.alerting.rate-limit-minutes:5}")
    private int rateLimitMinutes;
    
    private final RestTemplate restTemplate = new RestTemplate();
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    
    // Alert rate limiting
    private final Map<String, LocalDateTime> lastAlertTimes = new ConcurrentHashMap<>();
    
    // Alert history
    private final List<Alert> alertHistory = new ArrayList<>();
    
    // Alert statistics
    private final Map<AlertLevel, Long> alertCounts = new ConcurrentHashMap<>();
    
    @PostConstruct
    public void initialize() {
        logger.info("Initializing Alerting Service");
        
        // Initialize alert counts
        for (AlertLevel level : AlertLevel.values()) {
            alertCounts.put(level, 0L);
        }
        
        // Schedule alert cleanup
        scheduleAlertCleanup();
        
        logger.info("Alerting Service initialized - Enabled: {}", alertingEnabled);
    }
    
    /**
     * Send alert through all configured channels
     */
    public void sendAlert(Alert alert) {
        if (!alertingEnabled) {
            logger.debug("Alerting disabled, skipping alert: {}", alert.getTitle());
            return;
        }
        
        // Check rate limiting
        if (isRateLimited(alert)) {
            logger.debug("Alert rate limited: {}", alert.getTitle());
            return;
        }
        
        // Record alert
        recordAlert(alert);
        
        // Send through all channels asynchronously
        executorService.submit(() -> sendThroughAllChannels(alert));
    }
    
    /**
     * Check if alert is rate limited
     */
    private boolean isRateLimited(Alert alert) {
        String alertKey = generateAlertKey(alert);
        LocalDateTime lastAlertTime = lastAlertTimes.get(alertKey);
        
        if (lastAlertTime != null) {
            LocalDateTime rateLimitThreshold = LocalDateTime.now().minusMinutes(rateLimitMinutes);
            if (lastAlertTime.isAfter(rateLimitThreshold)) {
                return true;
            }
        }
        
        lastAlertTimes.put(alertKey, LocalDateTime.now());
        return false;
    }
    
    /**
     * Generate unique key for alert rate limiting
     */
    private String generateAlertKey(Alert alert) {
        return alert.getComponent() + ":" + alert.getTitle() + ":" + alert.getLevel();
    }
    
    /**
     * Record alert in history and statistics
     */
    private void recordAlert(Alert alert) {
        // Add to history
        synchronized (alertHistory) {
            alertHistory.add(alert);
            
            // Keep only last 1000 alerts
            if (alertHistory.size() > 1000) {
                alertHistory.remove(0);
            }
        }
        
        // Update statistics
        alertCounts.merge(alert.getLevel(), 1L, Long::sum);
        
        logger.info("Alert recorded: {} - {} - {}", alert.getLevel(), alert.getTitle(), alert.getMessage());
    }
    
    /**
     * Send alert through all configured channels
     */
    private void sendThroughAllChannels(Alert alert) {
        try {
            // Send to Slack
            if (slackWebhookUrl != null && !slackWebhookUrl.isEmpty()) {
                sendSlackAlert(alert);
            }
            
            // Send to email
            if (emailEnabled) {
                sendEmailAlert(alert);
            }
            
            // Send to webhook
            if (webhookUrl != null && !webhookUrl.isEmpty()) {
                sendWebhookAlert(alert);
            }
            
            // Send to PagerDuty (for critical alerts)
            if (alert.getLevel() == AlertLevel.CRITICAL && pagerDutyKey != null && !pagerDutyKey.isEmpty()) {
                sendPagerDutyAlert(alert);
            }
            
            // Send to OpsGenie (for critical alerts)
            if (alert.getLevel() == AlertLevel.CRITICAL && opsGenieKey != null && !opsGenieKey.isEmpty()) {
                sendOpsGenieAlert(alert);
            }
            
        } catch (Exception e) {
            logger.error("Error sending alert through channels", e);
        }
    }
    
    /**
     * Send alert to Slack
     */
    private void sendSlackAlert(Alert alert) {
        try {
            SlackMessage slackMessage = createSlackMessage(alert);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<SlackMessage> request = new HttpEntity<>(slackMessage, headers);
            
            restTemplate.postForEntity(slackWebhookUrl, request, String.class);
            
            logger.debug("Slack alert sent successfully: {}", alert.getTitle());
            
        } catch (Exception e) {
            logger.error("Failed to send Slack alert", e);
        }
    }
    
    /**
     * Create Slack message from alert
     */
    private SlackMessage createSlackMessage(Alert alert) {
        SlackMessage message = new SlackMessage();
        
        // Set color based on alert level
        String color = getSlackColor(alert.getLevel());
        message.setColor(color);
        
        // Create attachment
        SlackAttachment attachment = new SlackAttachment();
        attachment.setTitle("Session Continuity Alert: " + alert.getTitle());
        attachment.setText(alert.getMessage());
        attachment.setColor(color);
        
        // Add fields
        List<SlackField> fields = new ArrayList<>();
        fields.add(new SlackField("Level", alert.getLevel().toString(), true));
        fields.add(new SlackField("Component", alert.getComponent(), true));
        fields.add(new SlackField("Time", alert.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), true));
        
        attachment.setFields(fields);
        message.setAttachments(Collections.singletonList(attachment));
        
        return message;
    }
    
    /**
     * Get Slack color for alert level
     */
    private String getSlackColor(AlertLevel level) {
        switch (level) {
            case CRITICAL:
                return "danger";
            case WARNING:
                return "warning";
            case INFO:
                return "good";
            default:
                return "#439FE0";
        }
    }
    
    /**
     * Send alert via email
     */
    private void sendEmailAlert(Alert alert) {
        try {
            // Email implementation would go here
            // This is a placeholder for actual email service integration
            logger.info("Email alert would be sent: {} - {}", alert.getTitle(), alert.getMessage());
            
        } catch (Exception e) {
            logger.error("Failed to send email alert", e);
        }
    }
    
    /**
     * Send alert to webhook
     */
    private void sendWebhookAlert(Alert alert) {
        try {
            WebhookPayload payload = createWebhookPayload(alert);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<WebhookPayload> request = new HttpEntity<>(payload, headers);
            
            restTemplate.postForEntity(webhookUrl, request, String.class);
            
            logger.debug("Webhook alert sent successfully: {}", alert.getTitle());
            
        } catch (Exception e) {
            logger.error("Failed to send webhook alert", e);
        }
    }
    
    /**
     * Create webhook payload from alert
     */
    private WebhookPayload createWebhookPayload(Alert alert) {
        WebhookPayload payload = new WebhookPayload();
        payload.setAlertId(UUID.randomUUID().toString());
        payload.setTitle(alert.getTitle());
        payload.setMessage(alert.getMessage());
        payload.setLevel(alert.getLevel().toString());
        payload.setComponent(alert.getComponent());
        payload.setTimestamp(alert.getTimestamp().toString());
        payload.setSource("session-continuity-system");
        
        return payload;
    }
    
    /**
     * Send alert to PagerDuty
     */
    private void sendPagerDutyAlert(Alert alert) {
        try {
            PagerDutyEvent event = createPagerDutyEvent(alert);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<PagerDutyEvent> request = new HttpEntity<>(event, headers);
            
            String pagerDutyUrl = "https://events.pagerduty.com/v2/enqueue";
            restTemplate.postForEntity(pagerDutyUrl, request, String.class);
            
            logger.debug("PagerDuty alert sent successfully: {}", alert.getTitle());
            
        } catch (Exception e) {
            logger.error("Failed to send PagerDuty alert", e);
        }
    }
    
    /**
     * Create PagerDuty event from alert
     */
    private PagerDutyEvent createPagerDutyEvent(Alert alert) {
        PagerDutyEvent event = new PagerDutyEvent();
        event.setRoutingKey(pagerDutyKey);
        event.setEventAction("trigger");
        
        PagerDutyPayload payload = new PagerDutyPayload();
        payload.setSummary("Session Continuity Alert: " + alert.getTitle());
        payload.setSource("session-continuity-system");
        payload.setSeverity(getPagerDutySeverity(alert.getLevel()));
        payload.setComponent(alert.getComponent());
        
        Map<String, Object> customDetails = new HashMap<>();
        customDetails.put("message", alert.getMessage());
        customDetails.put("timestamp", alert.getTimestamp().toString());
        customDetails.put("level", alert.getLevel().toString());
        payload.setCustomDetails(customDetails);
        
        event.setPayload(payload);
        
        return event;
    }
    
    /**
     * Get PagerDuty severity for alert level
     */
    private String getPagerDutySeverity(AlertLevel level) {
        switch (level) {
            case CRITICAL:
                return "critical";
            case WARNING:
                return "warning";
            case INFO:
                return "info";
            default:
                return "info";
        }
    }
    
    /**
     * Send alert to OpsGenie
     */
    private void sendOpsGenieAlert(Alert alert) {
        try {
            OpsGenieAlert opsGenieAlert = createOpsGenieAlert(alert);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "GenieKey " + opsGenieKey);
            
            HttpEntity<OpsGenieAlert> request = new HttpEntity<>(opsGenieAlert, headers);
            
            String opsGenieUrl = "https://api.opsgenie.com/v2/alerts";
            restTemplate.postForEntity(opsGenieUrl, request, String.class);
            
            logger.debug("OpsGenie alert sent successfully: {}", alert.getTitle());
            
        } catch (Exception e) {
            logger.error("Failed to send OpsGenie alert", e);
        }
    }
    
    /**
     * Create OpsGenie alert from alert
     */
    private OpsGenieAlert createOpsGenieAlert(Alert alert) {
        OpsGenieAlert opsGenieAlert = new OpsGenieAlert();
        opsGenieAlert.setMessage("Session Continuity Alert: " + alert.getTitle());
        opsGenieAlert.setDescription(alert.getMessage());
        opsGenieAlert.setPriority(getOpsgeniePriority(alert.getLevel()));
        opsGenieAlert.setSource("session-continuity-system");
        
        Map<String, String> details = new HashMap<>();
        details.put("component", alert.getComponent());
        details.put("level", alert.getLevel().toString());
        details.put("timestamp", alert.getTimestamp().toString());
        opsGenieAlert.setDetails(details);
        
        List<String> tags = Arrays.asList("session-continuity", alert.getComponent(), alert.getLevel().toString().toLowerCase());
        opsGenieAlert.setTags(tags);
        
        return opsGenieAlert;
    }
    
    /**
     * Get OpsGenie priority for alert level
     */
    private String getOpsgeniePriority(AlertLevel level) {
        switch (level) {
            case CRITICAL:
                return "P1";
            case WARNING:
                return "P3";
            case INFO:
                return "P5";
            default:
                return "P5";
        }
    }
    
    /**
     * Schedule alert cleanup to remove old alerts
     */
    private void scheduleAlertCleanup() {
        executorService.scheduleAtFixedRate(() -> {
            try {
                cleanupOldAlerts();
            } catch (Exception e) {
                logger.error("Error during alert cleanup", e);
            }
        }, 1, 24, TimeUnit.HOURS); // Run daily
    }
    
    /**
     * Clean up old alerts and rate limit entries
     */
    private void cleanupOldAlerts() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(7);
        
        // Clean up rate limit entries
        lastAlertTimes.entrySet().removeIf(entry -> entry.getValue().isBefore(cutoffTime));
        
        // Clean up old alerts from history
        synchronized (alertHistory) {
            alertHistory.removeIf(alert -> alert.getTimestamp().isBefore(cutoffTime));
        }
        
        logger.debug("Alert cleanup completed");
    }
    
    /**
     * Get alert statistics
     */
    public AlertStatistics getAlertStatistics() {
        AlertStatistics stats = new AlertStatistics();
        stats.setAlertCounts(new HashMap<>(alertCounts));
        stats.setTotalAlerts(alertCounts.values().stream().mapToLong(Long::longValue).sum());
        
        // Calculate recent alert counts (last 24 hours)
        LocalDateTime last24Hours = LocalDateTime.now().minusHours(24);
        Map<AlertLevel, Long> recentCounts = new HashMap<>();
        
        synchronized (alertHistory) {
            for (Alert alert : alertHistory) {
                if (alert.getTimestamp().isAfter(last24Hours)) {
                    recentCounts.merge(alert.getLevel(), 1L, Long::sum);
                }
            }
        }
        
        stats.setRecentAlertCounts(recentCounts);
        stats.setRecentTotalAlerts(recentCounts.values().stream().mapToLong(Long::longValue).sum());
        
        return stats;
    }
    
    /**
     * Get recent alerts
     */
    public List<Alert> getRecentAlerts(int limit) {
        synchronized (alertHistory) {
            int size = alertHistory.size();
            int fromIndex = Math.max(0, size - limit);
            return new ArrayList<>(alertHistory.subList(fromIndex, size));
        }
    }
    
    /**
     * Test alert functionality
     */
    public void sendTestAlert() {
        Alert testAlert = new Alert();
        testAlert.setLevel(AlertLevel.INFO);
        testAlert.setTitle("Test Alert");
        testAlert.setMessage("This is a test alert from the Session Continuity System");
        testAlert.setComponent("session-continuity");
        testAlert.setTimestamp(LocalDateTime.now());
        
        sendAlert(testAlert);
        
        logger.info("Test alert sent");
    }
    
    /**
     * Check if alerting is enabled
     */
    public boolean isAlertingEnabled() {
        return alertingEnabled;
    }
    
    /**
     * Get alerting configuration
     */
    public AlertingConfiguration getConfiguration() {
        AlertingConfiguration config = new AlertingConfiguration();
        config.setEnabled(alertingEnabled);
        config.setSlackEnabled(slackWebhookUrl != null && !slackWebhookUrl.isEmpty());
        config.setEmailEnabled(emailEnabled);
        config.setWebhookEnabled(webhookUrl != null && !webhookUrl.isEmpty());
        config.setPagerDutyEnabled(pagerDutyKey != null && !pagerDutyKey.isEmpty());
        config.setOpsGenieEnabled(opsGenieKey != null && !opsGenieKey.isEmpty());
        config.setRateLimitMinutes(rateLimitMinutes);
        
        return config;
    }
}