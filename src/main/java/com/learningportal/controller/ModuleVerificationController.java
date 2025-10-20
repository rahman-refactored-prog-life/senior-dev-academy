package com.learningportal.controller;

import com.learningportal.model.LearningModule;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import com.learningportal.repository.InterviewQuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Module Verification Controller
 * 
 * Provides endpoints to verify that all learning modules and their components exist.
 */
@RestController
@RequestMapping("/verification")
@Tag(name = "Module Verification", description = "Verify all learning modules and components exist")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleVerificationController {
    
    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;
    
    public ModuleVerificationController(LearningModuleRepository moduleRepository,
                                      TopicRepository topicRepository,
                                      InterviewQuestionRepository questionRepository) {
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }
    
    @Operation(
        summary = "Verify all learning modules exist",
        description = "Returns status of all expected learning modules and their content"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module verification completed")
    })
    @GetMapping("/modules")
    public ResponseEntity<Map<String, Object>> verifyAllModules() {
        Map<String, Object> verification = new HashMap<>();
        
        // Expected modules
        List<String> expectedModules = Arrays.asList(
            "Node.js Fundamentals",
            "Java Fundamentals", 
            "React Development",
            "Data Structures & Algorithms",
            "Practice Questions",
            "FAANG Interview Questions",
            "System Design",
            "SQL Database Design",
            "NoSQL Databases",
            "Amazon Leadership Principles",
            "Behavioral Questions",
            "Cheatsheets",
            "Topic Summaries"
        );
        
        List<Map<String, Object>> moduleStatus = new ArrayList<>();
        int foundModules = 0;
        
        for (String expectedModule : expectedModules) {
            Map<String, Object> status = new HashMap<>();
            status.put("name", expectedModule);
            
            Optional<LearningModule> module = moduleRepository.findByNameIgnoreCase(expectedModule);
            if (module.isPresent()) {
                LearningModule m = module.get();
                status.put("exists", true);
                status.put("id", m.getId());
                status.put("category", m.getCategory().getDisplayName());
                status.put("difficulty", m.getDifficultyLevel().getDisplayName());
                status.put("estimatedHours", m.getEstimatedHours());
                status.put("topicCount", topicRepository.countByModule_Id(m.getId()));
                status.put("questionCount", questionRepository.countByModule_Id(m.getId()));
                foundModules++;
            } else {
                status.put("exists", false);
                status.put("topicCount", 0);
                status.put("questionCount", 0);
            }
            
            moduleStatus.add(status);
        }
        
        verification.put("expectedModules", expectedModules.size());
        verification.put("foundModules", foundModules);
        verification.put("completionPercentage", (foundModules * 100.0) / expectedModules.size());
        verification.put("allModulesExist", foundModules == expectedModules.size());
        verification.put("moduleStatus", moduleStatus);
        
        // Overall statistics
        verification.put("totalModules", moduleRepository.count());
        verification.put("totalTopics", topicRepository.count());
        verification.put("totalQuestions", questionRepository.count());
        
        return ResponseEntity.ok(verification);
    }
    
    @Operation(
        summary = "Get module statistics by category",
        description = "Returns breakdown of modules by category"
    )
    @GetMapping("/categories")
    public ResponseEntity<Map<String, Object>> getModulesByCategory() {
        Map<String, Object> categories = new HashMap<>();
        
        List<LearningModule> allModules = moduleRepository.findAll();
        Map<String, List<Map<String, Object>>> categoryBreakdown = new HashMap<>();
        
        for (LearningModule module : allModules) {
            String categoryName = module.getCategory().getDisplayName();
            
            Map<String, Object> moduleInfo = new HashMap<>();
            moduleInfo.put("name", module.getName());
            moduleInfo.put("difficulty", module.getDifficultyLevel().getDisplayName());
            moduleInfo.put("estimatedHours", module.getEstimatedHours());
            moduleInfo.put("topicCount", topicRepository.countByModule_Id(module.getId()));
            moduleInfo.put("questionCount", questionRepository.countByModule_Id(module.getId()));
            
            categoryBreakdown.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(moduleInfo);
        }
        
        categories.put("categoryBreakdown", categoryBreakdown);
        categories.put("totalCategories", categoryBreakdown.size());
        
        return ResponseEntity.ok(categories);
    }
    
    @Operation(
        summary = "Verify specific module exists",
        description = "Check if a specific module exists and return its details"
    )
    @GetMapping("/modules/{moduleName}")
    public ResponseEntity<Map<String, Object>> verifySpecificModule(@PathVariable String moduleName) {
        Map<String, Object> result = new HashMap<>();
        
        Optional<LearningModule> module = moduleRepository.findByNameIgnoreCase(moduleName);
        
        if (module.isPresent()) {
            LearningModule m = module.get();
            result.put("exists", true);
            result.put("id", m.getId());
            result.put("name", m.getName());
            result.put("description", m.getDescription());
            result.put("category", m.getCategory().getDisplayName());
            result.put("difficulty", m.getDifficultyLevel().getDisplayName());
            result.put("estimatedHours", m.getEstimatedHours());
            result.put("sortOrder", m.getSortOrder());
            result.put("topicCount", topicRepository.countByModule_Id(m.getId()));
            result.put("questionCount", questionRepository.countByModule_Id(m.getId()));
            result.put("createdAt", m.getCreatedAt());
            result.put("updatedAt", m.getUpdatedAt());
        } else {
            result.put("exists", false);
            result.put("message", "Module '" + moduleName + "' not found");
        }
        
        return ResponseEntity.ok(result);
    }
    
    @Operation(
        summary = "Get missing components report",
        description = "Returns report of any missing modules, topics, or questions"
    )
    @GetMapping("/missing-components")
    public ResponseEntity<Map<String, Object>> getMissingComponentsReport() {
        Map<String, Object> report = new HashMap<>();
        
        List<String> missingModules = new ArrayList<>();
        List<String> modulesWithoutTopics = new ArrayList<>();
        List<String> modulesWithoutQuestions = new ArrayList<>();
        
        List<String> expectedModules = Arrays.asList(
            "Node.js Fundamentals", "Java Fundamentals", "React Development",
            "Data Structures & Algorithms", "Practice Questions", "FAANG Interview Questions",
            "System Design", "SQL Database Design", "NoSQL Databases",
            "Amazon Leadership Principles", "Behavioral Questions", "Cheatsheets", "Topic Summaries"
        );
        
        for (String expectedModule : expectedModules) {
            Optional<LearningModule> module = moduleRepository.findByNameIgnoreCase(expectedModule);
            
            if (!module.isPresent()) {
                missingModules.add(expectedModule);
            } else {
                LearningModule m = module.get();
                long topicCount = topicRepository.countByModule_Id(m.getId());
                long questionCount = questionRepository.countByModule_Id(m.getId());
                
                if (topicCount == 0) {
                    modulesWithoutTopics.add(expectedModule);
                }
                if (questionCount == 0) {
                    modulesWithoutQuestions.add(expectedModule);
                }
            }
        }
        
        report.put("missingModules", missingModules);
        report.put("modulesWithoutTopics", modulesWithoutTopics);
        report.put("modulesWithoutQuestions", modulesWithoutQuestions);
        report.put("hasMissingComponents", !missingModules.isEmpty() || !modulesWithoutTopics.isEmpty() || !modulesWithoutQuestions.isEmpty());
        
        return ResponseEntity.ok(report);
    }
}