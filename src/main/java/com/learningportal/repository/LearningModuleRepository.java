package com.learningportal.repository;

import com.learningportal.model.LearningModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningModuleRepository extends JpaRepository<LearningModule, Long> {
    
    List<LearningModule> findByActiveTrue();
    List<LearningModule> findByCategory(LearningModule.Category category);
    List<LearningModule> findByDifficultyLevel(LearningModule.DifficultyLevel difficultyLevel);
    List<LearningModule> findByActiveTrueOrderBySortOrderAsc();
    
    @Query("SELECT m FROM LearningModule m WHERE m.active = true AND m.category = :category ORDER BY m.sortOrder ASC")
    List<LearningModule> findActiveByCategoryOrdered(@Param("category") LearningModule.Category category);
    
    @Query("SELECT SUM(m.estimatedHours) FROM LearningModule m WHERE m.active = true")
    Integer getTotalEstimatedHours();
}