package com.learningportal.service;

import com.learningportal.model.VisualLearningComponent;
import com.learningportal.repository.VisualLearningComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Service for Visual Learning Optimization
 * 
 * Implements interactive visualizations, algorithm animations, infographics,
 * mind maps, and Amazon architecture diagrams for optimal visual learning
 * with zero-experience learner support.
 */
@Service
@Transactional
public class VisualLearningOptimizationService {

    @Autowired
    private VisualLearningComponentRepository visualComponentRepository;

    /**
     * Create a new visual learning component
     */
    public VisualLearningComponent createVisualComponent(Long contentId, String contentType,
                                                       VisualLearningComponent.VisualComponentType componentType,
                                                       String title, String description) {
        VisualLearningComponent component = new VisualLearningComponent(contentId, contentType, componentType, title);
        component.setDescription(description);
        
        // Set default values based on component type
        setDefaultsForComponentType(component);
        
        return visualComponentRepository.save(component);
    }

    /**
     * Get visual components for specific content
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getVisualComponentsForContent(Long contentId, String contentType) {
        return visualComponentRepository.findByContentIdAndContentTypeAndIsActiveTrueOrderByCreatedAtDesc(
            contentId, contentType);
    }

    /**
     * Get beginner-friendly visual components
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getBeginnerFriendlyComponents() {
        return visualComponentRepository.findBeginnerFriendlyComponents();
    }

    /**
     * Get interactive algorithm animations
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getAlgorithmAnimations() {
        return visualComponentRepository.findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
            VisualLearningComponent.VisualComponentType.ALGORITHM_ANIMATION);
    }

    /**
     * Get infographics with Amazon context
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getAmazonContextInfographics() {
        List<VisualLearningComponent> infographics = visualComponentRepository
            .findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
                VisualLearningComponent.VisualComponentType.INFOGRAPHIC);
        
        return infographics.stream()
            .filter(VisualLearningComponent::hasAmazonContext)
            .collect(Collectors.toList());
    }

    /**
     * Get mind maps for knowledge hierarchy
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getKnowledgeHierarchyMindMaps() {
        return visualComponentRepository.findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
            VisualLearningComponent.VisualComponentType.MIND_MAP);
    }

    /**
     * Get Amazon architecture diagrams
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getAmazonArchitectureDiagrams() {
        List<VisualLearningComponent> diagrams = visualComponentRepository
            .findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(
                VisualLearningComponent.VisualComponentType.ARCHITECTURE_DIAGRAM);
        
        return diagrams.stream()
            .filter(VisualLearningComponent::hasAmazonContext)
            .collect(Collectors.toList());
    }

    /**
     * Update visual component with interactive elements
     */
    public VisualLearningComponent addInteractiveElements(Long componentId, 
                                                        Map<String, Object> interactiveConfig) {
        VisualLearningComponent component = visualComponentRepository.findById(componentId)
            .orElseThrow(() -> new RuntimeException("Visual component not found"));
        
        // Convert map to JSON string (in real implementation, use proper JSON serialization)
        component.setInteractiveElements(interactiveConfig.toString());
        
        // Increase effectiveness score for interactive components
        if (component.getEffectivenessScore() < 90) {
            component.setEffectivenessScore(Math.min(100, component.getEffectivenessScore() + 10));
        }
        
        return visualComponentRepository.save(component);
    }

    /**
     * Add animation configuration to component
     */
    public VisualLearningComponent addAnimationConfig(Long componentId, 
                                                    Map<String, Object> animationConfig) {
        VisualLearningComponent component = visualComponentRepository.findById(componentId)
            .orElseThrow(() -> new RuntimeException("Visual component not found"));
        
        component.setAnimationConfig(animationConfig.toString());
        
        // Increase effectiveness score for animated components
        if (component.getEffectivenessScore() < 85) {
            component.setEffectivenessScore(Math.min(100, component.getEffectivenessScore() + 15));
        }
        
        return visualComponentRepository.save(component);
    }

    /**
     * Add Amazon context to component
     */
    public VisualLearningComponent addAmazonContext(Long componentId, 
                                                  Map<String, Object> amazonContext) {
        VisualLearningComponent component = visualComponentRepository.findById(componentId)
            .orElseThrow(() -> new RuntimeException("Visual component not found"));
        
        component.setAmazonContext(amazonContext.toString());
        
        // Increase effectiveness score for Amazon context
        if (component.getEffectivenessScore() < 95) {
            component.setEffectivenessScore(Math.min(100, component.getEffectivenessScore() + 5));
        }
        
        return visualComponentRepository.save(component);
    }

