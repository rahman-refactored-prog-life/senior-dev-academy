package com.learningportal.controller;

import com.learningportal.model.LearningModule;
import com.learningportal.repository.LearningModuleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/modules")
@Tag(name = "Learning Modules", description = "API for managing learning modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LearningModuleController {

    private static final Logger log = LoggerFactory.getLogger(LearningModuleController.class);
    
    private final LearningModuleRepository moduleRepository;
    
    // Manual constructor injection (replacing @RequiredArgsConstructor)
    public LearningModuleController(LearningModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Operation(
        summary = "Get all learning modules",
        description = "Retrieve all learning modules with optional pagination and search"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved modules",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = LearningModule.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<Page<LearningModule>> getAllModules(
            @Parameter(description = "Page number (0-based)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            
            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") int size,
            
            @Parameter(description = "Search term for module name or description")
            @RequestParam(required = false) String search) {
        
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<LearningModule> modules;
            
            if (search != null && !search.trim().isEmpty()) {
                log.info("Searching modules with term: {}", search);
                modules = moduleRepository.searchModules(search.trim(), pageable);
            } else {
                log.info("Fetching all modules - page: {}, size: {}", page, size);
                modules = moduleRepository.findAll(pageable);
            }
            
            log.info("Found {} modules", modules.getTotalElements());
            return ResponseEntity.ok(modules);
            
        } catch (Exception e) {
            log.error("Error fetching modules", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get learning module by ID",
        description = "Retrieve a specific learning module with its topics and questions"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module found",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = LearningModule.class))),
        @ApiResponse(responseCode = "404", description = "Module not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LearningModule> getModuleById(
            @Parameter(description = "Module ID", required = true, example = "1")
            @PathVariable Long id,
            
            @Parameter(description = "Include topics in response")
            @RequestParam(defaultValue = "false") boolean includeTopics,
            
            @Parameter(description = "Include interview questions in response")
            @RequestParam(defaultValue = "false") boolean includeQuestions) {
        
        try {
            log.info("Fetching module with ID: {}", id);
            
            Optional<LearningModule> moduleOpt;
            
            if (includeTopics && includeQuestions) {
                moduleOpt = moduleRepository.findByIdWithTopics(id)
                    .map(module -> {
                        // Also load questions
                        return moduleRepository.findByIdWithQuestions(id).orElse(module);
                    });
            } else if (includeTopics) {
                moduleOpt = moduleRepository.findByIdWithTopics(id);
            } else if (includeQuestions) {
                moduleOpt = moduleRepository.findByIdWithQuestions(id);
            } else {
                moduleOpt = moduleRepository.findById(id);
            }
            
            if (moduleOpt.isPresent()) {
                log.info("Found module: {}", moduleOpt.get().getName());
                return ResponseEntity.ok(moduleOpt.get());
            } else {
                log.warn("Module not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) {
            log.error("Error fetching module with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get modules by category",
        description = "Retrieve learning modules filtered by category"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved modules by category"),
        @ApiResponse(responseCode = "400", description = "Invalid category"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/category/{category}")
    public ResponseEntity<List<LearningModule>> getModulesByCategory(
            @Parameter(description = "Module category", required = true)
            @PathVariable LearningModule.Category category) {
        
        try {
            log.info("Fetching modules by category: {}", category);
            List<LearningModule> modules = moduleRepository.findByCategoryOrderBySortOrderAsc(category);
            log.info("Found {} modules for category: {}", modules.size(), category);
            return ResponseEntity.ok(modules);
            
        } catch (Exception e) {
            log.error("Error fetching modules by category: {}", category, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get modules by difficulty level",
        description = "Retrieve learning modules filtered by difficulty level"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved modules by difficulty"),
        @ApiResponse(responseCode = "400", description = "Invalid difficulty level"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<LearningModule>> getModulesByDifficulty(
            @Parameter(description = "Difficulty level", required = true)
            @PathVariable LearningModule.DifficultyLevel difficulty) {
        
        try {
            log.info("Fetching modules by difficulty: {}", difficulty);
            List<LearningModule> modules = moduleRepository.findByDifficultyLevelOrderBySortOrderAsc(difficulty);
            log.info("Found {} modules for difficulty: {}", modules.size(), difficulty);
            return ResponseEntity.ok(modules);
            
        } catch (Exception e) {
            log.error("Error fetching modules by difficulty: {}", difficulty, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get modules statistics",
        description = "Retrieve comprehensive statistics about all learning modules"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getModulesStatistics() {
        try {
            log.info("Fetching modules statistics");
            
            Object[] stats = moduleRepository.getModulesStatistics();
            
            Map<String, Object> statistics = Map.of(
                "totalModules", stats[0] != null ? stats[0] : 0L,
                "totalTopics", stats[1] != null ? stats[1] : 0L,
                "totalQuestions", stats[2] != null ? stats[2] : 0L,
                "totalEstimatedHours", stats[3] != null ? stats[3] : 0L,
                "averageTopicsPerModule", stats[0] != null && (Long)stats[0] > 0 ? 
                    ((Long)stats[1]) / ((Long)stats[0]) : 0L,
                "averageQuestionsPerModule", stats[0] != null && (Long)stats[0] > 0 ? 
                    ((Long)stats[2]) / ((Long)stats[0]) : 0L
            );
            
            log.info("Statistics: {}", statistics);
            return ResponseEntity.ok(statistics);
            
        } catch (Exception e) {
            log.error("Error fetching modules statistics", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Get popular modules",
        description = "Retrieve modules with the most topics and questions"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved popular modules"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/popular")
    public ResponseEntity<List<LearningModule>> getPopularModules(
            @Parameter(description = "Maximum number of modules to return", example = "5")
            @RequestParam(defaultValue = "5") int limit) {
        
        try {
            log.info("Fetching top {} popular modules", limit);
            Pageable pageable = PageRequest.of(0, limit);
            List<LearningModule> modules = moduleRepository.findPopularModules(pageable);
            log.info("Found {} popular modules", modules.size());
            return ResponseEntity.ok(modules);
            
        } catch (Exception e) {
            log.error("Error fetching popular modules", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Create a new learning module",
        description = "Create a new learning module with the provided information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Module created successfully",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = LearningModule.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Module with same name already exists"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<LearningModule> createModule(
            @Parameter(description = "Module data", required = true)
            @Valid @RequestBody LearningModule module) {
        
        try {
            log.info("Creating new module: {}", module.getName());
            
            // Check if module with same name already exists
            if (moduleRepository.existsByNameIgnoreCase(module.getName())) {
                log.warn("Module with name '{}' already exists", module.getName());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
            
            LearningModule savedModule = moduleRepository.save(module);
            log.info("Created module with ID: {}", savedModule.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedModule);
            
        } catch (Exception e) {
            log.error("Error creating module", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Update a learning module",
        description = "Update an existing learning module with new information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Module updated successfully",
                    content = @Content(mediaType = "application/json", 
                                     schema = @Schema(implementation = LearningModule.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Module not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LearningModule> updateModule(
            @Parameter(description = "Module ID", required = true, example = "1")
            @PathVariable Long id,
            
            @Parameter(description = "Updated module data", required = true)
            @Valid @RequestBody LearningModule moduleUpdate) {
        
        try {
            log.info("Updating module with ID: {}", id);
            
            Optional<LearningModule> existingModuleOpt = moduleRepository.findById(id);
            if (existingModuleOpt.isEmpty()) {
                log.warn("Module not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            LearningModule existingModule = existingModuleOpt.get();
            
            // Update fields
            existingModule.setName(moduleUpdate.getName());
            existingModule.setDescription(moduleUpdate.getDescription());
            existingModule.setCategory(moduleUpdate.getCategory());
            existingModule.setDifficultyLevel(moduleUpdate.getDifficultyLevel());
            existingModule.setEstimatedHours(moduleUpdate.getEstimatedHours());
            existingModule.setSortOrder(moduleUpdate.getSortOrder());
            
            LearningModule savedModule = moduleRepository.save(existingModule);
            log.info("Updated module: {}", savedModule.getName());
            return ResponseEntity.ok(savedModule);
            
        } catch (Exception e) {
            log.error("Error updating module with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(
        summary = "Delete a learning module",
        description = "Delete a learning module and all its associated topics and questions"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Module deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Module not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModule(
            @Parameter(description = "Module ID", required = true, example = "1")
            @PathVariable Long id) {
        
        try {
            log.info("Deleting module with ID: {}", id);
            
            if (!moduleRepository.existsById(id)) {
                log.warn("Module not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }
            
            moduleRepository.deleteById(id);
            log.info("Deleted module with ID: {}", id);
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            log.error("Error deleting module with ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}