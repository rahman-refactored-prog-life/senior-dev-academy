package com.learningportal.continuity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Comprehensive session state model that captures complete project context
 * for bulletproof session continuity and zero context loss.
 */
public class SessionState {
    
    // Basic session information
    private String sessionId;
    private LocalDateTime timestamp;
    private long durationMinutes;
    
    // Progress tracking
    private String lastCompletedPhase;
    private double progressPercentage;
    private List<Task> completedTasks;
    private List<Task> inProgressTasks;
    
    // Technical environment
    private String javaVersion;
    private String mavenStatus;
    private CompilationStatus compilationStatus;
    private DatabaseStatus databaseStatus;
    private List<Issue> knownIssues;
    
    // File modifications
    private List<FileModification> filesModified;
    private int linesChanged;
    
    // Continuation planning
    private List<NextAction> nextActions;
    private List<String> dependencies;
    private int estimatedTimeMinutes;
    private List<String> successCriteria;
    
    // Constructors
    public SessionState() {
        this.timestamp = LocalDateTime.now();
        this.sessionId = generateSessionId();
    }
    
    public SessionState(String sessionId) {
        this.sessionId = sessionId;
        this.timestamp = LocalDateTime.now();
    }
    
    // Session ID generation
    private String generateSessionId() {
        return "session_" + System.currentTimeMillis();
    }
    
    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }
    
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public long getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public String getLastCompletedPhase() {
        return lastCompletedPhase;
    }
    
    public void setLastCompletedPhase(String lastCompletedPhase) {
        this.lastCompletedPhase = lastCompletedPhase;
    }
    
    public double getProgressPercentage() {
        return progressPercentage;
    }
    
    public void setProgressPercentage(double progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
    
    public List<Task> getCompletedTasks() {
        return completedTasks;
    }
    
    public void setCompletedTasks(List<Task> completedTasks) {
        this.completedTasks = completedTasks;
    }
    
    public List<Task> getInProgressTasks() {
        return inProgressTasks;
    }
    
    public void setInProgressTasks(List<Task> inProgressTasks) {
        this.inProgressTasks = inProgressTasks;
    }
    
    public String getJavaVersion() {
        return javaVersion;
    }
    
    public void setJavaVersion(String javaVersion) {
        this.javaVersion = javaVersion;
    }
    
    public String getMavenStatus() {
        return mavenStatus;
    }
    
    public void setMavenStatus(String mavenStatus) {
        this.mavenStatus = mavenStatus;
    }
    
    public CompilationStatus getCompilationStatus() {
        return compilationStatus;
    }
    
    public void setCompilationStatus(CompilationStatus compilationStatus) {
        this.compilationStatus = compilationStatus;
    }
    
    public DatabaseStatus getDatabaseStatus() {
        return databaseStatus;
    }
    
    public void setDatabaseStatus(DatabaseStatus databaseStatus) {
        this.databaseStatus = databaseStatus;
    }
    
    public List<Issue> getKnownIssues() {
        return knownIssues;
    }
    
    public void setKnownIssues(List<Issue> knownIssues) {
        this.knownIssues = knownIssues;
    }
    
    public List<FileModification> getFilesModified() {
        return filesModified;
    }
    
    public void setFilesModified(List<FileModification> filesModified) {
        this.filesModified = filesModified;
    }
    
    public int getLinesChanged() {
        return linesChanged;
    }
    
    public void setLinesChanged(int linesChanged) {
        this.linesChanged = linesChanged;
    }
    
    public List<NextAction> getNextActions() {
        return nextActions;
    }
    
    public void setNextActions(List<NextAction> nextActions) {
        this.nextActions = nextActions;
    }
    
    public List<String> getDependencies() {
        return dependencies;
    }
    
    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }
    
    public int getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }
    
    public void setEstimatedTimeMinutes(int estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }
    
    public List<String> getSuccessCriteria() {
        return successCriteria;
    }
    
    public void setSuccessCriteria(List<String> successCriteria) {
        this.successCriteria = successCriteria;
    }
    
    // Additional methods for compatibility
    public String getTimestampAsString() {
        return this.timestamp != null ? this.timestamp.toString() : null;
    }
    
    public void setTimestampFromString(String timestamp) {
        // Parse string timestamp if needed, for now just store as string representation
        if (timestamp != null && !timestamp.isEmpty()) {
            try {
                this.timestamp = LocalDateTime.parse(timestamp);
            } catch (Exception e) {
                // Keep current timestamp if parsing fails
            }
        }
    }
    
    public long getDuration() {
        return this.durationMinutes;
    }
    
    public void setDuration(long duration) {
        this.durationMinutes = duration;
    }
    
    @Override
    public String toString() {
        return "SessionState{" +
                "sessionId='" + sessionId + '\'' +
                ", timestamp=" + timestamp +
                ", lastCompletedPhase='" + lastCompletedPhase + '\'' +
                ", progressPercentage=" + progressPercentage +
                ", compilationStatus=" + compilationStatus +
                ", nextActions=" + (nextActions != null ? nextActions.size() : 0) + " actions" +
                '}';
    }
}