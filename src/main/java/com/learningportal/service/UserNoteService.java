package com.learningportal.service;

import com.learningportal.model.UserNote;
import com.learningportal.repository.UserNoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserNoteService {

    private final UserNoteRepository noteRepository;

    public UserNoteService(UserNoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public UserNote createNote(UserNote note) {
        // Sanitize HTML content
        if (note.getContent() != null) {
            note.setContent(sanitizeHtml(note.getContent()));
        }
        return noteRepository.save(note);
    }

    public Optional<UserNote> getNoteById(Long id) {
        return noteRepository.findById(id);
    }

    public List<UserNote> getUserNotes(Long userId) {
        return noteRepository.findByUserIdOrderByUpdatedAtDesc(userId);
    }

    public Page<UserNote> getUserNotes(Long userId, Pageable pageable) {
        return noteRepository.findByUserIdOrderByUpdatedAtDesc(userId, pageable);
    }

    public List<UserNote> getModuleNotes(Long moduleId, Long userId) {
        return noteRepository.findByModuleIdAndUserIdOrderByCreatedAtDesc(moduleId, userId);
    }

    public List<UserNote> getTopicNotes(Long topicId, Long userId) {
        return noteRepository.findByTopicIdAndUserIdOrderByCreatedAtDesc(topicId, userId);
    }

    public List<UserNote> getNotesByCategory(UserNote.Category category, Long userId) {
        return noteRepository.findByCategoryAndUserIdOrderByUpdatedAtDesc(category, userId);
    }

    public List<UserNote> getPublicNotes() {
        return noteRepository.findByIsPublicTrueOrderByUpdatedAtDesc();
    }

    public List<UserNote> searchUserNotes(Long userId, String search) {
        return noteRepository.searchUserNotes(userId, search);
    }

    public List<UserNote> searchPublicNotes(String search) {
        return noteRepository.searchPublicNotes(search);
    }

    public UserNote updateNote(Long id, UserNote noteUpdate) {
        return noteRepository.findById(id)
            .map(existingNote -> {
                existingNote.setTitle(noteUpdate.getTitle());
                existingNote.setContent(sanitizeHtml(noteUpdate.getContent()));
                existingNote.setCategory(noteUpdate.getCategory());
                existingNote.setIsPublic(noteUpdate.getIsPublic());
                existingNote.setModuleId(noteUpdate.getModuleId());
                existingNote.setTopicId(noteUpdate.getTopicId());
                return noteRepository.save(existingNote);
            })
            .orElseThrow(() -> new RuntimeException("Note not found with id: " + id));
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public long getUserNoteCount(Long userId) {
        return noteRepository.countByUserId(userId);
    }

    public long getModuleNoteCount(Long moduleId, Long userId) {
        return noteRepository.countByModuleIdAndUserId(moduleId, userId);
    }

    private String sanitizeHtml(String content) {
        if (content == null) return null;
        
        // Basic HTML sanitization - remove script tags and dangerous attributes
        return content
            .replaceAll("<script[^>]*>.*?</script>", "")
            .replaceAll("javascript:", "")
            .replaceAll("on\\w+\\s*=", "")
            .trim();
    }
}