    /**
     * Record component view and update statistics
     */
    public void recordComponentView(Long componentId, int viewDurationSeconds) {
        VisualLearningComponent component = visualComponentRepository.findById(componentId)
            .orElseThrow(() -> new RuntimeException("Visual component not found"));
        
        component.updateViewStatistics(viewDurationSeconds);
        visualComponentRepository.save(component);
    }

    /**
     * Get personalized visual learning recommendations
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getPersonalizedRecommendations(
            VisualLearningComponent.ComplexityLevel userLevel,
            boolean preferInteractive,
            boolean preferAnimated,
            boolean needsAmazonContext) {
        
        List<VisualLearningComponent> recommendations = new ArrayList<>();
        
        // Start with components matching user's complexity level
        List<VisualLearningComponent> levelComponents = visualComponentRepository
            .findByComplexityLevelAndIsActiveTrueOrderByEffectivenessScoreDesc(userLevel);
        
        // Filter based on preferences
        Stream<VisualLearningComponent> stream = levelComponents.stream();
        
        if (preferInteractive) {
            stream = stream.filter(VisualLearningComponent::isInteractive);
        }
        
        if (preferAnimated) {
            stream = stream.filter(VisualLearningComponent::isAnimated);
        }
        
        if (needsAmazonContext) {
            stream = stream.filter(VisualLearningComponent::hasAmazonContext);
        }
        
        recommendations.addAll(stream.limit(10).collect(Collectors.toList()));
        
        // If not enough recommendations, add highly effective components
        if (recommendations.size() < 5) {
            List<VisualLearningComponent> effective = visualComponentRepository
                .findHighlyEffectiveComponents(80);
            recommendations.addAll(effective.stream()
                .filter(c -> !recommendations.contains(c))
                .limit(5 - recommendations.size())
                .collect(Collectors.toList()));
        }
        
        return recommendations;
    }

    /**
     * Get visual learning analytics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getVisualLearningAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        
        // Basic statistics
        Object[] effectivenessStats = visualComponentRepository.getEffectivenessStatistics();
        Object[] cognitiveLoadStats = visualComponentRepository.getCognitiveLoadStatistics();
        Object[] viewStats = visualComponentRepository.getViewStatistics();
        
        if (effectivenessStats != null && effectivenessStats.length > 0) {
            analytics.put("totalComponents", effectivenessStats[0]);
            analytics.put("averageEffectiveness", effectivenessStats[1]);
            analytics.put("minEffectiveness", effectivenessStats[2]);
            analytics.put("maxEffectiveness", effectivenessStats[3]);
        }
        
        if (cognitiveLoadStats != null && cognitiveLoadStats.length > 0) {
            analytics.put("averageCognitiveLoad", cognitiveLoadStats[1]);
            analytics.put("minCognitiveLoad", cognitiveLoadStats[2]);
            analytics.put("maxCognitiveLoad", cognitiveLoadStats[3]);
        }
        
        if (viewStats != null && viewStats.length > 0) {
            analytics.put("totalViews", viewStats[1]);
            analytics.put("averageViewsPerComponent", viewStats[2]);
            analytics.put("averageViewDuration", viewStats[3]);
        }
        
        // Distribution statistics
        List<Object[]> typeDistribution = visualComponentRepository.getComponentTypeDistribution();
        analytics.put("componentTypeDistribution", typeDistribution.stream()
            .collect(Collectors.toMap(
                row -> row[0].toString(),
                row -> row[1]
            )));
        
        List<Object[]> complexityDistribution = visualComponentRepository.getComplexityLevelDistribution();
        analytics.put("complexityLevelDistribution", complexityDistribution.stream()
            .collect(Collectors.toMap(
                row -> row[0].toString(),
                row -> row[1]
            )));
        
        // Feature statistics
        analytics.put("interactiveComponentsCount", visualComponentRepository.countInteractiveComponents());
        analytics.put("animatedComponentsCount", visualComponentRepository.countAnimatedComponents());
        analytics.put("amazonContextComponentsCount", visualComponentRepository.countComponentsWithAmazonContext());
        analytics.put("mobileOptimizedCount", visualComponentRepository.countByMobileOptimizedTrueAndIsActiveTrue());
        analytics.put("accessibleComponentsCount", visualComponentRepository.countAccessibleComponents());
        
        return analytics;
    }

    /**
     * Get most engaging visual components
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getMostEngagingComponents(int limit) {
        return visualComponentRepository.findHighEngagementComponents(120) // 2+ minutes
            .stream()
            .limit(limit)
            .collect(Collectors.toList());
    }

    /**
     * Get components needing optimization
     */
    @Transactional(readOnly = true)
    public List<VisualLearningComponent> getComponentsNeedingOptimization() {
        return visualComponentRepository.findComponentsNeedingImprovement(60, 60); // Below 60% effectiveness or 1 minute duration
    }

