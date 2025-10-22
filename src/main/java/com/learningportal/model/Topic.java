package com.learningportal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.util.Objects;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics")

@EntityListeners(AuditingEntityListener.class)
@Schema(description = "Individual topic within a learning module")
public class Topic {

    @Schema(description = "Types of topic content")
    public enum TopicType {
        LEARNING_CONTENT("Learning Content"),
        INTERVIEW_QUESTION("Interview Question"),
        CODE_EXAMPLE("Code Example"),
        PRACTICE_EXERCISE("Practice Exercise");

        private final String displayName;

        TopicType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the topic", example = "1")
    private Long id;

    @NotBlank(message = "Topic title is required")
    @Column(nullable = false, length = 300)
    @Schema(description = "Title of the topic", example = "Java Basics: Variables and Data Types")
    private String title;

    @NotBlank(message = "Topic description is required")
    @Column(nullable = false, length = 1000)
    @Schema(description = "Brief description of the topic")
    private String description;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Schema(description = "Complete content of the topic including HTML, code examples, and explanations")
    private String content;

    @Positive(message = "Estimated minutes must be positive")
    @Column(name = "estimated_minutes")
    @Schema(description = "Estimated time to complete this topic in minutes", example = "45")
    private Integer estimatedMinutes;

    @Column(name = "sort_order")
    @Schema(description = "Sort order within the module", example = "1")
    private Integer sortOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "topic_type", nullable = false)
    @Schema(description = "Type of topic content")
    private TopicType topicType = TopicType.LEARNING_CONTENT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonBackReference
    @Schema(description = "The learning module this topic belongs to")
    private LearningModule module;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    // Helper methods
    @Schema(description = "Get the module ID this topic belongs to")
    public Long getModuleId() {
        return module != null ? module.getId() : null;
    }

    @Schema(description = "Get the module name this topic belongs to")
    public String getModuleName() {
        return module != null ? module.getName() : null;
    }

    @Schema(description = "Get estimated time in hours (rounded)")
    public double getEstimatedHours() {
        return estimatedMinutes != null ? Math.round((estimatedMinutes / 60.0) * 100.0) / 100.0 : 0.0;
    }

    @Schema(description = "Check if topic has content")
    public boolean hasContent() {
        return content != null && !content.trim().isEmpty();
    }

    @Schema(description = "Get content length")
    public int getContentLength() {
        return content != null ? content.length() : 0;
    }

    // Standard Java getters and setters (replacing Lombok @Data)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getName() { return title; } // Alias for title

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Integer getEstimatedMinutes() { return estimatedMinutes; }
    public void setEstimatedMinutes(Integer estimatedMinutes) { this.estimatedMinutes = estimatedMinutes; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public TopicType getTopicType() { return topicType; }
    public void setTopicType(TopicType topicType) { this.topicType = topicType; }

    public LearningModule getModule() { return module; }
    public void setModule(LearningModule module) { this.module = module; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Standard equals and hashCode (excluding module to avoid circular references)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) && Objects.equals(title, topic.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", estimatedMinutes=" + estimatedMinutes +
                ", sortOrder=" + sortOrder +
                ", moduleId=" + getModuleId() +
                '}';
    }
}