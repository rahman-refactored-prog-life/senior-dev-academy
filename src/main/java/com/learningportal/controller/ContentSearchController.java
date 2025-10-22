package com.learningportal.controller;

import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.model.InterviewQuestion;
import com.learningportal.repository.LearningModuleRepository;
import com.learningportal.repository.TopicRepository;
import com.learningportal.repository.InterviewQuestionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search")
@Tag(name = "Content Search", description = "Global search and content discovery APIs")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ContentSearchController {

    private final LearningModuleRepository moduleRepository;
    private final TopicRepository topicRepository;
    private final InterviewQuestionRepository questionRepository;

    public ContentSearchController(
            LearningModuleRepository moduleRepository,
            TopicRepository topicRepository,
            InterviewQuestionRepository questionRepository) {
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
        this.questionRepository = questionRepository;
    }

    @Operation(
        summary = "Global content search",
        description = "Search across all content types: modules, topics, and questions"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search results retrieved successfully")
    })
    @GetMapping("/global")
    public ResponseEntity<Map<String, Object>> globalSearch(
            @Parameter(description = "Search query", required = true)
            @RequestParam String q,
            
            @Parameter(description = "Maximum results per category", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        
        Map<String, Object> results = new HashMap<>();
        
        // Search modules
        List<LearningModule> modules = moduleRepository.searchModules(q, null)
            .getContent().stream()
            .limit(limit)
            .collect(Collectors.toList());
        
        // Search topics
        List<Topic> topics = topicRepository.searchTopics(q).stream()
            .limit(limit)
            .collect(Collectors.toList());
        
        // Search questions
        List<InterviewQuestion> questions = questionRepository.searchQuestions(q, Pageable.ofSize(limit)).getContent();
        
        results.put("modules", modules);
        results.put("topics", topics);
        results.put("questions", questions);
        results.put("totalResults", modules.size() + topics.size() + questions.size());
        
        return ResponseEntity.ok(results);
    }

    @Operation(
        summary = "Get trending content",
        description = "Get trending and popular content based on usage metrics"
    )
    @GetMapping("/trending")
    public ResponseEntity<Map<String, Object>> getTrendingContent(
            @Parameter(description = "Maximum results per category", example = "5")
            @RequestParam(defaultValue = "5") int limit) {
        
        Map<String, Object> trending = new HashMap<>();
        
        // Get popular modules (most topics/questions)
        List<LearningModule> popularModules = moduleRepository.findPopularModules(
            org.springframework.data.domain.PageRequest.of(0, limit));
        
        // Get recent topics
        List<Topic> recentTopics = topicRepository.findTop10ByOrderByIdDesc().stream()
            .limit(limit)
            .collect(Collectors.toList());
        
        // Get questions by difficulty distribution
        List<InterviewQuestion> featuredQuestions = questionRepository
            .findByDifficultyOrderByIdDesc(InterviewQuestion.Difficulty.MEDIUM).stream()
            .limit(limit)
            .collect(Collectors.toList());
        
        trending.put("popularModules", popularModules);
        trending.put("recentTopics", recentTopics);
        trending.put("featuredQuestions", featuredQuestions);
        
        return ResponseEntity.ok(trending);
    }

    @Operation(
        summary = "Get content recommendations",
        description = "Get personalized content recommendations based on user activity"
    )
    @GetMapping("/recommendations")
    public ResponseEntity<Map<String, Object>> getRecommendations(
            @Parameter(description = "User ID for personalized recommendations")
            @RequestParam(required = false) Long userId,
            
            @Parameter(description = "Category filter")
            @RequestParam(required = false) LearningModule.Category category,
            
            @Parameter(description = "Difficulty filter")
            @RequestParam(required = false) LearningModule.DifficultyLevel difficulty) {
        
        Map<String, Object> recommendations = new HashMap<>();
        
        // Basic recommendation logic
        List<LearningModule> recommendedModules;
        
        if (category != null) {
            recommendedModules = moduleRepository.findByCategoryOrderBySortOrderAsc(category);
        } else if (difficulty != null) {
            recommendedModules = moduleRepository.findByDifficultyLevelOrderBySortOrderAsc(difficulty);
        } else {
            // Default recommendations - popular content
            recommendedModules = moduleRepository.findPopularModules(
                org.springframework.data.domain.PageRequest.of(0, 10));
        }
        
        recommendations.put("modules", recommendedModules);
        recommendations.put("basedOn", userId != null ? "user_activity" : "popularity");
        
        return ResponseEntity.ok(recommendations);
    }

    @Operation(
        summary = "Get content by tags",
        description = "Search content by tags and categories"
    )
    @GetMapping("/tags")
    public ResponseEntity<Map<String, Object>> getContentByTags(
            @Parameter(description = "Comma-separated tags")
            @RequestParam String tags,
            
            @Parameter(description = "Content type filter")
            @RequestParam(required = false) String type) {
        
        Map<String, Object> results = new HashMap<>();
        String[] tagArray = tags.split(",");
        
        // Simple tag-based search (can be enhanced with actual tagging system)
        List<LearningModule> modules = new ArrayList<>();
        List<Topic> topics = new ArrayList<>();
        List<InterviewQuestion> questions = new ArrayList<>();
        
        for (String tag : tagArray) {
            String trimmedTag = tag.trim();
            
            if (type == null || "modules".equals(type)) {
                modules.addAll(moduleRepository.searchModules(trimmedTag, null).getContent());
            }
            
            if (type == null || "topics".equals(type)) {
                topics.addAll(topicRepository.searchTopics(trimmedTag));
            }
            
            if (type == null || "questions".equals(type)) {
                questions.addAll(questionRepository.searchQuestions(trimmedTag, Pageable.unpaged()).getContent());
            }
        }
        
        // Remove duplicates
        modules = modules.stream().distinct().collect(Collectors.toList());
        topics = topics.stream().distinct().collect(Collectors.toList());
        questions = questions.stream().distinct().collect(Collectors.toList());
        
        results.put("modules", modules);
        results.put("topics", topics);
        results.put("questions", questions);
        results.put("searchTags", Arrays.asList(tagArray));
        
        return ResponseEntity.ok(results);
    }

    @Operation(
        summary = "Get search suggestions",
        description = "Get search suggestions and autocomplete options"
    )
    @GetMapping("/suggestions")
    public ResponseEntity<List<String>> getSearchSuggestions(
            @Parameter(description = "Partial search query", required = true)
            @RequestParam String q,
            
            @Parameter(description = "Maximum suggestions", example = "10")
            @RequestParam(defaultValue = "10") int limit) {
        
        Set<String> suggestions = new HashSet<>();
        
        // Get module names that match
        moduleRepository.searchModules(q, null).getContent().stream()
            .map(LearningModule::getName)
            .forEach(suggestions::add);
        
        // Get topic names that match
        topicRepository.searchTopics(q).stream()
            .map(Topic::getName)
            .forEach(suggestions::add);
        
        // Convert to list and limit
        List<String> result = suggestions.stream()
            .limit(limit)
            .sorted()
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(result);
    }
}