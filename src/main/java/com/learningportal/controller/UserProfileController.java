package com.learningportal.controller;

import com.learningportal.model.User;
import com.learningportal.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "User Profiles", description = "API for managing user profiles and preferences")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserProfileController {

    private final UserRepository userRepository;

    public UserProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(
        summary = "Get user profile",
        description = "Retrieve user profile information by ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Update user profile",
        description = "Update user profile information"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Profile updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserProfile(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id,
            
            @Parameter(description = "Updated profile data", required = true)
            @Valid @RequestBody User profileUpdate) {
        
        return userRepository.findById(id)
            .map(existingUser -> {
                existingUser.setFullName(profileUpdate.getFullName());
                existingUser.setEmail(profileUpdate.getEmail());
                existingUser.setProfilePictureUrl(profileUpdate.getProfilePictureUrl());
                return ResponseEntity.ok(userRepository.save(existingUser));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Get user preferences",
        description = "Retrieve user preferences and settings"
    )
    @GetMapping("/{id}/preferences")
    public ResponseEntity<Map<String, String>> getUserPreferences(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        
        return userRepository.findById(id)
            .map(user -> ResponseEntity.ok(user.getPreferences()))
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Update user preferences",
        description = "Update user preferences and settings"
    )
    @PutMapping("/{id}/preferences")
    public ResponseEntity<Map<String, String>> updateUserPreferences(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id,
            
            @Parameter(description = "User preferences", required = true)
            @RequestBody Map<String, String> preferences) {
        
        return userRepository.findById(id)
            .map(user -> {
                user.setPreferences(preferences);
                User savedUser = userRepository.save(user);
                return ResponseEntity.ok(savedUser.getPreferences());
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Update last login",
        description = "Update user's last login timestamp"
    )
    @PostMapping("/{id}/login")
    public ResponseEntity<Void> updateLastLogin(
            @Parameter(description = "User ID", required = true, example = "1")
            @PathVariable Long id) {
        
        return userRepository.findById(id)
            .map(user -> {
                user.setLastLogin(LocalDateTime.now());
                userRepository.save(user);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Search users",
        description = "Search users by username, email, or full name"
    )
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(
            @Parameter(description = "Search query", required = true)
            @RequestParam String q) {
        
        List<User> users = userRepository.searchUsers(q);
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Get active users",
        description = "Get all active users"
    )
    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        List<User> users = userRepository.findByIsActiveTrueOrderByCreatedAtDesc();
        return ResponseEntity.ok(users);
    }

    @Operation(
        summary = "Get users by role",
        description = "Get users filtered by role"
    )
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(
            @Parameter(description = "User role", required = true)
            @PathVariable User.Role role) {
        
        List<User> users = userRepository.findByRoleOrderByCreatedAtDesc(role);
        return ResponseEntity.ok(users);
    }
}