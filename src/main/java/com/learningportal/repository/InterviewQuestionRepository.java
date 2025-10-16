package com.learningportal.repository;

import com.learningportal.model.InterviewQuestion;
import com.learningportal.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {
    
    List<InterviewQuestion> findByDifficulty(InterviewQuestion.Difficulty difficulty);
    List<InterviewQuestion> findByCategory(InterviewQuestion.QuestionCategory category);
    List<InterviewQuestion> findByCompany(InterviewQuestion.Company company);
    List<InterviewQuestion> findByTopic(Topic topic);
    List<InterviewQuestion> findByActiveTrue();
    
    @Query("SELECT q FROM InterviewQuestion q WHERE q.active = true AND q.difficulty = :difficulty ORDER BY q.frequencyScore DESC")
    List<InterviewQuestion> findByDifficultyOrderedByFrequency(@Param("difficulty") InterviewQuestion.Difficulty difficulty);
    
    @Query("SELECT q FROM InterviewQuestion q WHERE q.active = true AND q.frequencyScore >= :minScore")
    List<InterviewQuestion> findHighFrequencyQuestions(@Param("minScore") Integer minScore);
    
    @Query("SELECT COUNT(q) FROM InterviewQuestion q WHERE q.active = true AND q.difficulty = :difficulty")
    long countByDifficulty(@Param("difficulty") InterviewQuestion.Difficulty difficulty);
    
    @Query("SELECT q FROM InterviewQuestion q WHERE q.active = true AND q.category IN :categories")
    List<InterviewQuestion> findByCategories(@Param("categories") List<InterviewQuestion.QuestionCategory> categories);
}