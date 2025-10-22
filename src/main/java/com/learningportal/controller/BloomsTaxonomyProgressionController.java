package com.learningportal.controller;

import com.learningportal.model.BloomsTaxonomyProgression;
import com.learningportal.service.BloomsTaxonomyProgressionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Bloom's Taxonomy Progression Management
 * 
 * Provides endpoints for managing Bloom's Taxonomy progression tracking
 * with Amazon competency alignment and zero-experience learning support.
 */
@RestController
@RequestMapping("/api/blooms-progression")
@Tag(name = "Bloom's Taxonomy Progression", description = "Bloom's Taxonomy progression tracking and analytics")
public class BloomsTaxonomyProgressionController {

    @Autowired
    private BloomsTaxonomyProgressionService progressionService;

    @Operation(summary = "Get or create progression for user and content")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progression retrieved or created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/user/{userId}/content/{contentId}")
    public ResponseEntity<BloomsTaxonomyProgression> getOrCreateProgression(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId) {
        
        BloomsTaxonomyProgression progression = progressionService.getOrCreateProgression(userId, contentId);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Update level score for user progression")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Score updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        @ApiResponse(responseCode = "404", description = "Progression not found")
    })
    @PutMapping("/user/{userId}/content/{contentId}/level/{level}/score")
    public ResponseEntity<BloomsTaxonomyProgression> updateLevelScore(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId,
            @Parameter(description = "Bloom's Taxonomy Level") @PathVariable BloomsTaxonomyProgression.BloomsLevel level,
            @Parameter(description = "Score (0-100)") @RequestParam Integer score) {
        
        if (score < 0 || score > 100) {
            return ResponseEntity.badRequest().build();
        }
        
        BloomsTaxonomyProgression progression = progressionService.updateLevelScore(userId, contentId, level, score);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Advance user to next Bloom's level")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Advancement attempted"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PostMapping("/user/{userId}/content/{contentId}/advance")
    public ResponseEntity<Map<String, Object>> advanceToNextLevel(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId) {
        
        boolean advanced = progressionService.advanceToNextLevel(userId, contentId);
        BloomsTaxonomyProgression progression = progressionService.getOrCreateProgression(userId, contentId);
        
        Map<String, Object> response = Map.of(
            "advanced", advanced,
            "currentLevel", progression.getCurrentLevel(),
            "nextLevel", progression.getNextLevel(),
            "readyForNext", progression.isReadyForNextLevel()
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get user progression analytics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}/analytics")
    public ResponseEntity<Map<String, Object>> getUserProgressionAnalytics(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> analytics = progressionService.getUserProgressionAnalytics(userId);
        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Get content progression analytics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Content not found")
    })
    @GetMapping("/content/{contentId}/analytics")
    public ResponseEntity<Map<String, Object>> getContentProgressionAnalytics(
            @Parameter(description = "Content ID") @PathVariable Long contentId) {
        
        Map<String, Object> analytics = progressionService.getContentProgressionAnalytics(contentId);
        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Get Amazon Senior SDE level candidates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Candidates retrieved successfully")
    })
    @GetMapping("/amazon-senior-sde-candidates")
    public ResponseEntity<List<BloomsTaxonomyProgression>> getAmazonSeniorSDECandidates() {
        List<BloomsTaxonomyProgression> candidates = progressionService.getAmazonSeniorSDECandidates();
        return ResponseEntity.ok(candidates);
    }

    @Operation(summary = "Get users needing additional support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping("/users-needing-support")
    public ResponseEntity<List<BloomsTaxonomyProgression>> getUsersNeedingSupport() {
        List<BloomsTaxonomyProgression> users = progressionService.getUsersNeedingSupport();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Generate personalized learning recommendations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recommendations generated successfully"),
        @ApiResponse(responseCode = "404", description = "User or content not found")
    })
    @GetMapping("/user/{userId}/content/{contentId}/recommendations")
    public ResponseEntity<List<String>> generateLearningRecommendations(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId) {
        
        List<String> recommendations = progressionService.generateLearningRecommendations(userId, contentId);
        return ResponseEntity.ok(recommendations);
    }

    @Operation(summary = "Validate prerequisites for level advancement")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Prerequisites validated"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/user/{userId}/content/{contentId}/validate-prerequisites/{targetLevel}")
    public ResponseEntity<Map<String, Object>> validatePrerequisites(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId,
            @Parameter(description = "Target Bloom's Level") @PathVariable BloomsTaxonomyProgression.BloomsLevel targetLevel) {
        
        boolean valid = progressionService.validatePrerequisites(userId, contentId, targetLevel);
        BloomsTaxonomyProgression progression = progressionService.getOrCreateProgression(userId, contentId);
        
        Map<String, Object> response = Map.of(
            "prerequisitesValid", valid,
            "currentLevel", progression.getCurrentLevel(),
            "targetLevel", targetLevel,
            "currentScores", Map.of(
                "remember", progression.getRememberLevelScore(),
                "understand", progression.getUnderstandLevelScore(),
                "apply", progression.getApplyLevelScore(),
                "analyze", progression.getAnalyzeLevelScore(),
                "evaluate", progression.getEvaluateLevelScore(),
                "create", progression.getCreateLevelScore()
            )
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all progressions for a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progressions retrieved successfully")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BloomsTaxonomyProgression>> getUserProgressions(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        List<BloomsTaxonomyProgression> progressions = progressionService.getUserProgressionAnalytics(userId)
            .entrySet().stream()
            .filter(entry -> "progressions".equals(entry.getKey()))
            .map(entry -> (List<BloomsTaxonomyProgression>) entry.getValue())
            .findFirst()
            .orElse(List.of());
        
        return ResponseEntity.ok(progressions);
    }

    @Operation(summary = "Get Bloom's Taxonomy level information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Level information retrieved successfully")
    })
    @GetMapping("/levels")
    public ResponseEntity<Map<String, Object>> getBloomsLevels() {
        Map<String, Object> levels = Map.of(
            "REMEMBER", Map.of(
                "name", "Remember",
                "description", "Recall facts and basic concepts",
                "amazonLevel", "L3",
                "keywords", List.of("define", "list", "recall", "recognize", "retrieve")
            ),
            "UNDERSTAND", Map.of(
                "name", "Understand",
                "description", "Explain ideas or concepts",
                "amazonLevel", "L3-L4",
                "keywords", List.of("explain", "interpret", "summarize", "classify", "compare")
            ),
            "APPLY", Map.of(
                "name", "Apply",
                "description", "Use information in new situations",
                "amazonLevel", "L4",
                "keywords", List.of("execute", "implement", "solve", "use", "demonstrate")
            ),
            "ANALYZE", Map.of(
                "name", "Analyze",
                "description", "Draw connections among ideas",
                "amazonLevel", "L4-L5",
                "keywords", List.of("differentiate", "organize", "relate", "compare", "contrast")
            ),
            "EVALUATE", Map.of(
                "name", "Evaluate",
                "description", "Justify a stand or decision",
                "amazonLevel", "L5",
                "keywords", List.of("appraise", "argue", "defend", "judge", "select")
            ),
            "CREATE", Map.of(
                "name", "Create",
                "description", "Produce new or original work",
                "amazonLevel", "L5-L6",
                "keywords", List.of("design", "construct", "plan", "produce", "invent")
            )
        );
        
        return ResponseEntity.ok(levels);
    }

    @Operation(summary = "Get progression statistics summary")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    })
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getProgressionStatistics() {
        // This would typically aggregate data from the service
        Map<String, Object> stats = Map.of(
            "totalProgressions", "Available via service",
            "averageScores", "Available via service",
            "levelDistribution", "Available via service",
            "amazonReadinessDistribution", "Available via service"
        );
        
        return ResponseEntity.ok(stats);
    }
}