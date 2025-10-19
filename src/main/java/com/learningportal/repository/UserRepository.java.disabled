package com.learningportal.repository;

import com.learningportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * User Repository Interface
 * 
 * Demonstrates Spring Data JPA repository patterns and custom queries
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Query methods using method naming conventions
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByActiveTrue();
    List<User> findByRole(User.Role role);
    
    // Custom JPQL queries
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.active = true")
    Optional<User> findActiveUserByEmail(@Param("email") String email);
    
    @Query("SELECT u FROM User u WHERE u.createdAt >= :startDate")
    List<User> findUsersCreatedAfter(@Param("startDate") LocalDateTime startDate);
    
    // Native SQL query example
    @Query(value = "SELECT COUNT(*) FROM users WHERE active = true", nativeQuery = true)
    long countActiveUsers();
    
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}