package com.learningportal.service;

import java.util.List;

/**
 * Database Schema Validator Interface
 * 
 * Provides comprehensive validation of database schema integrity,
 * entity relationships, and constraint compliance.
 */
public interface DatabaseSchemaValidator {
    
    /**
     * Validates the complete database schema
     * @return SchemaValidationResult with detailed status and issues
     */
    SchemaValidationResult validateSchema();
    
    /**
     * Validates all entity relationships for integrity
     * @return List of relationship issues found
     */
    List<EntityRelationshipIssue> validateEntityRelationships();
    
    /**
     * Validates data integrity across all entities
     * @return DataIntegrityReport with comprehensive analysis
     */
    DataIntegrityReport validateDataIntegrity();
    
    /**
     * Attempts to repair identified schema issues
     * @param issues List of schema issues to repair
     * @return true if repairs were successful, false otherwise
     */
    boolean repairSchemaIssues(List<SchemaIssue> issues);
    
    /**
     * Validates specific entity relationship
     * @param parentEntity Parent entity class
     * @param childEntity Child entity class
     * @return List of issues found in the relationship
     */
    List<EntityRelationshipIssue> validateRelationship(Class<?> parentEntity, Class<?> childEntity);
    
    /**
     * Checks for orphaned records (child records without valid parent)
     * @return List of orphaned record issues
     */
    List<OrphanedRecordIssue> findOrphanedRecords();
    
    /**
     * Validates foreign key constraints
     * @return List of foreign key constraint violations
     */
    List<ConstraintViolation> validateForeignKeyConstraints();
}