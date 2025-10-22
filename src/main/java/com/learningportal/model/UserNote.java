package com.learningportal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * UserNote entity for note-taking system
 */
@Entity
@Table(name = "user_notes")
@Schema(description = "User note for learning content")
public class UserNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier", example = "1")
    private Long id;

    @NotBlank
    @Size(max = 200)
    @Column(nullable = false)
    @Schema(description = "Note title", example = "Java Collections Summary")
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Schema(description = "Note content in HTML format")
    private String content;

    @Column(name = "user_id")
    @Schema(description = "User ID who created the note", example = "1")
    private Long userId;

    @Column(name = "module_id")
    @Schema(description = "Associated learning module ID", example = "1")
    private Long moduleId;

    @Column(name = "topic_id")
    @Schema(description = "Associated topic ID", example = "1")
    private Long topicId;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Note category")
    private Category category = Category.GENERAL;

    @Column(name = "is_public")
    @Schema(description = "Whether note is publicly shared", example = "false")
    private Boolean isPublic = false;

    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    public enum Category {
        GENERAL, SUMMARY, QUESTION, CODE_SNIPPET, IMPORTANT, REVIEW
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public UserNote() {}

    public UserNote(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }

    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public Boolean getIsPublic() { return isPublic; }
    public void setIsPublic(Boolean isPublic) { this.isPublic = isPublic; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}