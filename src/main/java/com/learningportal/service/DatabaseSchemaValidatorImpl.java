package com.learningportal.service;

import com.learningportal.model.InterviewQuestion;
import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.repository.InterviewQuestionRepository;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.*;

/**
 * Database Schema Validator Implementation
 * 
 * Provides comprehensive validation of database schema integrity,
 * entity relationships, and constraint compliance for the learning portal.
 */
@Service
public class DatabaseSchemaValidatorImpl implements DatabaseSchemaValidator {
    
    private static final Logger log = LoggerFactory.getLogger(DatabaseSchemaValidatorImpl.class);
    
    private final EntityManager entityManager;
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    
    public DatabaseSchemaValidatorImpl(EntityManager entityManager,
                                     LearningModuleRepository moduleRepository,
                                     TopicRepository topicRepository,
                                     InterviewQuestionRepository questionRepository) {
        this.entityManager = entityManager;
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }
    
    @Override
    public SchemaValidationResult validateSchema() {
        log.info("🔍 Starting comprehensive schema validation...");
        
        long startTime = System.currentTimeMillis();
        List<SchemaIssue> issues = new ArrayList<>();
        Map<String, TableStatus> tableStatuses = new HashMap<>();
        
        try {
            // Validate core tables exist and have proper structure
            validateTable("learning_modules", LearningModule.class, issues, tableStatuses);
            validateTable("topics", Topic.class, issues, tableStatuses);
            validateTable("interview_questions", InterviewQuestion.class, issues, tableStatuses);
            
            // Validate relationships
            List<EntityRelationshipIssue> relationshipIssues = validateEntityRelationships();
            
            // Convert relationship issues to schema issues
            for (EntityRelationshipIssue relationshipIssue : relationshipIssues) {
                SchemaIssue schemaIssue = new SchemaIssue();
                schemaIssue.setTableName(relationshipIssue.getChildEntity());
                schemaIssue.setIssueType(SchemaIssue.IssueType.FOREIGN_KEY_VIOLATION);
                schemaIssue.setSeverity(SchemaIssue.IssueSeverity.HIGH);
                schemaIssue.setDescription(relationshipIssue.getDescription());
                schemaIssue.setSuggestedFix(relationshipIssue.getSuggestedFix());
                issues.add(schemaIssue);
            }
            
            long validationTime = System.currentTimeMillis() - startTime;
            
            SchemaValidationResult result;
            if (issues.isEmpty()) {
                result = SchemaValidationResult.success(validationTime);
                log.info("✅ Schema validation passed in {}ms", validationTime);
            } else {
                result = SchemaValidationResult.failure(issues, validationTime);
                log.warn("❌ Schema validation found {} issues in {}ms", issues.size(), validationTime);
            }
            
            result.setTableStatuses(tableStatuses);
            result.setTotalTables(tableStatuses.size());
            result.setValidTables((int) tableStatuses.values().stream()
                                 .filter(TableStatus::isHealthy).count());
            
            return result;
            
        } catch (Exception e) {
            log.error("Schema validation failed with exception", e);
            
            SchemaIssue criticalIssue = new SchemaIssue();
            criticalIssue.setIssueType(SchemaIssue.IssueType.MISSING_TABLE);
            criticalIssue.setSeverity(SchemaIssue.IssueSeverity.CRITICAL);
            criticalIssue.setDescription("Schema validation failed: " + e.getMessage());
            issues.add(criticalIssue);
            
            return SchemaValidationResult.failure(issues, System.currentTimeMillis() - startTime);
        }
    }
    
    @Override
    public List<EntityRelationshipIssue> validateEntityRelationships() {
        log.info("🔗 Validating entity relationships...");
        
        List<EntityRelationshipIssue> issues = new ArrayList<>();
        
        // Validate LearningModule -> Topic relationship
        issues.addAll(validateModuleTopicRelationship());
        
        // Validate LearningModule -> InterviewQuestion relationship
        issues.addAll(validateModuleQuestionRelationship());
        
        log.info("Found {} relationship issues", issues.size());
        return issues;
    }
    
