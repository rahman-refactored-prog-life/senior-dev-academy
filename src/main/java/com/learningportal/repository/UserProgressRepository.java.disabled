package com.learningportal.repository;

import com.learningportal.model.UserProgress;
import com.learningportal.model.User;
import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProgressRepository extends JpaRepository<UserProgress, Long> {
    
    List<UserProgress> findByUser(User user);
    Optional<UserProgress> findByUserAndModule(User user, LearningModule module);
    Optional<UserProgress> findByUserAndTopic(User user, Topic topic);
    List<UserProgress> findByUserAndStatus(User user, UserProgress.ProgressStatus status);
    
    @Query("SELECT AVG(p.progressPercentage) FROM UserProgress p WHERE p.user = :user")
    Double getAverageProgressByUser(@Param("user") User user);
    
    @Query("SELECT SUM(p.timeSpentMinutes) FROM UserProgress p WHERE p.user = :user")
    Integer getTotalTimeSpentByUser(@Param("user") User user);
    
    @Query("SELECT p FROM UserProgress p WHERE p.user = :user AND p.module IS NOT NULL ORDER BY p.updatedAt DESC")
    List<UserProgress> findModuleProgressByUser(@Param("user") User user);
    
    @Query("SELECT p FROM UserProgress p WHERE p.user = :user AND p.topic IS NOT NULL ORDER BY p.updatedAt DESC")
    List<UserProgress> findTopicProgressByUser(@Param("user") User user);
}