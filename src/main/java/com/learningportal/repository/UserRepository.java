package com.learningportal.repository;

import com.learningportal.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    List<User> findByIsActiveTrueOrderByCreatedAtDesc();
    
    List<User> findByRoleOrderByCreatedAtDesc(User.Role role);
    
    @Query("SELECT u FROM User u WHERE u.lastLogin >= :since ORDER BY u.lastLogin DESC")
    List<User> findActiveUsersSince(@Param("since") LocalDateTime since);
    
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<User> searchUsers(@Param("search") String search);
    
    long countByIsActiveTrue();
    
    long countByRole(User.Role role);
}