    @Override
    public DataIntegrityReport validateDataIntegrity() {
        log.info("📊 Starting data integrity analysis...");
        
        long startTime = System.currentTimeMillis();
        DataIntegrityReport report = new DataIntegrityReport();
        
        try {
            // Get relationship issues
            List<EntityRelationshipIssue> relationshipIssues = validateEntityRelationships();
            report.setRelationshipIssues(relationshipIssues);
            
            // Find orphaned records
            List<OrphanedRecordIssue> orphanedRecords = findOrphanedRecords();
            report.setOrphanedRecords(orphanedRecords);
            
            // Validate constraints
            List<ConstraintViolation> constraintViolations = validateForeignKeyConstraints();
            report.setConstraintViolations(constraintViolations);
            
            // Calculate integrity statistics
            Map<String, EntityIntegrityStatus> entityStatuses = calculateEntityIntegrityStatuses();
            report.setEntityStatuses(entityStatuses);
            
            // Calculate totals
            int totalRecords = entityStatuses.values().stream()
                             .mapToInt(EntityIntegrityStatus::getTotalRecords).sum();
            int validRecords = entityStatuses.values().stream()
                             .mapToInt(EntityIntegrityStatus::getValidRecords).sum();
            
            report.setTotalRecords(totalRecords);
            report.setValidRecords(validRecords);
            report.setTotalRelationships(relationshipIssues.size() + orphanedRecords.size());
            report.setValidRelationships(report.getTotalRelationships() - relationshipIssues.size());
            
            // Determine overall integrity status
            boolean hasIssues = report.hasIssues();
            report.setDataIntegrityValid(!hasIssues);
            
            long analysisTime = System.currentTimeMillis() - startTime;
            report.setAnalysisTimeMs(analysisTime);
            
            log.info(report.getIntegritySummary());
            return report;
            
        } catch (Exception e) {
            log.error("Data integrity analysis failed", e);
            report.setDataIntegrityValid(false);
            report.setAnalysisTimeMs(System.currentTimeMillis() - startTime);
            return report;
        }
    }
    
    @Override
    public boolean repairSchemaIssues(List<SchemaIssue> issues) {
        log.info("🔧 Attempting to repair {} schema issues", issues.size());
        
        int repairedCount = 0;
        
        for (SchemaIssue issue : issues) {
            if (issue.canAutoFix()) {
                if (repairSingleIssue(issue)) {
                    repairedCount++;
                    log.info("✅ Repaired: {}", issue.getFormattedIssue());
                } else {
                    log.warn("❌ Failed to repair: {}", issue.getFormattedIssue());
                }
            } else {
                log.info("⚠️ Manual repair required: {}", issue.getFormattedIssue());
            }
        }
        
        boolean allRepaired = repairedCount == issues.stream()
                                                   .mapToInt(issue -> issue.canAutoFix() ? 1 : 0)
                                                   .sum();
        
        log.info("🔧 Repair summary: {}/{} auto-fixable issues repaired", 
                repairedCount, issues.stream().mapToInt(issue -> issue.canAutoFix() ? 1 : 0).sum());
        
        return allRepaired;
    }
    
    @Override
    public List<EntityRelationshipIssue> validateRelationship(Class<?> parentEntity, Class<?> childEntity) {
        log.info("🔗 Validating relationship: {} -> {}", 
                parentEntity.getSimpleName(), childEntity.getSimpleName());
        
        List<EntityRelationshipIssue> issues = new ArrayList<>();
        
        if (parentEntity == LearningModule.class && childEntity == Topic.class) {
            issues.addAll(validateModuleTopicRelationship());
        } else if (parentEntity == LearningModule.class && childEntity == InterviewQuestion.class) {
            issues.addAll(validateModuleQuestionRelationship());
        } else {
            log.warn("⚠️ Unknown relationship: {} -> {}", 
                    parentEntity.getSimpleName(), childEntity.getSimpleName());
        }
        
        return issues;
    }
    
    @Override
    public List<OrphanedRecordIssue> findOrphanedRecords() {
        log.info("🔍 Searching for orphaned records...");
        
        List<OrphanedRecordIssue> orphanedRecords = new ArrayList<>();
        
        // Find topics without valid modules
        orphanedRecords.addAll(findOrphanedTopics());
        
        // Find interview questions without valid modules
        orphanedRecords.addAll(findOrphanedQuestions());
        
        log.info("Found {} orphaned records", orphanedRecords.size());
        return orphanedRecords;
    }
    
    @Override
    public List<ConstraintViolation> validateForeignKeyConstraints() {
        log.info("🔒 Validating foreign key constraints...");
        
        List<ConstraintViolation> violations = new ArrayList<>();
        
        // Check Topic -> LearningModule foreign key constraints
        violations.addAll(validateTopicModuleConstraints());
        
        // Check InterviewQuestion -> LearningModule foreign key constraints
        violations.addAll(validateQuestionModuleConstraints());
        
        log.info("Found {} constraint violations", violations.size());
        return violations;
    }
    
