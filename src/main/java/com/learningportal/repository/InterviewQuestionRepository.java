package com.learningportal.repository;

import com.learningportal.model.InterviewQuestion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Tag(name = "Interview Question Repository", description = "Data access layer for interview questions")
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

    /**
     * Find questions by module ID
     */
    List<InterviewQuestion> findByModuleIdOrderByIdAsc(Long moduleId);

    /**
     * Find questions by module ID with pagination
     */
    Page<InterviewQuestion> findByModuleIdOrderByIdAsc(Long moduleId, Pageable pageable);

    /**
     * Find questions by difficulty level
     */
    Page<InterviewQuestion> findByDifficultyOrderByIdAsc(InterviewQuestion.Difficulty difficulty, Pageable pageable);

    /**
     * Find questions by company
     */
    Page<InterviewQuestion> findByCompanyIgnoreCaseOrderByIdAsc(String company, Pageable pageable);

    /**
     * Find questions by topic
     */
    Page<InterviewQuestion> findByTopicIgnoreCaseOrderByIdAsc(String topic, Pageable pageable);

    /**
     * Search questions by question text
     */
    @Query("SELECT q FROM InterviewQuestion q WHERE " +
           "LOWER(q.question) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "ORDER BY q.id ASC")
    Page<InterviewQuestion> searchByQuestion(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Search questions by question or answer text
     */
    @Query("SELECT q FROM InterviewQuestion q WHERE " +
           "LOWER(q.question) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(q.answer) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "ORDER BY q.id ASC")
    Page<InterviewQuestion> searchQuestions(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find questions by company and difficulty
     */
    Page<InterviewQuestion> findByCompanyIgnoreCaseAndDifficultyOrderByIdAsc(
        String company, 
        InterviewQuestion.Difficulty difficulty, 
        Pageable pageable
    );

    /**
     * Find questions by topic and difficulty
     */
    Page<InterviewQuestion> findByTopicIgnoreCaseAndDifficultyOrderByIdAsc(
        String topic, 
        InterviewQuestion.Difficulty difficulty, 
        Pageable pageable
    );

    /**
     * Find question with module information
     */
    @Query("SELECT q FROM InterviewQuestion q JOIN FETCH q.module WHERE q.id = :id")
    Optional<InterviewQuestion> findByIdWithModule(@Param("id") Long id);

    /**
     * Get questions statistics
     */
    @Query("SELECT " +
           "COUNT(q) as totalQuestions, " +
           "COUNT(CASE WHEN q.difficulty = 'EASY' THEN 1 END) as easyQuestions, " +
           "COUNT(CASE WHEN q.difficulty = 'MEDIUM' THEN 1 END) as mediumQuestions, " +
           "COUNT(CASE WHEN q.difficulty = 'HARD' THEN 1 END) as hardQuestions, " +
           "COUNT(CASE WHEN q.difficulty = 'EXPERT' THEN 1 END) as expertQuestions, " +
           "COUNT(CASE WHEN q.answer IS NOT NULL AND LENGTH(q.answer) > 0 THEN 1 END) as questionsWithAnswers " +
           "FROM InterviewQuestion q")
    Object[] getQuestionsStatistics();

    /**
     * Find questions without answers
     */
    @Query("SELECT q FROM InterviewQuestion q WHERE q.answer IS NULL OR LENGTH(TRIM(q.answer)) = 0 ORDER BY q.id ASC")
    List<InterviewQuestion> findQuestionsWithoutAnswers();

    /**
     * Find high-frequency questions
     */
    @Query("SELECT q FROM InterviewQuestion q WHERE q.frequencyScore >= :minScore ORDER BY q.frequencyScore DESC, q.id ASC")
    List<InterviewQuestion> findHighFrequencyQuestions(@Param("minScore") Integer minScore, Pageable pageable);

    /**
     * Get distinct companies
     */
    @Query("SELECT DISTINCT q.company FROM InterviewQuestion q ORDER BY q.company ASC")
    List<String> findDistinctCompanies();

    /**
     * Get distinct topics
     */
    @Query("SELECT DISTINCT q.topic FROM InterviewQuestion q ORDER BY q.topic ASC")
    List<String> findDistinctTopics();

    /**
     * Count questions by company
     */
    long countByCompanyIgnoreCase(String company);

    /**
     * Count questions by topic
     */
    long countByTopicIgnoreCase(String topic);

    /**
     * Count questions by difficulty
     */
    long countByDifficulty(InterviewQuestion.Difficulty difficulty);

    /**
     * Count questions by module
     */
    long countByModuleId(Long moduleId);

    /**
     * Find random questions by difficulty
     */
    @Query(value = "SELECT * FROM interview_questions WHERE difficulty = :difficulty ORDER BY RANDOM() LIMIT :limit", 
           nativeQuery = true)
    List<InterviewQuestion> findRandomQuestionsByDifficulty(
        @Param("difficulty") String difficulty, 
        @Param("limit") int limit
    );

    /**
     * Find questions by tags containing
     */
    @Query("SELECT q FROM InterviewQuestion q WHERE LOWER(q.tags) LIKE LOWER(CONCAT('%', :tag, '%')) ORDER BY q.id ASC")
    Page<InterviewQuestion> findByTagsContaining(@Param("tag") String tag, Pageable pageable);

    /**
     * Get questions by module category
     */
    @Query("SELECT q FROM InterviewQuestion q JOIN q.module m WHERE m.category = :category ORDER BY q.id ASC")
    List<InterviewQuestion> findByModuleCategory(@Param("category") com.learningportal.model.LearningModule.Category category);

    /**
     * Advanced search with multiple filters
     */
    @Query("SELECT q FROM InterviewQuestion q WHERE " +
           "(:company IS NULL OR LOWER(q.company) = LOWER(:company)) AND " +
           "(:topic IS NULL OR LOWER(q.topic) = LOWER(:topic)) AND " +
           "(:difficulty IS NULL OR q.difficulty = :difficulty) AND " +
           "(:searchTerm IS NULL OR LOWER(q.question) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY q.id ASC")
    Page<InterviewQuestion> findWithFilters(
        @Param("company") String company,
        @Param("topic") String topic,
        @Param("difficulty") InterviewQuestion.Difficulty difficulty,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );
}