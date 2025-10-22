package com.learningportal.controller;

import com.learningportal.model.SpacedRepetitionSchedule;
import com.learningportal.service.SpacedRepetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for Spaced Repetition Management
 * 
 * Provides endpoints for managing spaced repetition schedules with
 * scientifically-optimized review patterns and Amazon interview preparation.
 */
@RestController
@RequestMapping("/api/spaced-repetition")
@Tag(name = "Spaced Repetition", description = "Spaced repetition scheduling and review management")
public class SpacedRepetitionController {

    @Autowired
    private SpacedRepetitionService spacedRepetitionService;

    @Operation(summary = "Get or create spaced repetition schedule")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Schedule retrieved or created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @GetMapping("/user/{userId}/content/{contentId}/type/{contentType}")
    public ResponseEntity<SpacedRepetitionSchedule> getOrCreateSchedule(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId,
            @Parameter(description = "Content Type") @PathVariable String contentType) {
        
        SpacedRepetitionSchedule schedule = spacedRepetitionService.getOrCreateSchedule(userId, contentId, contentType);
        return ResponseEntity.ok(schedule);
    }

    @Operation(summary = "Record a review session")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Review recorded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PostMapping("/user/{userId}/content/{contentId}/type/{contentType}/review")
    public ResponseEntity<SpacedRepetitionSchedule> recordReview(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId,
            @Parameter(description = "Content Type") @PathVariable String contentType,
            @Parameter(description = "Performance rating (1-5)") @RequestParam int performanceRating,
            @Parameter(description = "Retention score (0-100)") @RequestParam int retentionScore) {
        
        if (performanceRating < 1 || performanceRating > 5) {
            return ResponseEntity.badRequest().build();
        }
        
        if (retentionScore < 0 || retentionScore > 100) {
            return ResponseEntity.badRequest().build();
        }
        
        SpacedRepetitionSchedule schedule = spacedRepetitionService.recordReview(
            userId, contentId, contentType, performanceRating, retentionScore);
        return ResponseEntity.ok(schedule);
    }