    /**
     * Optimize component for better learning outcomes
     */
    public VisualLearningComponent optimizeComponent(Long componentId, 
                                                   Map<String, Object> optimizationConfig) {
        VisualLearningComponent component = visualComponentRepository.findById(componentId)
            .orElseThrow(() -> new RuntimeException("Visual component not found"));
        
        // Apply optimizations based on config
        if (optimizationConfig.containsKey("reduceCognitiveLoad")) {
            Boolean reduce = (Boolean) optimizationConfig.get("reduceCognitiveLoad");
            if (reduce && component.getCognitiveLoadLevel() > 3) {
                component.setCognitiveLoadLevel(Math.max(1, component.getCognitiveLoadLevel() - 2));
            }
        }
        
        if (optimizationConfig.containsKey("addInteractivity")) {
            Boolean addInteractivity = (Boolean) optimizationConfig.get("addInteractivity");
            if (addInteractivity && !component.isInteractive()) {
                Map<String, Object> interactiveElements = new HashMap<>();
                interactiveElements.put("clickable", true);
                interactiveElements.put("hover", true);
                interactiveElements.put("expandable", true);
                component.setInteractiveElements(interactiveElements.toString());
            }
        }
        
        if (optimizationConfig.containsKey("mobileOptimize")) {
            Boolean mobileOptimize = (Boolean) optimizationConfig.get("mobileOptimize");
            if (mobileOptimize) {
                component.setMobileOptimized(true);
            }
        }
        
        if (optimizationConfig.containsKey("addAccessibility")) {
            Boolean addAccessibility = (Boolean) optimizationConfig.get("addAccessibility");
            if (addAccessibility && !component.isAccessible()) {
                Map<String, Object> accessibilityFeatures = new HashMap<>();
                accessibilityFeatures.put("altText", true);
                accessibilityFeatures.put("screenReader", true);
                accessibilityFeatures.put("keyboardNavigation", true);
                component.setAccessibilityFeatures(accessibilityFeatures.toString());
            }
        }
        
        return visualComponentRepository.save(component);
    }

    /**
     * Generate visual learning effectiveness report
     */
    @Transactional(readOnly = true)
    public Map<String, Object> generateEffectivenessReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Top performing components
        List<VisualLearningComponent> topPerformers = visualComponentRepository.findTopPerformingComponents();
        report.put("topPerformingComponents", topPerformers.stream()
            .limit(10)
            .map(this::componentToSummary)
            .collect(Collectors.toList()));
        
        // Components needing improvement
        List<VisualLearningComponent> needingImprovement = getComponentsNeedingOptimization();
        report.put("componentsNeedingImprovement", needingImprovement.stream()
            .limit(10)
            .map(this::componentToSummary)
            .collect(Collectors.toList()));
        
        // Most viewed components
        List<VisualLearningComponent> mostViewed = visualComponentRepository
            .findMostViewedComponents(PageRequest.of(0, 10));
        report.put("mostViewedComponents", mostViewed.stream()
            .map(this::componentToSummary)
            .collect(Collectors.toList()));
        
        // Effectiveness by type
        Map<String, Double> effectivenessByType = new HashMap<>();
        for (VisualLearningComponent.VisualComponentType type : VisualLearningComponent.VisualComponentType.values()) {
            List<VisualLearningComponent> typeComponents = visualComponentRepository
                .findByComponentTypeAndIsActiveTrueOrderByEffectivenessScoreDesc(type);
            double avgEffectiveness = typeComponents.stream()
                .mapToInt(VisualLearningComponent::getEffectivenessScore)
                .average()
                .orElse(0.0);
            effectivenessByType.put(type.name(), avgEffectiveness);
        }
        report.put("effectivenessByType", effectivenessByType);
        
        // Recommendations for improvement
        List<String> recommendations = generateSystemRecommendations();
        report.put("systemRecommendations", recommendations);
        
