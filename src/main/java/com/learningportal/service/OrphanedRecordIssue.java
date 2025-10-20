package com.learningportal.service;

/**
 * Orphaned Record Issue Data Class
 * 
 * Represents records that exist without valid parent references.
 */
public class OrphanedRecordIssue {
    
    private String tableName;
    private String foreignKeyColumn;
    private Object recordId;
    private Object orphanedForeignKeyValue;
    private String parentTableName;
    private String description;
    private OrphanedRecordAction suggestedAction;
    
    public enum OrphanedRecordAction {
        DELETE_RECORD("Delete orphaned record"),
        CREATE_PARENT("Create missing parent record"),
        UPDATE_REFERENCE("Update foreign key reference"),
        IGNORE("Ignore (manual review required)");
        
        private final String displayName;
        
        OrphanedRecordAction(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public OrphanedRecordIssue() {}
    
    public OrphanedRecordIssue(String tableName, String foreignKeyColumn, 
                             Object recordId, Object orphanedForeignKeyValue) {
        this.tableName = tableName;
        this.foreignKeyColumn = foreignKeyColumn;
        this.recordId = recordId;
        this.orphanedForeignKeyValue = orphanedForeignKeyValue;
    }
    
    // Helper methods
    public String getFormattedIssue() {
        return String.format("Orphaned record in %s (ID: %s) - %s references non-existent %s (ID: %s)",
                           tableName, recordId, foreignKeyColumn, 
                           parentTableName != null ? parentTableName : "parent", 
                           orphanedForeignKeyValue);
    }
    
    public String getLocationInfo() {
        return String.format("%s.%s", tableName, foreignKeyColumn);
    }
    
    public boolean hasParentTable() {
        return parentTableName != null && !parentTableName.trim().isEmpty();
    }
    
    public boolean hasSuggestedAction() {
        return suggestedAction != null;
    }
    
    public String getActionDescription() {
        return hasSuggestedAction() ? suggestedAction.getDisplayName() : "No action suggested";
    }
    
    // Standard getters and setters
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    
    public String getForeignKeyColumn() { return foreignKeyColumn; }
    public void setForeignKeyColumn(String foreignKeyColumn) { this.foreignKeyColumn = foreignKeyColumn; }
    
    public Object getRecordId() { return recordId; }
    public void setRecordId(Object recordId) { this.recordId = recordId; }
    
    public Object getOrphanedForeignKeyValue() { return orphanedForeignKeyValue; }
    public void setOrphanedForeignKeyValue(Object orphanedForeignKeyValue) { 
        this.orphanedForeignKeyValue = orphanedForeignKeyValue; 
    }
    
    public String getParentTableName() { return parentTableName; }
    public void setParentTableName(String parentTableName) { this.parentTableName = parentTableName; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public OrphanedRecordAction getSuggestedAction() { return suggestedAction; }
    public void setSuggestedAction(OrphanedRecordAction suggestedAction) { this.suggestedAction = suggestedAction; }
    
    @Override
    public String toString() {
        return "OrphanedRecordIssue{" +
                "tableName='" + tableName + '\'' +
                ", foreignKeyColumn='" + foreignKeyColumn + '\'' +
                ", recordId=" + recordId +
                ", orphanedForeignKeyValue=" + orphanedForeignKeyValue +
                '}';
    }
}