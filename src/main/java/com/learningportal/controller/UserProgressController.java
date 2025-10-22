package com.learningportal.controller;

import com.learningportal.dto.ApiErrorResponse;
import com.learningportal.model.UserProgress;
import com.learningportal.service.UserProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for User Progress tracking operations.
 * 
 * This controller provides comprehensive progress tracking and analytics including:
 * - Progress CRUD operations
 * - Learning analytics and statistics
 * - Progress visualization data
 * - Learning recommendations
 */
@RestController
@RequestMapping("/progress")
@Tag(
    name = "User Progress", 
    description = "User progress tracking and analytics operations. " +
                  "Provides comprehensive learning progress management including progress updates, " +
                  "statistics, analytics, and visualization data for learning modules and topics."
)
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserProgressController {

    private static final Logger log = LoggerFactory.getLogger(UserProgressController.class);
    
    private final UserProgressService progressService;
    
    public UserProgressController(UserProgressService progressService) {
        this.progressService = progressService;
    }

    @Operation(
        summary = "Get user progress records",
        description = "Retrieve paginated list of user's progress records across all learning modules and topics. " +
                      "Results are ordered by most recently updated first."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved user progress",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Page.class),
                examples = @ExampleObject(
                    name = "Progress List",
                    value = """
                    {
                      "content": [
                        {
                          "id": 1,
                          "progressPercentage": 75,
                          "status": "IN_PROGRESS",
                          "timeSpentMinutes": 120,
                          "startedAt": "2024-01-15T10:30:00",
                          "lastAccessedAt": "2024-01-20T14:45:00",
                          "accessCount": 15,
                          "userRating": 4,
                          "module": {
                            "id": 1,
                            "name": "Java Fundamentals"
                          }
                        }
                      ],
                      "totalElements": 1,
                      "totalPages": 1,
                      "first": true,
                      "last": true
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<UserProgress>> getUserProgress(
            @Parameter(
                description = "User ID to get progress for",
                required = true,
                example = "1"
            )
            @PathVariable Long userId,
            
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size", example = "20")
            @RequestParam(defaultValue = "20") int size) {
        
        try {
            log.info("Fetching progress for user: {} (page: {}, size: {})", userId, page, size);
            Pageable pageable = PageRequest.of(page, size);
            Page<UserProgress> progress = progressService.getUserProgress(userId, pageable);
            log.info("Found {} progress records for user: {}", progress.getTotalElements(), userId);
            return ResponseEntity.ok(progress);
            
        } catch (Exception e) {
            log.error("Error fetching user progress for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Update module progress",
        description = "Update user's progress for a specific learning module. " +
                      "Automatically manages progress status transitions and timestamps."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Progress updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProgress.class),
                examples = @ExampleObject(
                    name = "Updated Progress",
                    value = """
                    {
                      "id": 1,
                      "progressPercentage": 85,
                      "status": "IN_PROGRESS",
                      "timeSpentMinutes": 150,
                      "startedAt": "2024-01-15T10:30:00",
                      "lastAccessedAt": "2024-01-20T16:00:00",
                      "accessCount": 16,
                      "module": {
                        "id": 1,
                        "name": "Java Fundamentals"
                      }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - Invalid progress data",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @PutMapping("/user/{userId}/module/{moduleId}")
    public ResponseEntity<UserProgress> updateModuleProgress(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            
            @Parameter(description = "Module ID", required = true, example = "1")
            @PathVariable Long moduleId,
            
            @Parameter(description = "Progress percentage (0-100)", example = "75")
            @RequestParam(required = false) 
            @Min(value = 0, message = "Progress cannot be negative")
            @Max(value = 100, message = "Progress cannot exceed 100%")
            Integer progressPercentage,
            
            @Parameter(description = "Additional time spent in minutes", example = "30")
            @RequestParam(required = false)
            @Min(value = 0, message = "Time spent cannot be negative")
            Integer timeSpentMinutes) {
        
        try {
            log.info("Updating module progress for user {} and module {}: {}%", userId, moduleId, progressPercentage);
            UserProgress updatedProgress = progressService.updateModuleProgress(userId, moduleId, progressPercentage, timeSpentMinutes);
            log.info("Updated progress record ID: {}", updatedProgress.getId());
            return ResponseEntity.ok(updatedProgress);
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid progress update request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating module progress for user {} and module {}", userId, moduleId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Update topic progress",
        description = "Update user's progress for a specific topic within a learning module. " +
                      "Automatically manages progress status transitions and timestamps."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Topic progress updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProgress.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - Invalid progress data",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @PutMapping("/user/{userId}/topic/{topicId}")
    public ResponseEntity<UserProgress> updateTopicProgress(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            
            @Parameter(description = "Topic ID", required = true, example = "1")
            @PathVariable Long topicId,
            
            @Parameter(description = "Progress percentage (0-100)", example = "100")
            @RequestParam(required = false)
            @Min(value = 0, message = "Progress cannot be negative")
            @Max(value = 100, message = "Progress cannot exceed 100%")
            Integer progressPercentage,
            
            @Parameter(description = "Additional time spent in minutes", example = "45")
            @RequestParam(required = false)
            @Min(value = 0, message = "Time spent cannot be negative")
            Integer timeSpentMinutes) {
        
        try {
            log.info("Updating topic progress for user {} and topic {}: {}%", userId, topicId, progressPercentage);
            UserProgress updatedProgress = progressService.updateTopicProgress(userId, topicId, progressPercentage, timeSpentMinutes);
            log.info("Updated progress record ID: {}", updatedProgress.getId());
            return ResponseEntity.ok(updatedProgress);
            
        } catch (IllegalArgumentException e) {
            log.warn("Invalid progress update request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating topic progress for user {} and topic {}", userId, topicId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get user progress statistics",
        description = "Retrieve comprehensive learning statistics for a user including completion rates, " +
                      "time spent, learning streaks, and efficiency metrics."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved progress statistics",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Progress Statistics",
                    value = """
                    {
                      "totalItems": 25,
                      "averageProgress": 68.5,
                      "totalTimeSpent": 1200,
                      "completedItems": 15,
                      "inProgressItems": 8,
                      "completionRate": 60.0,
                      "learningStreak": 7,
                      "learningEfficiency": 0.057
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @GetMapping("/user/{userId}/statistics")
    public ResponseEntity<Map<String, Object>> getUserProgressStatistics(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        try {
            log.info("Fetching progress statistics for user: {}", userId);
            Map<String, Object> statistics = progressService.getUserProgressStatistics(userId);
            log.info("Retrieved statistics for user: {}", userId);
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("Error fetching progress statistics for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get recent learning activity",
        description = "Retrieve user's recent learning activity within the specified number of days. " +
                      "Useful for showing recent progress and engagement patterns."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved recent activity",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProgress.class, type = "array")
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<UserProgress>> getRecentActivity(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            
            @Parameter(description = "Number of days to look back", example = "7")
            @RequestParam(defaultValue = "7") 
            @Min(value = 1, message = "Days must be at least 1")
            @Max(value = 365, message = "Days cannot exceed 365")
            int days) {
        
        try {
            log.info("Fetching recent activity for user {} (last {} days)", userId, days);
            List<UserProgress> recentActivity = progressService.getRecentActivity(userId, days);
            log.info("Found {} recent activities for user: {}", recentActivity.size(), userId);
            return ResponseEntity.ok(recentActivity);
            
        } catch (Exception e) {
            log.error("Error fetching recent activity for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get active learning sessions",
        description = "Retrieve user's currently active learning sessions (items with IN_PROGRESS status). " +
                      "Useful for showing what the user is currently working on."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved active sessions",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProgress.class, type = "array")
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<UserProgress>> getActiveSessions(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        try {
            log.info("Fetching active sessions for user: {}", userId);
            List<UserProgress> activeSessions = progressService.getActiveSessions(userId);
            log.info("Found {} active sessions for user: {}", activeSessions.size(), userId);
            return ResponseEntity.ok(activeSessions);
            
        } catch (Exception e) {
            log.error("Error fetching active sessions for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get learning velocity trends",
        description = "Retrieve learning velocity trends for data visualization. " +
                      "Returns daily progress, time spent, and activity counts for the specified period."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved velocity trends",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Velocity Trends",
                    value = """
                    [
                      {
                        "date": "2024-01-20",
                        "dailyProgress": 150,
                        "dailyTimeSpent": 90,
                        "dailyActivities": 3
                      },
                      {
                        "date": "2024-01-19",
                        "dailyProgress": 200,
                        "dailyTimeSpent": 120,
                        "dailyActivities": 4
                      }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @GetMapping("/user/{userId}/velocity")
    public ResponseEntity<List<Map<String, Object>>> getLearningVelocityTrends(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId,
            
            @Parameter(description = "Number of days to analyze", example = "30")
            @RequestParam(defaultValue = "30")
            @Min(value = 1, message = "Days must be at least 1")
            @Max(value = 365, message = "Days cannot exceed 365")
            int days) {
        
        try {
            log.info("Fetching learning velocity trends for user {} (last {} days)", userId, days);
            List<Map<String, Object>> trends = progressService.getLearningVelocityTrends(userId, days);
            log.info("Retrieved {} trend data points for user: {}", trends.size(), userId);
            return ResponseEntity.ok(trends);
            
        } catch (Exception e) {
            log.error("Error fetching velocity trends for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Rate learning progress",
        description = "Allow user to rate their learning experience and add notes for a specific progress record."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Rating updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserProgress.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request - Invalid rating data",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Progress record not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @PutMapping("/{progressId}/rating")
    public ResponseEntity<UserProgress> rateProgress(
            @Parameter(description = "Progress record ID", required = true, example = "1")
            @PathVariable Long progressId,
            
            @Parameter(description = "Rating (1-5 stars)", required = true, example = "4")
            @RequestParam
            @Min(value = 1, message = "Rating must be at least 1")
            @Max(value = 5, message = "Rating cannot exceed 5")
            Integer rating,
            
            @Parameter(description = "Optional notes about the learning experience")
            @RequestParam(required = false) String notes) {
        
        try {
            log.info("Rating progress record {} with rating: {}", progressId, rating);
            UserProgress updatedProgress = progressService.rateProgress(progressId, rating, notes);
            log.info("Updated rating for progress record ID: {}", updatedProgress.getId());
            return ResponseEntity.ok(updatedProgress);
            
        } catch (EntityNotFoundException e) {
            log.warn("Progress record not found with ID: {}", progressId);
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Invalid rating request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error rating progress record: {}", progressId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get completion rate by difficulty",
        description = "Retrieve user's completion statistics grouped by difficulty level. " +
                      "Useful for understanding learning patterns and identifying areas for improvement."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved completion rates",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Completion Rates",
                    value = """
                    [
                      {
                        "difficulty": "BEGINNER",
                        "totalAttempts": 10,
                        "completions": 9,
                        "averageProgress": 92.5,
                        "completionRate": 90.0
                      },
                      {
                        "difficulty": "INTERMEDIATE",
                        "totalAttempts": 8,
                        "completions": 5,
                        "averageProgress": 68.2,
                        "completionRate": 62.5
                      }
                    ]
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @GetMapping("/user/{userId}/completion-by-difficulty")
    public ResponseEntity<List<Map<String, Object>>> getCompletionRateByDifficulty(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long userId) {
        
        try {
            log.info("Fetching completion rate by difficulty for user: {}", userId);
            List<Map<String, Object>> completionRates = progressService.getCompletionRateByDifficulty(userId);
            log.info("Retrieved completion rates for {} difficulty levels for user: {}", completionRates.size(), userId);
            return ResponseEntity.ok(completionRates);
            
        } catch (Exception e) {
            log.error("Error fetching completion rates for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Delete progress record",
        description = "Delete a specific progress record. This action cannot be undone."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Progress record deleted successfully"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Progress record not found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Authentication required",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)
            )
        )
    })
    @DeleteMapping("/{progressId}")
    public ResponseEntity<Void> deleteProgress(
            @Parameter(description = "Progress record ID", required = true, example = "1")
            @PathVariable Long progressId) {
        
        try {
            log.info("Deleting progress record with ID: {}", progressId);
            progressService.deleteProgress(progressId);
            log.info("Deleted progress record with ID: {}", progressId);
            return ResponseEntity.noContent().build();
            
        } catch (EntityNotFoundException e) {
            log.warn("Progress record not found with ID: {}", progressId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting progress record: {}", progressId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}