        return report;
    }

    /**
     * Search visual components
     */
    @Transactional(readOnly = true)
    public Page<VisualLearningComponent> searchComponents(String searchTerm, Pageable pageable) {
        return visualComponentRepository.searchComponents(searchTerm, pageable);
    }

    // Private helper methods

    private void setDefaultsForComponentType(VisualLearningComponent component) {
        switch (component.getComponentType()) {
            case ALGORITHM_ANIMATION:
                component.setComplexityLevel(VisualLearningComponent.ComplexityLevel.INTERMEDIATE);
                component.setCognitiveLoadLevel(6);
                component.setEffectivenessScore(75);
                break;
            case INFOGRAPHIC:
                component.setComplexityLevel(VisualLearningComponent.ComplexityLevel.BEGINNER);
                component.setCognitiveLoadLevel(3);
                component.setEffectivenessScore(70);
                break;
            case MIND_MAP:
                component.setComplexityLevel(VisualLearningComponent.ComplexityLevel.INTERMEDIATE);
                component.setCognitiveLoadLevel(5);
                component.setEffectivenessScore(80);
                break;
            case ARCHITECTURE_DIAGRAM:
                component.setComplexityLevel(VisualLearningComponent.ComplexityLevel.ADVANCED);
                component.setCognitiveLoadLevel(7);
                component.setEffectivenessScore(85);
                break;
            case FLOWCHART:
                component.setComplexityLevel(VisualLearningComponent.ComplexityLevel.BEGINNER);
                component.setCognitiveLoadLevel(4);
                component.setEffectivenessScore(65);
                break;
            default:
                component.setComplexityLevel(VisualLearningComponent.ComplexityLevel.BEGINNER);
                component.setCognitiveLoadLevel(5);
                component.setEffectivenessScore(60);
        }
    }

    private Map<String, Object> componentToSummary(VisualLearningComponent component) {
        Map<String, Object> summary = new HashMap<>();
        summary.put("id", component.getId());
        summary.put("title", component.getTitle());
        summary.put("type", component.getComponentType());
        summary.put("effectivenessScore", component.getEffectivenessScore());
        summary.put("viewCount", component.getViewCount());
        summary.put("averageViewDuration", component.getAverageViewDuration());
        summary.put("cognitiveLoadLevel", component.getCognitiveLoadLevel());
        summary.put("isInteractive", component.isInteractive());
        summary.put("isAnimated", component.isAnimated());
        summary.put("hasAmazonContext", component.hasAmazonContext());
        return summary;
    }

    private List<String> generateSystemRecommendations() {
        List<String> recommendations = new ArrayList<>();
        
        // Check for low effectiveness components
        long lowEffectivenessCount = visualComponentRepository.findByEffectivenessScoreRange(0, 60).size();
        if (lowEffectivenessCount > 0) {
            recommendations.add("Optimize " + lowEffectivenessCount + " components with low effectiveness scores");
        }
        
        // Check for high cognitive load components
        long highCognitiveLoadCount = visualComponentRepository.findByCognitiveLoadRange(8, 10).size();
        if (highCognitiveLoadCount > 0) {
            recommendations.add("Reduce cognitive load for " + highCognitiveLoadCount + " complex components");
        }
        
        // Check for missing interactivity
        long totalComponents = visualComponentRepository.count();
        long interactiveComponents = visualComponentRepository.countInteractiveComponents();
        double interactivePercentage = (double) interactiveComponents / totalComponents * 100;
        if (interactivePercentage < 50) {
            recommendations.add("Increase interactivity - only " + String.format("%.1f", interactivePercentage) + "% of components are interactive");
        }
        
        // Check for Amazon context coverage
        long amazonContextComponents = visualComponentRepository.countComponentsWithAmazonContext();
        double amazonContextPercentage = (double) amazonContextComponents / totalComponents * 100;
        if (amazonContextPercentage < 70) {
            recommendations.add("Add Amazon context to more components - currently " + String.format("%.1f", amazonContextPercentage) + "% have Amazon context");
        }
        
        // Check for mobile optimization
        long mobileOptimizedComponents = visualComponentRepository.countByMobileOptimizedTrueAndIsActiveTrue();
        double mobileOptimizedPercentage = (double) mobileOptimizedComponents / totalComponents * 100;
        if (mobileOptimizedPercentage < 80) {
            recommendations.add("Optimize more components for mobile - currently " + String.format("%.1f", mobileOptimizedPercentage) + "% are mobile-optimized");
        }
        
        return recommendations;
    }
}