    // Private helper methods
    
    private void validateTable(String tableName, Class<?> entityClass, 
                             List<SchemaIssue> issues, Map<String, TableStatus> tableStatuses) {
        try {
            // Check if table exists by querying it
            Query query = entityManager.createQuery("SELECT COUNT(*) FROM " + entityClass.getSimpleName());
            Long count = (Long) query.getSingleResult();
            
            TableStatus status = new TableStatus(tableName, true, count.intValue(), 
                                                TableStatus.TableHealth.HEALTHY);
            status.setDescription("Table exists and is accessible");
            tableStatuses.put(tableName, status);
            
            log.debug("✅ Table '{}' validated: {} records", tableName, count);
            
        } catch (Exception e) {
            log.error("❌ Table '{}' validation failed: {}", tableName, e.getMessage());
            
            SchemaIssue issue = new SchemaIssue(tableName, SchemaIssue.IssueType.MISSING_TABLE,
                                               SchemaIssue.IssueSeverity.CRITICAL,
                                               "Table does not exist or is not accessible: " + e.getMessage());
            issue.setSuggestedFix("Ensure database schema is properly initialized");
            issues.add(issue);
            
            TableStatus status = new TableStatus(tableName, false, 0, TableStatus.TableHealth.MISSING);
            status.setDescription("Table missing or inaccessible: " + e.getMessage());
            tableStatuses.put(tableName, status);
        }
    }
    
    private List<EntityRelationshipIssue> validateModuleTopicRelationship() {
        List<EntityRelationshipIssue> issues = new ArrayList<>();
        
        try {
            // Find topics with null or invalid module references
            Query query = entityManager.createQuery(
                "SELECT t FROM Topic t WHERE t.module IS NULL OR t.module.id NOT IN " +
                "(SELECT m.id FROM LearningModule m)"
            );
            
            @SuppressWarnings("unchecked")
            List<Topic> invalidTopics = query.getResultList();
            
            for (Topic topic : invalidTopics) {
                EntityRelationshipIssue issue = new EntityRelationshipIssue(
                    "LearningModule", "Topic", 
                    EntityRelationshipIssue.RelationshipIssueType.MISSING_PARENT,
                    "Topic references invalid or missing LearningModule"
                );
                issue.setChildId(topic.getId());
                issue.setForeignKeyColumn("module_id");
                issue.setSuggestedFix("Update topic to reference valid module or delete orphaned topic");
                issues.add(issue);
            }
            
        } catch (Exception e) {
            log.error("Failed to validate module-topic relationship", e);
        }
        
        return issues;
    }
    
    private List<EntityRelationshipIssue> validateModuleQuestionRelationship() {
        List<EntityRelationshipIssue> issues = new ArrayList<>();
        
        try {
            // Find interview questions with null or invalid module references
            Query query = entityManager.createQuery(
                "SELECT q FROM InterviewQuestion q WHERE q.module IS NULL OR q.module.id NOT IN " +
                "(SELECT m.id FROM LearningModule m)"
            );
            
            @SuppressWarnings("unchecked")
            List<InterviewQuestion> invalidQuestions = query.getResultList();
            
            for (InterviewQuestion question : invalidQuestions) {
                EntityRelationshipIssue issue = new EntityRelationshipIssue(
                    "LearningModule", "InterviewQuestion",
                    EntityRelationshipIssue.RelationshipIssueType.MISSING_PARENT,
                    "InterviewQuestion references invalid or missing LearningModule"
                );
                issue.setChildId(question.getId());
                issue.setForeignKeyColumn("module_id");
                issue.setSuggestedFix("Update question to reference valid module or delete orphaned question");
                issues.add(issue);
            }
            
        } catch (Exception e) {
            log.error("Failed to validate module-question relationship", e);
        }
        
        return issues;
    }
    
    private List<OrphanedRecordIssue> findOrphanedTopics() {
        List<OrphanedRecordIssue> orphanedRecords = new ArrayList<>();
        
        try {
            Query query = entityManager.createQuery(
                "SELECT t.id, t.module.id FROM Topic t WHERE t.module.id NOT IN " +
                "(SELECT m.id FROM LearningModule m)"
            );
            
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            for (Object[] result : results) {
                OrphanedRecordIssue issue = new OrphanedRecordIssue(
                    "topics", "module_id", result[0], result[1]
                );
                issue.setParentTableName("learning_modules");
                issue.setDescription("Topic references non-existent learning module");
                issue.setSuggestedAction(OrphanedRecordIssue.OrphanedRecordAction.DELETE_RECORD);
                orphanedRecords.add(issue);
            }
            
        } catch (Exception e) {
            log.error("Failed to find orphaned topics", e);
        }
        
        return orphanedRecords;
    }
    
