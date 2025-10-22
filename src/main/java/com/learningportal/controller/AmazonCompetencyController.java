package com.learningportal.controller;

import com.learningportal.model.AmazonCompetencyProgression;
import com.learningportal.service.AmazonCompetencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Amazon Competency Management
 * 
 * Provides endpoints for managing Amazon competency progression with
 * L3-L6 tracking, Leadership Principles integration, and hiring bar standards.
 */
@RestController
@RequestMapping("/api/amazon-competency")
@Tag(name = "Amazon Competency", description = "Amazon competency progression and interview readiness")
public class AmazonCompetencyController {

    @Autowired
    private AmazonCompetencyService amazonCompetencyService;

    @Operation(summary = "Get or create competency progression for user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progression retrieved or created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<AmazonCompetencyProgression> getOrCreateProgression(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        AmazonCompetencyProgression progression = amazonCompetencyService.getOrCreateProgression(userId);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Update competency assessment scores")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Assessment updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PutMapping("/user/{userId}/assessment")
    public ResponseEntity<AmazonCompetencyProgression> updateCompetencyAssessment(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Interview readiness score (0-100)") @RequestParam(required = false) Integer interviewReadinessScore,
            @Parameter(description = "Cultural fit score (0-100)") @RequestParam(required = false) Integer culturalFitScore) {
        
        if ((interviewReadinessScore != null && (interviewReadinessScore < 0 || interviewReadinessScore > 100)) ||
            (culturalFitScore != null && (culturalFitScore < 0 || culturalFitScore > 100))) {
            return ResponseEntity.badRequest().build();
        }
        
        AmazonCompetencyProgression progression = amazonCompetencyService.updateCompetencyAssessment(
            userId, interviewReadinessScore, culturalFitScore);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Set target Amazon level")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Target level updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PutMapping("/user/{userId}/target-level")
    public ResponseEntity<AmazonCompetencyProgression> setTargetLevel(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Target Amazon level") @RequestParam AmazonCompetencyProgression.AmazonLevel targetLevel) {
        
        AmazonCompetencyProgression progression = amazonCompetencyService.setTargetLevel(userId, targetLevel);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Update Leadership Principles progress")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leadership Principles progress updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PutMapping("/user/{userId}/leadership-principles")
    public ResponseEntity<AmazonCompetencyProgression> updateLeadershipPrinciplesProgress(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Leadership Principles scores") @RequestBody Map<String, Integer> principlesScores) {
        
        // Validate scores are within range
        for (Integer score : principlesScores.values()) {
            if (score < 0 || score > 100) {
                return ResponseEntity.badRequest().build();
            }
        }
        
        AmazonCompetencyProgression progression = amazonCompetencyService.updateLeadershipPrinciplesProgress(
            userId, principlesScores);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Update technical competencies")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Technical competencies updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PutMapping("/user/{userId}/technical-competencies")
    public ResponseEntity<AmazonCompetencyProgression> updateTechnicalCompetencies(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "Technical competencies") @RequestBody Map<String, Object> competencies) {
        
        AmazonCompetencyProgression progression = amazonCompetencyService.updateTechnicalCompetencies(
            userId, competencies);
        return ResponseEntity.ok(progression);
    }

    @Operation(summary = "Get Amazon interview readiness assessment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Assessment retrieved successfully")
    })
    @GetMapping("/user/{userId}/interview-readiness")
    public ResponseEntity<Map<String, Object>> getInterviewReadinessAssessment(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> assessment = amazonCompetencyService.getInterviewReadinessAssessment(userId);
        return ResponseEntity.ok(assessment);
    }

