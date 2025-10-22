package com.learningportal.controller;

import com.learningportal.model.VisualLearningComponent;
import com.learningportal.service.VisualLearningOptimizationService;
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

import java.util.List;
import java.util.Map;

/**
 * REST Controller for Visual Learning Optimization
 * 
 * Provides endpoints for managing visual learning components including
 * algorithm animations, infographics, mind maps, and Amazon architecture diagrams.
 */
@RestController
@RequestMapping("/api/visual-learning")
@Tag(name = "Visual Learning", description = "Visual learning optimization and component management")
public class VisualLearningController {

    @Autowired
    private VisualLearningOptimizationService visualLearningService;

    @Operation(summary = "Create a new visual learning component")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Component created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters")
    })
    @PostMapping("/components")
    public ResponseEntity<VisualLearningComponent> createVisualComponent(
            @Parameter(description = "Content ID") @RequestParam Long contentId,
            @Parameter(description = "Content type") @RequestParam String contentType,
            @Parameter(description = "Component type") @RequestParam VisualLearningComponent.VisualComponentType componentType,
            @Parameter(description = "Component title") @RequestParam String title,
            @Parameter(description = "Component description") @RequestParam(required = false) String description) {
        
        VisualLearningComponent component = visualLearningService.createVisualComponent(
            contentId, contentType, componentType, title, description);
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Get visual components for specific content")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Components retrieved successfully")
    })
    @GetMapping("/content/{contentId}/type/{contentType}")
    public ResponseEntity<List<VisualLearningComponent>> getVisualComponentsForContent(
            @Parameter(description = "Content ID") @PathVariable Long contentId,
            @Parameter(description = "Content type") @PathVariable String contentType) {
        
        List<VisualLearningComponent> components = visualLearningService.getVisualComponentsForContent(contentId, contentType);
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get beginner-friendly visual components")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Beginner-friendly components retrieved successfully")
    })
    @GetMapping("/beginner-friendly")
    public ResponseEntity<List<VisualLearningComponent>> getBeginnerFriendlyComponents() {
        List<VisualLearningComponent> components = visualLearningService.getBeginnerFriendlyComponents();
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get interactive algorithm animations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Algorithm animations retrieved successfully")
    })
    @GetMapping("/algorithm-animations")
    public ResponseEntity<List<VisualLearningComponent>> getAlgorithmAnimations() {
        List<VisualLearningComponent> animations = visualLearningService.getAlgorithmAnimations();
        return ResponseEntity.ok(animations);
    }

    @Operation(summary = "Get infographics with Amazon context")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Amazon context infographics retrieved successfully")
    })
    @GetMapping("/amazon-infographics")
    public ResponseEntity<List<VisualLearningComponent>> getAmazonContextInfographics() {
        List<VisualLearningComponent> infographics = visualLearningService.getAmazonContextInfographics();
        return ResponseEntity.ok(infographics);
    }

    @Operation(summary = "Get mind maps for knowledge hierarchy")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Knowledge hierarchy mind maps retrieved successfully")
    })
    @GetMapping("/knowledge-mind-maps")
    public ResponseEntity<List<VisualLearningComponent>> getKnowledgeHierarchyMindMaps() {
        List<VisualLearningComponent> mindMaps = visualLearningService.getKnowledgeHierarchyMindMaps();
        return ResponseEntity.ok(mindMaps);
    }

    @Operation(summary = "Get Amazon architecture diagrams")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Amazon architecture diagrams retrieved successfully")
    })
    @GetMapping("/amazon-architecture-diagrams")
    public ResponseEntity<List<VisualLearningComponent>> getAmazonArchitectureDiagrams() {
        List<VisualLearningComponent> diagrams = visualLearningService.getAmazonArchitectureDiagrams();
        return ResponseEntity.ok(diagrams);
    }

    @Operation(summary = "Add interactive elements to component")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Interactive elements added successfully"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @PutMapping("/components/{componentId}/interactive-elements")
    public ResponseEntity<VisualLearningComponent> addInteractiveElements(
            @Parameter(description = "Component ID") @PathVariable Long componentId,
            @Parameter(description = "Interactive configuration") @RequestBody Map<String, Object> interactiveConfig) {
        
        VisualLearningComponent component = visualLearningService.addInteractiveElements(componentId, interactiveConfig);
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Add animation configuration to component")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Animation configuration added successfully"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @PutMapping("/components/{componentId}/animation-config")
    public ResponseEntity<VisualLearningComponent> addAnimationConfig(
            @Parameter(description = "Component ID") @PathVariable Long componentId,
            @Parameter(description = "Animation configuration") @RequestBody Map<String, Object> animationConfig) {
        
        VisualLearningComponent component = visualLearningService.addAnimationConfig(componentId, animationConfig);
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Add Amazon context to component")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Amazon context added successfully"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @PutMapping("/components/{componentId}/amazon-context")
    public ResponseEntity<VisualLearningComponent> addAmazonContext(
            @Parameter(description = "Component ID") @PathVariable Long componentId,
            @Parameter(description = "Amazon context") @RequestBody Map<String, Object> amazonContext) {
        
        VisualLearningComponent component = visualLearningService.addAmazonContext(componentId, amazonContext);
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Record component view and update statistics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "View recorded successfully"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @PostMapping("/components/{componentId}/view")
    public ResponseEntity<Map<String, String>> recordComponentView(
            @Parameter(description = "Component ID") @PathVariable Long componentId,
            @Parameter(description = "View duration in seconds") @RequestParam int viewDurationSeconds) {
        
        visualLearningService.recordComponentView(componentId, viewDurationSeconds);
        
        Map<String, String> response = Map.of(
            "message", "View recorded successfully",
            "componentId", componentId.toString(),
            "viewDuration", viewDurationSeconds + " seconds"
        );
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get personalized visual learning recommendations")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully")
    })
    @GetMapping("/recommendations")
    public ResponseEntity<List<VisualLearningComponent>> getPersonalizedRecommendations(
            @Parameter(description = "User complexity level") @RequestParam VisualLearningComponent.ComplexityLevel userLevel,
            @Parameter(description = "Prefer interactive components") @RequestParam(defaultValue = "false") boolean preferInteractive,
            @Parameter(description = "Prefer animated components") @RequestParam(defaultValue = "false") boolean preferAnimated,
            @Parameter(description = "Need Amazon context") @RequestParam(defaultValue = "false") boolean needsAmazonContext) {
        
        List<VisualLearningComponent> recommendations = visualLearningService.getPersonalizedRecommendations(
            userLevel, preferInteractive, preferAnimated, needsAmazonContext);
        return ResponseEntity.ok(recommendations);
    }

    @Operation(summary = "Get visual learning analytics")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Analytics retrieved successfully")
    })
    @GetMapping("/analytics")
    public ResponseEntity<Map<String, Object>> getVisualLearningAnalytics() {
        Map<String, Object> analytics = visualLearningService.getVisualLearningAnalytics();
        return ResponseEntity.ok(analytics);
    }

    @Operation(summary = "Get most engaging visual components")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Engaging components retrieved successfully")
    })
    @GetMapping("/most-engaging")
    public ResponseEntity<List<VisualLearningComponent>> getMostEngagingComponents(
            @Parameter(description = "Number of components to retrieve") @RequestParam(defaultValue = "10") int limit) {
        
        List<VisualLearningComponent> components = visualLearningService.getMostEngagingComponents(limit);
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Get components needing optimization")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Components needing optimization retrieved successfully")
    })
    @GetMapping("/needs-optimization")
    public ResponseEntity<List<VisualLearningComponent>> getComponentsNeedingOptimization() {
        List<VisualLearningComponent> components = visualLearningService.getComponentsNeedingOptimization();
        return ResponseEntity.ok(components);
    }

    @Operation(summary = "Optimize component for better learning outcomes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Component optimized successfully"),
        @ApiResponse(responseCode = "404", description = "Component not found")
    })
    @PutMapping("/components/{componentId}/optimize")
    public ResponseEntity<VisualLearningComponent> optimizeComponent(
            @Parameter(description = "Component ID") @PathVariable Long componentId,
            @Parameter(description = "Optimization configuration") @RequestBody Map<String, Object> optimizationConfig) {
        
        VisualLearningComponent component = visualLearningService.optimizeComponent(componentId, optimizationConfig);
        return ResponseEntity.ok(component);
    }

    @Operation(summary = "Generate visual learning effectiveness report")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Effectiveness report generated successfully")
    })
    @GetMapping("/effectiveness-report")
    public ResponseEntity<Map<String, Object>> generateEffectivenessReport() {
        Map<String, Object> report = visualLearningService.generateEffectivenessReport();
        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Search visual components")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    })
    @GetMapping("/search")
    public ResponseEntity<Page<VisualLearningComponent>> searchComponents(
            @Parameter(description = "Search term") @RequestParam String searchTerm,
            Pageable pageable) {
        
        Page<VisualLearningComponent> results = visualLearningService.searchComponents(searchTerm, pageable);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Get visual component types information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Component types information retrieved successfully")
    })
    @GetMapping("/component-types")
    public ResponseEntity<Map<String, Object>> getComponentTypes() {
        Map<String, Object> types = Map.of(
            "ALGORITHM_ANIMATION", Map.of(
                "displayName", "Algorithm Animation",
                "description", "Interactive step-by-step algorithm visualization",
                "defaultComplexity", "INTERMEDIATE",
                "defaultCognitiveLoad", 6
            ),
            "INFOGRAPHIC", Map.of(
                "displayName", "Infographic",
                "description", "Complex concept summary with visual elements",
                "defaultComplexity", "BEGINNER",
                "defaultCognitiveLoad", 3
            ),
            "MIND_MAP", Map.of(
                "displayName", "Mind Map",
                "description", "Knowledge hierarchy and relationship visualization",
                "defaultComplexity", "INTERMEDIATE",
                "defaultCognitiveLoad", 5
            ),
            "ARCHITECTURE_DIAGRAM", Map.of(
                "displayName", "Architecture Diagram",
                "description", "System architecture and component relationships",
                "defaultComplexity", "ADVANCED",
                "defaultCognitiveLoad", 7
            ),
            "FLOWCHART", Map.of(
                "displayName", "Flowchart",
                "description", "Process flow and decision tree visualization",
                "defaultComplexity", "BEGINNER",
                "defaultCognitiveLoad", 4
            ),
            "DATA_VISUALIZATION", Map.of(
                "displayName", "Data Visualization",
                "description", "Charts, graphs, and data representation",
                "defaultComplexity", "INTERMEDIATE",
                "defaultCognitiveLoad", 5
            )
        );
        
        return ResponseEntity.ok(types);
    }

    @Operation(summary = "Get complexity levels information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Complexity levels information retrieved successfully")
    })
    @GetMapping("/complexity-levels")
    public ResponseEntity<Map<String, Object>> getComplexityLevels() {
        Map<String, Object> levels = Map.of(
            "BEGINNER", Map.of(
                "level", "Beginner",
                "description", "Simple, clear visuals for zero-experience learners",
                "cognitiveLoadRange", "1-4",
                "targetAudience", "Complete beginners, zero experience"
            ),
            "INTERMEDIATE", Map.of(
                "level", "Intermediate",
                "description", "Moderate complexity with additional details",
                "cognitiveLoadRange", "4-6",
                "targetAudience", "Some experience, building competency"
            ),
            "ADVANCED", Map.of(
                "level", "Advanced",
                "description", "Complex visuals with comprehensive information",
                "cognitiveLoadRange", "6-8",
                "targetAudience", "Experienced learners, advanced concepts"
            ),
            "EXPERT", Map.of(
                "level", "Expert",
                "description", "Highly detailed visuals for expert-level understanding",
                "cognitiveLoadRange", "8-10",
                "targetAudience", "Expert practitioners, comprehensive mastery"
            )
        );
        
        return ResponseEntity.ok(levels);
    }

    @Operation(summary = "Get visual learning best practices")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Best practices retrieved successfully")
    })
    @GetMapping("/best-practices")
    public ResponseEntity<Map<String, Object>> getVisualLearningBestPractices() {
        Map<String, Object> bestPractices = Map.of(
            "cognitiveLoadManagement", List.of(
                "Keep cognitive load below 6 for beginners",
                "Use progressive disclosure for complex information",
                "Provide clear visual hierarchy and organization",
                "Minimize extraneous visual elements"
            ),
            "interactivityGuidelines", List.of(
                "Make interactive elements clearly identifiable",
                "Provide immediate feedback for user actions",
                "Allow users to control pacing and exploration",
                "Include reset and replay functionality"
            ),
            "animationPrinciples", List.of(
                "Use animations to show processes and changes",
                "Keep animations smooth and purposeful",
                "Allow users to pause and replay animations",
                "Provide step-by-step controls for complex animations"
            ),
            "amazonContextIntegration", List.of(
                "Use real Amazon architecture examples",
                "Include Amazon scale considerations",
                "Reference Amazon Leadership Principles where relevant",
                "Provide interview preparation context"
            ),
            "accessibilityRequirements", List.of(
                "Provide alternative text for all visual elements",
                "Ensure keyboard navigation support",
                "Use sufficient color contrast ratios",
                "Support screen readers and assistive technologies"
            ),
            "mobileOptimization", List.of(
                "Design for touch interactions",
                "Optimize for smaller screen sizes",
                "Ensure readable text and clickable elements",
                "Test on various mobile devices"
            )
        );
        
        return ResponseEntity.ok(bestPractices);
    }
}