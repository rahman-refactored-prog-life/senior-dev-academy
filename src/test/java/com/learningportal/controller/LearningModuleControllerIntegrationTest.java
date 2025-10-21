package com.learningportal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learningportal.model.LearningModule;
import com.learningportal.repository.LearningModuleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for LearningModuleController
 * Tests complete REST endpoint functionality with PostgreSQL database
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
@Transactional
class LearningModuleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LearningModuleRepository moduleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private LearningModule testModule;

    @BeforeEach
    void setUp() {
        moduleRepository.deleteAll();
        
        testModule = new LearningModule();
        testModule.setName("Integration Test Module");
        testModule.setDescription("Test Description for Integration");
        testModule.setCategory(LearningModule.Category.PROGRAMMING_LANGUAGES);
        testModule.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        testModule.setEstimatedHours(50);
        testModule.setSortOrder(1);
    }

    @Test
    void getAllModules_ShouldReturnPagedModules() throws Exception {
        // Arrange
        moduleRepository.save(testModule);

        // Act & Assert
        mockMvc.perform(get("/modules")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Integration Test Module")))
                .andExpect(jsonPath("$.content[0].category", is("PROGRAMMING_LANGUAGES")))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.totalPages", is(1)));
    }

    @Test
    void getModuleById_WhenModuleExists_ShouldReturnModule() throws Exception {
        // Arrange
        LearningModule savedModule = moduleRepository.save(testModule);

        // Act & Assert
        mockMvc.perform(get("/modules/{id}", savedModule.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Integration Test Module")))
                .andExpect(jsonPath("$.description", is("Test Description for Integration")))
                .andExpect(jsonPath("$.estimatedHours", is(50)));
    }

    @Test
    void getModuleById_WhenModuleNotExists_ShouldReturn404() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/modules/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")));
    }

    @Test
    void createModule_WithValidData_ShouldCreateModule() throws Exception {
        // Arrange
        String moduleJson = objectMapper.writeValueAsString(testModule);

        // Act & Assert
        mockMvc.perform(post("/modules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Integration Test Module")))
                .andExpect(jsonPath("$.id", notNullValue()));

        // Verify in database
        assert moduleRepository.count() == 1;
    }

    @Test
    void createModule_WithInvalidData_ShouldReturn400() throws Exception {
        // Arrange - module with missing required fields
        LearningModule invalidModule = new LearningModule();
        invalidModule.setDescription("Only description, missing name");
        String moduleJson = objectMapper.writeValueAsString(invalidModule);

        // Act & Assert
        mockMvc.perform(post("/modules")
                .contentType(MediaType.APPLICATION_JSON)
                .content(moduleJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.error", is("Validation Failed")));
    }

    @Test
    void updateModule_WhenModuleExists_ShouldUpdateModule() throws Exception {
        // Arrange
        LearningModule savedModule = moduleRepository.save(testModule);
        
        LearningModule updateData = new LearningModule();
        updateData.setName("Updated Module Name");
        updateData.setDescription("Updated Description");
        updateData.setCategory(LearningModule.Category.FRONTEND);
        updateData.setDifficultyLevel(LearningModule.DifficultyLevel.ADVANCED);
        updateData.setEstimatedHours(75);
        
        String updateJson = objectMapper.writeValueAsString(updateData);

        // Act & Assert
        mockMvc.perform(put("/modules/{id}", savedModule.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is("Updated Module Name")))
                .andExpect(jsonPath("$.category", is("FRONTEND")))
                .andExpect(jsonPath("$.estimatedHours", is(75)));
    }

    @Test
    void deleteModule_WhenModuleExists_ShouldDeleteModule() throws Exception {
        // Arrange
        LearningModule savedModule = moduleRepository.save(testModule);

        // Act & Assert
        mockMvc.perform(delete("/modules/{id}", savedModule.getId()))
                .andExpect(status().isNoContent());

        // Verify deletion
        assert moduleRepository.count() == 0;
    }

    @Test
    void deleteModule_WhenModuleNotExists_ShouldReturn404() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/modules/{id}", 999L))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is(404)));
    }

    @Test
    void searchModules_ByCategory_ShouldReturnFilteredResults() throws Exception {
        // Arrange
        LearningModule module1 = new LearningModule();
        module1.setName("Java Module");
        module1.setCategory(LearningModule.Category.PROGRAMMING_LANGUAGES);
        module1.setDifficultyLevel(LearningModule.DifficultyLevel.BEGINNER);
        module1.setEstimatedHours(40);
        
        LearningModule module2 = new LearningModule();
        module2.setName("React Module");
        module2.setCategory(LearningModule.Category.FRONTEND);
        module2.setDifficultyLevel(LearningModule.DifficultyLevel.INTERMEDIATE);
        module2.setEstimatedHours(60);
        
        moduleRepository.save(module1);
        moduleRepository.save(module2);

        // Act & Assert
        mockMvc.perform(get("/modules")
                .param("category", "PROGRAMMING_LANGUAGES"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].name", is("Java Module")));
    }
}