    @Operation(summary = "Get due reviews for user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Due reviews retrieved successfully")
    })
    @GetMapping("/user/{userId}/due-reviews")
    public ResponseEntity<List<SpacedRepetitionSchedule>> getDueReviews(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        List<SpacedRepetitionSchedule> dueReviews = spacedRepetitionService.getDueReviews(userId);
        return ResponseEntity.ok(dueReviews);
    }

    @Operation(summary = "Get next N reviews for user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Next reviews retrieved successfully")
    })
    @GetMapping("/user/{userId}/next-reviews")
    public ResponseEntity<List<SpacedRepetitionSchedule>> getNextReviews(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Number of reviews to retrieve") @RequestParam(defaultValue = "10") int count) {
        
        List<SpacedRepetitionSchedule> nextReviews = spacedRepetitionService.getNextReviews(userId, count);
        return ResponseEntity.ok(nextReviews);
    }

    @Operation(summary = "Get overdue reviews for user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Overdue reviews retrieved successfully")
    })
    @GetMapping("/user/{userId}/overdue-reviews")
    public ResponseEntity<List<SpacedRepetitionSchedule>> getOverdueReviews(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        List<SpacedRepetitionSchedule> overdueReviews = spacedRepetitionService.getOverdueReviews(userId);
        return ResponseEntity.ok(overdueReviews);
    }

    @Operation(summary = "Get Amazon interview priority reviews")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Priority reviews retrieved successfully")
    })
    @GetMapping("/user/{userId}/amazon-priority-reviews")
    public ResponseEntity<List<SpacedRepetitionSchedule>> getAmazonInterviewPriorityReviews(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        List<SpacedRepetitionSchedule> priorityReviews = spacedRepetitionService.getAmazonInterviewPriorityReviews(userId);
        return ResponseEntity.ok(priorityReviews);
    }

    @Operation(summary = "Set Amazon interview priority for content")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Priority updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PutMapping("/user/{userId}/content/{contentId}/type/{contentType}/amazon-priority")
    public ResponseEntity<SpacedRepetitionSchedule> setAmazonInterviewPriority(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Content ID") @PathVariable Long contentId,
            @Parameter(description = "Content Type") @PathVariable String contentType,
            @Parameter(description = "Priority flag") @RequestParam boolean priority) {
        
        SpacedRepetitionSchedule schedule = spacedRepetitionService.setAmazonInterviewPriority(
            userId, contentId, contentType, priority);
        return ResponseEntity.ok(schedule);
    }

    @Operation(summary = "Get daily review schedule for user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Daily schedule retrieved successfully")
    })
    @GetMapping("/user/{userId}/daily-schedule")
    public ResponseEntity<Map<String, Object>> getDailyReviewSchedule(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Number of days") @RequestParam(defaultValue = "7") int days) {
        
        Map<String, Object> dailySchedule = spacedRepetitionService.getDailyReviewSchedule(userId, days);
        return ResponseEntity.ok(dailySchedule);
    }

    @Operation(summary = "Get user learning analytics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully")
    })
    @GetMapping("/user/{userId}/analytics")
    public ResponseEntity<Map<String, Object>> getUserLearningAnalytics(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> analytics = spacedRepetitionService.getUserLearningAnalytics(userId);
        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Generate personalized review recommendations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recommendations generated successfully")
    })
    @GetMapping("/user/{userId}/recommendations")
    public ResponseEntity<List<String>> generateReviewRecommendations(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        List<String> recommendations = spacedRepetitionService.generateReviewRecommendations(userId);
        return ResponseEntity.ok(recommendations);
    }

    @Operation(summary = "Optimize schedule for Amazon interview preparation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Schedule optimized successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid target date")
    })
    @PostMapping("/user/{userId}/optimize-for-amazon-interview")
    public ResponseEntity<Map<String, String>> optimizeForAmazonInterview(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Target interview date") 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDate) {
        
        if (targetDate.isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().build();
        }
        
        spacedRepetitionService.optimizeForAmazonInterview(userId, targetDate);
        
        Map<String, String> response = Map.of(
            "message", "Schedule optimized for Amazon interview preparation",
            "targetDate", targetDate.toString(),
            "optimizationApplied", "true"
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get content difficulty analysis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Analysis retrieved successfully")
    })
    @GetMapping("/content-difficulty-analysis")
    public ResponseEntity<Map<String, Object>> getContentDifficultyAnalysis() {
        Map<String, Object> analysis = spacedRepetitionService.getContentDifficultyAnalysis();
        return ResponseEntity.ok(analysis);
    }

    @Operation(summary = "Bulk update schedules for content type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Schedules updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PutMapping("/content-type/{contentType}/bulk-update")
    public ResponseEntity<Map<String, String>> bulkUpdateContentType(
            @Parameter(description = "Content Type") @PathVariable String contentType,
            @Parameter(description = "Difficulty adjustment factor") @RequestParam(required = false) BigDecimal difficultyAdjustment,
            @Parameter(description = "Amazon priority flag") @RequestParam(required = false) Boolean amazonPriority) {
        
        if (difficultyAdjustment != null && 
            (difficultyAdjustment.compareTo(BigDecimal.valueOf(0.1)) < 0 || 
             difficultyAdjustment.compareTo(BigDecimal.valueOf(3.0)) > 0)) {
            return ResponseEntity.badRequest().build();
        }
        
        spacedRepetitionService.bulkUpdateContentType(contentType, difficultyAdjustment, amazonPriority);
        
        Map<String, String> response = Map.of(
            "message", "Bulk update completed successfully",
            "contentType", contentType,
            "updatedSchedules", "true"
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get system-wide spaced repetition statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    })
    @GetMapping("/system-statistics")
    public ResponseEntity<Map<String, Object>> getSystemStatistics() {
        Map<String, Object> stats = spacedRepetitionService.getSystemStatistics();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Get spaced repetition algorithm information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Algorithm information retrieved successfully")
    })
    @GetMapping("/algorithm-info")
    public ResponseEntity<Map<String, Object>> getAlgorithmInfo() {
        Map<String, Object> info = Map.of(
            "algorithm", "SM-2 (SuperMemo 2)",
            "description", "Scientifically-optimized spaced repetition algorithm",
            "performanceRatings", Map.of(
                "1", "Complete blackout",
                "2", "Incorrect response; correct answer remembered",
                "3", "Incorrect response; correct answer seemed easy to recall",
                "4", "Correct response after hesitation",
                "5", "Perfect response"
            ),
            "easeFactorRange", "1.3 to 2.5",
            "amazonOptimizations", List.of(
                "Priority scheduling for interview preparation",
                "Accelerated review cycles for high-priority content",
                "Retention threshold optimization for Amazon standards"
            ),
            "adaptiveFeatures", List.of(
                "Individual learning pattern recognition",
                "Difficulty adjustment based on content type",
                "Cognitive load management integration"
            )
        );
        
        return ResponseEntity.ok(info);
    }
}