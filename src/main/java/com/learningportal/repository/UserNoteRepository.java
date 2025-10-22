package com.learningportal.repository;

import com.learningportal.model.UserNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, Long> {

    List<UserNote> findByUserIdOrderByUpdatedAtDesc(Long userId);
    
    Page<UserNote> findByUserIdOrderByUpdatedAtDesc(Long userId, Pageable pageable);
    
    List<UserNote> findByModuleIdAndUserIdOrderByCreatedAtDesc(Long moduleId, Long userId);
    
    List<UserNote> findByTopicIdAndUserIdOrderByCreatedAtDesc(Long topicId, Long userId);
    
    List<UserNote> findByCategoryAndUserIdOrderByUpdatedAtDesc(UserNote.Category category, Long userId);
    
    List<UserNote> findByIsPublicTrueOrderByUpdatedAtDesc();
    
    @Query("SELECT n FROM UserNote n WHERE n.userId = :userId AND " +
           "(LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(n.content) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<UserNote> searchUserNotes(@Param("userId") Long userId, @Param("search") String search);
    
    @Query("SELECT n FROM UserNote n WHERE n.isPublic = true AND " +
           "(LOWER(n.title) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(n.content) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<UserNote> searchPublicNotes(@Param("search") String search);
    
    long countByUserId(Long userId);
    
    long countByModuleIdAndUserId(Long moduleId, Long userId);
}