    @Operation(summary = "Get Leadership Principles assessment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leadership Principles assessment retrieved successfully")
    })
    @GetMapping("/user/{userId}/leadership-principles-assessment")
    public ResponseEntity<Map<String, Object>> getLeadershipPrinciplesAssessment(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> assessment = amazonCompetencyService.getLeadershipPrinciplesAssessment(userId);
        return ResponseEntity.ok(assessment);
    }

    @Operation(summary = "Get competency gap analysis")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Gap analysis retrieved successfully")
    })
    @GetMapping("/user/{userId}/gap-analysis")
    public ResponseEntity<Map<String, Object>> getCompetencyGapAnalysis(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> analysis = amazonCompetencyService.getCompetencyGapAnalysis(userId);
        return ResponseEntity.ok(analysis);
    }

    @Operation(summary = "Get Amazon level progression roadmap")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Progression roadmap retrieved successfully")
    })
    @GetMapping("/user/{userId}/progression-roadmap")
    public ResponseEntity<Map<String, Object>> getProgressionRoadmap(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> roadmap = amazonCompetencyService.getProgressionRoadmap(userId);
        return ResponseEntity.ok(roadmap);
    }

    @Operation(summary = "Generate personalized Amazon career development plan")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Development plan generated successfully")
    })
    @GetMapping("/user/{userId}/career-development-plan")
    public ResponseEntity<Map<String, Object>> generateCareerDevelopmentPlan(
            @Parameter(description = "User ID") @PathVariable Long userId) {
        
        Map<String, Object> plan = amazonCompetencyService.generateCareerDevelopmentPlan(userId);
        return ResponseEntity.ok(plan);
    }

    @Operation(summary = "Get Amazon Senior SDE candidates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Senior SDE candidates retrieved successfully")
    })
    @GetMapping("/senior-sde-candidates")
    public ResponseEntity<List<AmazonCompetencyProgression>> getSeniorSDECandidates() {
        List<AmazonCompetencyProgression> candidates = amazonCompetencyService.getSeniorSDECandidates();
        return ResponseEntity.ok(candidates);
    }

    @Operation(summary = "Get system-wide competency statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    })
    @GetMapping("/system-statistics")
    public ResponseEntity<Map<String, Object>> getSystemCompetencyStatistics() {
        Map<String, Object> stats = amazonCompetencyService.getSystemCompetencyStatistics();
        return ResponseEntity.ok(stats);
    }

    @Operation(summary = "Get Amazon levels information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Amazon levels information retrieved successfully")
    })
    @GetMapping("/amazon-levels")
    public ResponseEntity<Map<String, Object>> getAmazonLevels() {
        Map<String, Object> levels = Map.of(
            "L3", Map.of(
                "title", "Junior SDE",
                "description", "Entry-level software development engineer",
                "requirements", Map.of(
                    "interviewReadiness", 60,
                    "culturalFit", 60,
                    "experience", "0-2 years"
                )
            ),
            "L4", Map.of(
                "title", "SDE",
                "description", "Software development engineer",
                "requirements", Map.of(
                    "interviewReadiness", 70,
                    "culturalFit", 70,
                    "experience", "2-4 years"
                )
            ),
            "L5", Map.of(
                "title", "Senior SDE",
                "description", "Senior software development engineer",
                "requirements", Map.of(
                    "interviewReadiness", 80,
                    "culturalFit", 80,
                    "experience", "4-8 years"
                )
            ),
            "L6", Map.of(
                "title", "Principal SDE",
                "description", "Principal software development engineer",
                "requirements", Map.of(
                    "interviewReadiness", 90,
                    "culturalFit", 85,
                    "experience", "8+ years"
                )
            )
        );
        
        return ResponseEntity.ok(levels);
    }

    @Operation(summary = "Get Amazon Leadership Principles information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Leadership Principles information retrieved successfully")
    })
    @GetMapping("/leadership-principles")
    public ResponseEntity<Map<String, Object>> getLeadershipPrinciples() {
        Map<String, Object> principles = Map.of(
            "totalPrinciples", 16,
            "principles", Map.of(
                "CUSTOMER_OBSESSION", Map.of(
                    "name", "Customer Obsession",
                    "description", "Leaders start with the customer and work backwards"
                ),
                "OWNERSHIP", Map.of(
                    "name", "Ownership",
                    "description", "Leaders are owners. They think long term and don't sacrifice long-term value for short-term results"
                ),
                "INVENT_AND_SIMPLIFY", Map.of(
                    "name", "Invent and Simplify",
                    "description", "Leaders expect and require innovation and invention from their teams"
                ),
                "ARE_RIGHT_A_LOT", Map.of(
                    "name", "Are Right, A Lot",
                    "description", "Leaders are right a lot. They have strong judgment and good instincts"
                ),
                "LEARN_AND_BE_CURIOUS", Map.of(
                    "name", "Learn and Be Curious",
                    "description", "Leaders are never done learning and always seek to improve themselves"
                ),
                "HIRE_AND_DEVELOP_THE_BEST", Map.of(
                    "name", "Hire and Develop the Best",
                    "description", "Leaders raise the performance bar with every hire and promotion"
                )
                // Additional principles would be included in a real implementation
            ),
            "assessmentGuidelines", List.of(
                "Use STAR method (Situation, Task, Action, Result) for examples",
                "Provide specific, measurable examples",
                "Demonstrate impact and results",
                "Show progression and learning from experiences"
            )
        );
        
        return ResponseEntity.ok(principles);
    }

    @Operation(summary = "Get Amazon hiring bar information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Hiring bar information retrieved successfully")
    })
    @GetMapping("/hiring-bar")
    public ResponseEntity<Map<String, Object>> getAmazonHiringBar() {
        Map<String, Object> hiringBar = Map.of(
            "standards", Map.of(
                "exceedsHiringBar", Map.of(
                    "interviewReadiness", "≥85",
                    "culturalFit", "≥80",
                    "description", "Exceptional candidate, likely to succeed and raise the bar"
                ),
                "meetsHiringBar", Map.of(
                    "interviewReadiness", "≥75",
                    "culturalFit", "≥70",
                    "description", "Strong candidate, meets Amazon standards"
                ),
                "approachingHiringBar", Map.of(
                    "interviewReadiness", "≥60",
                    "culturalFit", "≥60",
                    "description", "Developing candidate, needs improvement"
                ),
                "belowHiringBar", Map.of(
                    "interviewReadiness", "<60",
                    "culturalFit", "<60",
                    "description", "Does not meet Amazon standards"
                )
            ),
            "assessmentCriteria", List.of(
                "Technical competency and problem-solving ability",
                "Leadership Principles demonstration",
                "Cultural fit and Amazon values alignment",
                "Communication and collaboration skills",
                "Growth potential and learning agility"
            ),
            "interviewProcess", List.of(
                "Technical coding rounds",
                "System design discussion",
                "Behavioral interviews (Leadership Principles)",
                "Bar raiser interview",
                "Hiring manager discussion"
            )
        );
        
        return ResponseEntity.ok(hiringBar);
    }
}