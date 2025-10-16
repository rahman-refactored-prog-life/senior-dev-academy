package com.learningportal.repository;

import com.learningportal.model.Topic;
import com.learningportal.model.LearningModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    
    List<Topic> findByModuleAndActiveTrueOrderBySortOrderAsc(LearningModule module);
    List<Topic> findByTopicType(Topic.TopicType topicType);
    List<Topic> findByActiveTrue();
    
    @Query("SELECT t FROM Topic t WHERE t.module.id = :moduleId AND t.active = true ORDER BY t.sortOrder ASC")
    List<Topic> findActiveTopicsByModuleId(@Param("moduleId") Long moduleId);
    
    @Query("SELECT COUNT(t) FROM Topic t WHERE t.module = :module AND t.active = true")
    long countActiveTopicsByModule(@Param("module") LearningModule module);
}