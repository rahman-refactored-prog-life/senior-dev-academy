package com.learningportal.controller;

import com.learningportal.model.User;
import com.learningportal.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User REST Controller - API endpoints for user management
 * 
 * This controller demonstrates:
 * - RESTful API design principles
 * - Proper HTTP status codes and response handling
 * - Request/Response DTOs for API contracts
 * - Exception handling and error responses
 * - CORS configuration for frontend integration
 * - API documentation with clear endpoint descriptions
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"}) // React dev servers
public class UserController {
    
    private final UserService userService;
    
    /**
     * Get all active users
     * GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllActiveUsers() {
        log.info("GET /api/users - Fetching all active users");
        
        List<User> users = userService.findAllActiveUsers();
        return ResponseEntity.ok(users);
    }
    
    /**
     * Get user by ID
     * GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("GET /api/users/{} - Fetching user by ID", id);
        
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get user by username
     * GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        log.info("GET /api/users/username/{} - Fetching user by username", username);
        
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Create new user
     * POST /api/users
     */
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("POST /api/users - Creating new user with username: {}", request.getUsername());
        
        try {
            User user = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getFirstName(),
                request.getLastName()
            );
            
            ApiResponse<User> response = new ApiResponse<>(
                true,
                "User created successfully",
                user
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to create user: {}", e.getMessage());
            
            ApiResponse<User> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Update user profile
     * PUT /api/users/{id}/profile
     */
    @PutMapping("/{id}/profile")
    public ResponseEntity<ApiResponse<User>> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfileRequest request) {
        
        log.info("PUT /api/users/{}/profile - Updating user profile", id);
        
        try {
            User user = userService.updateUserProfile(
                id,
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
            );
            
            ApiResponse<User> response = new ApiResponse<>(
                true,
                "Profile updated successfully",
                user
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to update user profile: {}", e.getMessage());
            
            ApiResponse<User> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Change user password
     * PUT /api/users/{id}/password
     */
    @PutMapping("/{id}/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request) {
        
        log.info("PUT /api/users/{}/password - Changing user password", id);
        
        try {
            userService.changePassword(id, request.getCurrentPassword(), request.getNewPassword());
            
            ApiResponse<Void> response = new ApiResponse<>(
                true,
                "Password changed successfully",
                null
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to change password: {}", e.getMessage());
            
            ApiResponse<Void> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Deactivate user
     * DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deactivateUser(@PathVariable Long id) {
        log.info("DELETE /api/users/{} - Deactivating user", id);
        
        try {
            userService.deactivateUser(id);
            
            ApiResponse<Void> response = new ApiResponse<>(
                true,
                "User deactivated successfully",
                null
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deactivate user: {}", e.getMessage());
            
            ApiResponse<Void> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Activate user
     * PUT /api/users/{id}/activate
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<Void>> activateUser(@PathVariable Long id) {
        log.info("PUT /api/users/{}/activate - Activating user", id);
        
        try {
            userService.activateUser(id);
            
            ApiResponse<Void> response = new ApiResponse<>(
                true,
                "User activated successfully",
                null
            );
            
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Failed to activate user: {}", e.getMessage());
            
            ApiResponse<Void> response = new ApiResponse<>(
                false,
                e.getMessage(),
                null
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Get user statistics
     * GET /api/users/stats
     */
    @GetMapping("/stats")
    public ResponseEntity<UserStatsResponse> getUserStats() {
        log.info("GET /api/users/stats - Fetching user statistics");
        
        long activeUserCount = userService.getActiveUserCount();
        List<User> recentUsers = userService.findUsersCreatedAfter(LocalDateTime.now().minusDays(30));
        
        UserStatsResponse stats = new UserStatsResponse(
            activeUserCount,
            recentUsers.size()
        );
        
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Authenticate user
     * POST /api/users/authenticate
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<AuthenticationResponse>> authenticateUser(
            @Valid @RequestBody AuthenticationRequest request) {
        
        log.info("POST /api/users/authenticate - Authenticating user: {}", request.getUsername());
        
        boolean isAuthenticated = userService.authenticateUser(request.getUsername(), request.getPassword());
        
        if (isAuthenticated) {
            Optional<User> userOpt = userService.findByUsername(request.getUsername());
            
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                AuthenticationResponse authResponse = new AuthenticationResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getFullName(),
                    user.getRole().name()
                );
                
                ApiResponse<AuthenticationResponse> response = new ApiResponse<>(
                    true,
                    "Authentication successful",
                    authResponse
                );
                
                return ResponseEntity.ok(response);
            }
        }
        
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>(
            false,
            "Invalid username or password",
            null
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
    
    // DTO Classes for API contracts
    
    public static class CreateUserRequest {
        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        
        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
    }
    
    public static class UpdateProfileRequest {
        private String firstName;
        private String lastName;
        private String email;
        
        // Getters and setters
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
    
    public static class ChangePasswordRequest {
        private String currentPassword;
        private String newPassword;
        
        // Getters and setters
        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
    
    public static class AuthenticationRequest {
        private String username;
        private String password;
        
        // Getters and setters
        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class AuthenticationResponse {
        private Long id;
        private String username;
        private String email;
        private String fullName;
        private String role;
        
        public AuthenticationResponse(Long id, String username, String email, String fullName, String role) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.fullName = fullName;
            this.role = role;
        }
        
        // Getters
        public Long getId() { return id; }
        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getFullName() { return fullName; }
        public String getRole() { return role; }
    }
    
    public static class UserStatsResponse {
        private long totalActiveUsers;
        private long newUsersThisMonth;
        
        public UserStatsResponse(long totalActiveUsers, long newUsersThisMonth) {
            this.totalActiveUsers = totalActiveUsers;
            this.newUsersThisMonth = newUsersThisMonth;
        }
        
        // Getters
        public long getTotalActiveUsers() { return totalActiveUsers; }
        public long getNewUsersThisMonth() { return newUsersThisMonth; }
    }
    
    public static class ApiResponse<T> {
        private boolean success;
        private String message;
        private T data;
        
        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }
        
        // Getters
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public T getData() { return data; }
    }
}