package com.learningportal.service;

/**
 * Entity Relationship Issue Data Class
 * 
 * Represents issues found in entity relationships and foreign key constraints.
 */
public class EntityRelationshipIssue {
    
    private String parentEntity;
    private String childEntity;
    private String relationshipType;
    private RelationshipIssueType issueType;
    private String description;
    private String foreignKeyColumn;
    private Object parentId;
    private Object childId;
    private String suggestedFix;
    
    public enum RelationshipIssueType {
        ORPHANED_CHILD("Orphaned Child Record"),
        MISSING_PARENT("Missing Parent Record"),
        INVALID_FOREIGN_KEY("Invalid Foreign Key"),
        CIRCULAR_REFERENCE("Circular Reference"),
        CONSTRAINT_VIOLATION("Constraint Violation"),
        MAPPING_ERROR("Mapping Error");
        
        private final String displayName;
        
        RelationshipIssueType(String displayName) {
            this.displayName = displayName;
        }
        
        public String getDisplayName() {
            return displayName;
        }
    }
    
    // Constructors
    public EntityRelationshipIssue() {}
    
    public EntityRelationshipIssue(String parentEntity, String childEntity, 
                                 RelationshipIssueType issueType, String description) {
        this.parentEntity = parentEntity;
        this.childEntity = childEntity;
        this.issueType = issueType;
        this.description = description;
    }
    
    // Helper methods
    public String getRelationshipDescription() {
        return String.format("%s -> %s (%s)", parentEntity, childEntity, relationshipType);
    }
    
    public String getFormattedIssue() {
        return String.format("[%s] %s in relationship %s: %s",
                           issueType.getDisplayName(),
                           getRelationshipDescription(),
                           foreignKeyColumn != null ? "via " + foreignKeyColumn : "",
                           description);
    }
    
    public boolean hasIds() {
        return parentId != null || childId != null;
    }
    
    public String getIdInfo() {
        if (parentId != null && childId != null) {
            return String.format("Parent ID: %s, Child ID: %s", parentId, childId);
        } else if (parentId != null) {
            return String.format("Parent ID: %s", parentId);
        } else if (childId != null) {
            return String.format("Child ID: %s", childId);
        }
        return "No ID information";
    }
    
    // Standard getters and setters
    public String getParentEntity() { return parentEntity; }
    public void setParentEntity(String parentEntity) { this.parentEntity = parentEntity; }
    
    public String getChildEntity() { return childEntity; }
    public void setChildEntity(String childEntity) { this.childEntity = childEntity; }
    
    public String getRelationshipType() { return relationshipType; }
    public void setRelationshipType(String relationshipType) { this.relationshipType = relationshipType; }
    
    public RelationshipIssueType getIssueType() { return issueType; }
    public void setIssueType(RelationshipIssueType issueType) { this.issueType = issueType; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getForeignKeyColumn() { return foreignKeyColumn; }
    public void setForeignKeyColumn(String foreignKeyColumn) { this.foreignKeyColumn = foreignKeyColumn; }
    
    public Object getParentId() { return parentId; }
    public void setParentId(Object parentId) { this.parentId = parentId; }
    
    public Object getChildId() { return childId; }
    public void setChildId(Object childId) { this.childId = childId; }
    
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    
    @Override
    public String toString() {
        return "EntityRelationshipIssue{" +
                "parentEntity='" + parentEntity + '\'' +
                ", childEntity='" + childEntity + '\'' +
                ", issueType=" + issueType +
                ", description='" + description + '\'' +
                '}';
    }
}