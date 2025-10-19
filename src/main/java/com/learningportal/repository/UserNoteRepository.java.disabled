package com.learningportal.repository;

import com.learningportal.model.UserNote;
import com.learningportal.model.User;
import com.learningportal.model.LearningModule;
import com.learningportal.model.Topic;
import com.learningportal.model.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserNoteRepository extends JpaRepository<UserNote, Long> {
    
    List<UserNote> findByUser(User user);
    List<UserNote> findByUserAndCategory(User user, UserNote.NoteCategory category);
    List<UserNote> findByUserAndPinnedTrue(User user);
    List<UserNote> findByUserAndFavoriteTrue(User user);
    List<UserNote> findByUserAndModule(User user, LearningModule module);
    List<UserNote> findByUserAndTopic(User user, Topic topic);
    List<UserNote> findByUserAndQuestion(User user, InterviewQuestion question);
    
    @Query("SELECT n FROM UserNote n WHERE n.user = :user ORDER BY n.pinned DESC, n.updatedAt DESC")
    List<UserNote> findByUserOrderedByPinnedAndDate(@Param("user") User user);
    
    @Query("SELECT n FROM UserNote n WHERE n.user = :user AND n.reminderDate <= :now AND n.reminderSent = false")
    List<UserNote> findDueReminders(@Param("user") User user, @Param("now") LocalDateTime now);
    
    @Query("SELECT n FROM UserNote n WHERE n.user = :user AND (n.title LIKE %:searchTerm% OR n.content LIKE %:searchTerm%)")
    List<UserNote> searchUserNotes(@Param("user") User user, @Param("searchTerm") String searchTerm);
}