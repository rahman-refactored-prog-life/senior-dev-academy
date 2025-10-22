package com.learningportal.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Results from database validation
 */
public class DatabaseValidationResults {
    
    private boolean connectionSuccess;
    private boolean schemaValid;
    private boolean relationshipsValid;
    private boolean dataIntegrityValid;
    private boolean overallSuccess;
    
    private String databaseUrl;
    private String databaseProduct;
    private String databaseVersion;
    
    private List<SchemaIssue> schemaIssues = new ArrayList<>();
    private List<EntityRelationshipIssue> relationshipIssues = new ArrayList<>();
    private List<String> dataIntegrityIssues = new ArrayList<>();
    private List<String> errors = new ArrayList<>();
    
    public void addError(String error) {
        errors.add(error);
    }
    
    // Getters and setters
    public boolean isConnectionSuccess() { return connectionSuccess; }
    public void setConnectionSuccess(boolean connectionSuccess) { this.connectionSuccess = connectionSuccess; }
    
    public boolean isSchemaValid() { return schemaValid; }
    public void setSchemaValid(boolean schemaValid) { this.schemaValid = schemaValid; }
    
    public boolean isRelationshipsValid() { return relationshipsValid; }
    public void setRelationshipsValid(boolean relationshipsValid) { this.relationshipsValid = relationshipsValid; }
    
    public boolean isDataIntegrityValid() { return dataIntegrityValid; }
    public void setDataIntegrityValid(boolean dataIntegrityValid) { this.dataIntegrityValid = dataIntegrityValid; }
    
    public boolean isOverallSuccess() { return overallSuccess; }
    public void setOverallSuccess(boolean overallSuccess) { this.overallSuccess = overallSuccess; }
    
    public String getDatabaseUrl() { return databaseUrl; }
    public void setDatabaseUrl(String databaseUrl) { this.databaseUrl = databaseUrl; }
    
    public String getDatabaseProduct() { return databaseProduct; }
    public void setDatabaseProduct(String databaseProduct) { this.databaseProduct = databaseProduct; }
    
    public String getDatabaseVersion() { return databaseVersion; }
    public void setDatabaseVersion(String databaseVersion) { this.databaseVersion = databaseVersion; }
    
    public List<SchemaIssue> getSchemaIssues() { return schemaIssues; }
    public void setSchemaIssues(List<SchemaIssue> schemaIssues) { this.schemaIssues = schemaIssues; }
    
    public List<EntityRelationshipIssue> getRelationshipIssues() { return relationshipIssues; }
    public void setRelationshipIssues(List<EntityRelationshipIssue> relationshipIssues) { this.relationshipIssues = relationshipIssues; }
    
    public List<String> getDataIntegrityIssues() { return dataIntegrityIssues; }
    public void setDataIntegrityIssues(List<String> dataIntegrityIssues) { this.dataIntegrityIssues = dataIntegrityIssues; }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}