    private List<OrphanedRecordIssue> findOrphanedQuestions() {
        List<OrphanedRecordIssue> orphanedRecords = new ArrayList<>();
        
        try {
            Query query = entityManager.createQuery(
                "SELECT q.id, q.module.id FROM InterviewQuestion q WHERE q.module.id NOT IN " +
                "(SELECT m.id FROM LearningModule m)"
            );
            
            @SuppressWarnings("unchecked")
            List<Object[]> results = query.getResultList();
            
            for (Object[] result : results) {
                OrphanedRecordIssue issue = new OrphanedRecordIssue(
                    "interview_questions", "module_id", result[0], result[1]
                );
                issue.setParentTableName("learning_modules");
                issue.setDescription("InterviewQuestion references non-existent learning module");
                issue.setSuggestedAction(OrphanedRecordIssue.OrphanedRecordAction.DELETE_RECORD);
                orphanedRecords.add(issue);
            }
            
        } catch (Exception e) {
            log.error("Failed to find orphaned questions", e);
        }
        
        return orphanedRecords;
    }
    
    private List<ConstraintViolation> validateTopicModuleConstraints() {
        List<ConstraintViolation> violations = new ArrayList<>();
        
        try {
            // Check for topics with null module_id (NOT NULL constraint)
            long nullModuleCount = topicRepository.countByModuleIsNull();
            
            if (nullModuleCount > 0) {
                ConstraintViolation violation = new ConstraintViolation(
                    "fk_topic_module", "topics", 
                    ConstraintViolation.ConstraintType.NOT_NULL,
                    String.format("%d topics have null module_id", nullModuleCount)
                );
                violation.setColumnName("module_id");
                violation.setSuggestedFix("Update topics to reference valid modules");
                violation.setAutoFixable(false);
                violations.add(violation);
            }
            
        } catch (Exception e) {
            log.error("Failed to validate topic-module constraints", e);
        }
        
        return violations;
    }
    
    private List<ConstraintViolation> validateQuestionModuleConstraints() {
        List<ConstraintViolation> violations = new ArrayList<>();
        
        try {
            // Check for questions with null module_id (NOT NULL constraint)
            long nullModuleCount = questionRepository.countByModuleIsNull();
            
            if (nullModuleCount > 0) {
                ConstraintViolation violation = new ConstraintViolation(
                    "fk_question_module", "interview_questions",
                    ConstraintViolation.ConstraintType.NOT_NULL,
                    String.format("%d questions have null module_id", nullModuleCount)
                );
                violation.setColumnName("module_id");
                violation.setSuggestedFix("Update questions to reference valid modules");
                violation.setAutoFixable(false);
                violations.add(violation);
            }
            
        } catch (Exception e) {
            log.error("Failed to validate question-module constraints", e);
        }
        
        return violations;
    }
    
    private Map<String, EntityIntegrityStatus> calculateEntityIntegrityStatuses() {
        Map<String, EntityIntegrityStatus> statuses = new HashMap<>();
        
        try {
            // LearningModule status
            long moduleCount = moduleRepository.count();
            statuses.put("LearningModule", new EntityIntegrityStatus("LearningModule", 
                                                                   (int) moduleCount, (int) moduleCount));
            
            // Topic status
            long topicCount = topicRepository.count();
            long validTopicCount = topicRepository.countByModuleIsNotNull();
            statuses.put("Topic", new EntityIntegrityStatus("Topic", 
                                                           (int) topicCount, (int) validTopicCount));
            
            // InterviewQuestion status
            long questionCount = questionRepository.count();
            long validQuestionCount = questionRepository.countByModuleIsNotNull();
            statuses.put("InterviewQuestion", new EntityIntegrityStatus("InterviewQuestion", 
                                                                       (int) questionCount, (int) validQuestionCount));
            
        } catch (Exception e) {
            log.error("Failed to calculate entity integrity statuses", e);
        }
        
        return statuses;
    }
    
    private boolean repairSingleIssue(SchemaIssue issue) {
        // For now, we'll log that repair is needed but not implement automatic fixes
        // This would require careful consideration of data safety
        log.info("🔧 Auto-repair not implemented for: {}", issue.getFormattedIssue());
        return false;
    }
}