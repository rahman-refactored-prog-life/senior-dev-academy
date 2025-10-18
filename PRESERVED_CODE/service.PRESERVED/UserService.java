package com.learningportal.service;

import com.learningportal.model.User;
import com.learningportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User Service - Business logic for user management
 * 
 * This service demonstrates:
 * - Service layer pattern with business logic separation
 * - Transaction management with @Transactional
 * - Dependency injection with constructor injection
 * - Exception handling and validation
 * - Security best practices (password encoding)
 * - Logging for monitoring and debugging
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Create a new user with encrypted password
     */
    public User createUser(String username, String email, String password, String firstName, String lastName) {
        log.info("Creating new user with username: {}", username);
        
        // Validate input
        validateUserInput(username, email, password);
        
        // Check if user already exists
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists: " + username);
        }
        
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        // Create new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRole(User.Role.USER);
        user.setActive(true);
        
        User savedUser = userRepository.save(user);
        log.info("Successfully created user with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    /**
     * Find user by username
     */
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        log.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username);
    }
    
    /**
     * Find user by email
     */
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }
    
    /**
     * Find user by ID
     */
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        log.debug("Finding user by ID: {}", id);
        return userRepository.findById(id);
    }
    
    /**
     * Get all active users
     */
    @Transactional(readOnly = true)
    public List<User> findAllActiveUsers() {
        log.debug("Finding all active users");
        return userRepository.findByActiveTrue();
    }
    
    /**
     * Update user profile
     */
    public User updateUserProfile(Long userId, String firstName, String lastName, String email) {
        log.info("Updating profile for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        // Check if email is being changed and if it's already taken
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }
        
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        
        User updatedUser = userRepository.save(user);
        log.info("Successfully updated profile for user ID: {}", userId);
        
        return updatedUser;
    }
    
    /**
     * Change user password
     */
    public void changePassword(Long userId, String currentPassword, String newPassword) {
        log.info("Changing password for user ID: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }
        
        // Validate new password
        validatePassword(newPassword);
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        log.info("Successfully changed password for user ID: {}", userId);
    }
    
    /**
     * Deactivate user account
     */
    public void deactivateUser(Long userId) {
        log.info("Deactivating user ID: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        user.setActive(false);
        userRepository.save(user);
        
        log.info("Successfully deactivated user ID: {}", userId);
    }
    
    /**
     * Activate user account
     */
    public void activateUser(Long userId) {
        log.info("Activating user ID: {}", userId);
        
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
        
        user.setActive(true);
        userRepository.save(user);
        
        log.info("Successfully activated user ID: {}", userId);
    }
    
    /**
     * Get users created after a specific date
     */
    @Transactional(readOnly = true)
    public List<User> findUsersCreatedAfter(LocalDateTime date) {
        log.debug("Finding users created after: {}", date);
        return userRepository.findUsersCreatedAfter(date);
    }
    
    /**
     * Get count of active users
     */
    @Transactional(readOnly = true)
    public long getActiveUserCount() {
        long count = userRepository.countActiveUsers();
        log.debug("Active user count: {}", count);
        return count;
    }
    
    /**
     * Authenticate user credentials
     */
    @Transactional(readOnly = true)
    public boolean authenticateUser(String username, String password) {
        log.debug("Authenticating user: {}", username);
        
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            log.warn("Authentication failed: User not found - {}", username);
            return false;
        }
        
        User user = userOpt.get();
        if (!user.getActive()) {
            log.warn("Authentication failed: User account inactive - {}", username);
            return false;
        }
        
        boolean matches = passwordEncoder.matches(password, user.getPassword());
        if (matches) {
            log.info("Authentication successful for user: {}", username);
        } else {
            log.warn("Authentication failed: Invalid password for user - {}", username);
        }
        
        return matches;
    }
    
    /**
     * Private helper methods for validation
     */
    private void validateUserInput(String username, String email, String password) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        
        if (username.length() < 3 || username.length() > 50) {
            throw new IllegalArgumentException("Username must be between 3 and 50 characters");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        validatePassword(password);
    }
    
    private void validatePassword(String password) {
        if (password == null || password.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }
        
        // Add more password complexity rules as needed
        if (!password.matches(".*[A-Za-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one letter");
        }
    }
}