package com.learningportal.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "learning_modules")

@EntityListeners(AuditingEntityListener.class)
@Schema(description = "Learning module containing topics and interview questions")
public class LearningModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the learning module", example = "1")
    private Long id;

    @NotBlank(message = "Module name is required")
    @Column(nullable = false, length = 200)
    @Schema(description = "Name of the learning module", example = "Java Fundamentals to Expert")
    private String name;

    @NotBlank(message = "Module description is required")
    @Column(nullable = false, length = 1000)
    @Schema(description = "Detailed description of the module", 
            example = "Complete Java mastery from basics to advanced concepts")
    private String description;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Category of the learning module")
    private Category category;

    @NotNull(message = "Difficulty level is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Difficulty level of the module")
    private DifficultyLevel difficultyLevel;

    @Positive(message = "Estimated hours must be positive")
    @Column(name = "estimated_hours")
    @Schema(description = "Estimated hours to complete the module", example = "40")
    private Integer estimatedHours;

    @Column(name = "sort_order")
    @Schema(description = "Sort order for displaying modules", example = "1")
    private Integer sortOrder;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "List of topics in this module")
    private List<Topic> topics = new ArrayList<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @Schema(description = "List of interview questions for this module")
    private List<InterviewQuestion> interviewQuestions = new ArrayList<>();

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    @Schema(description = "Learning module categories")
    public enum Category {
        PROGRAMMING_LANGUAGES("Programming Languages"),
        FRAMEWORKS("Frameworks"),
        DATABASES("Databases"),
        SYSTEM_DESIGN("System Design"),
        DATA_STRUCTURES("Data Structures"),
        ALGORITHMS("Algorithms"),
        MICROSERVICES("Microservices"),
        CLOUD_COMPUTING("Cloud Computing"),
        DEVOPS("DevOps"),
        SECURITY("Security"),
        TESTING("Testing"),
        FRONTEND("Frontend Development"),
        BACKEND("Backend Development"),
        MOBILE("Mobile Development"),
        MACHINE_LEARNING("Machine Learning"),
        INTERVIEW_PREP("Interview Preparation");

        private final String displayName;

        Category(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    @Schema(description = "Difficulty levels for learning modules")
    public enum DifficultyLevel {
        BEGINNER("Beginner"),
        INTERMEDIATE("Intermediate"),
        ADVANCED("Advanced"),
        EXPERT("Expert");

        private final String displayName;

        DifficultyLevel(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Helper methods
    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.setModule(this);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
        topic.setModule(null);
    }

    public void addInterviewQuestion(InterviewQuestion question) {
        interviewQuestions.add(question);
        question.setModule(this);
    }

    public void removeInterviewQuestion(InterviewQuestion question) {
        interviewQuestions.remove(question);
        question.setModule(null);
    }

    @Schema(description = "Get total number of topics in this module")
    public int getTopicCount() {
        return topics != null ? topics.size() : 0;
    }

    @Schema(description = "Get total number of interview questions in this module")
    public int getQuestionCount() {
        return interviewQuestions != null ? interviewQuestions.size() : 0;
    }

    // Standard Java getters and setters (replacing Lombok @Data)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public Integer getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(Integer estimatedHours) { this.estimatedHours = estimatedHours; }

    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }

    public List<Topic> getTopics() { return topics; }
    public void setTopics(List<Topic> topics) { this.topics = topics; }

    public List<InterviewQuestion> getInterviewQuestions() { return interviewQuestions; }
    public void setInterviewQuestions(List<InterviewQuestion> interviewQuestions) { this.interviewQuestions = interviewQuestions; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Standard equals and hashCode (excluding collections to avoid circular references)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningModule that = (LearningModule) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "LearningModule{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", difficultyLevel=" + difficultyLevel +
                ", estimatedHours=" + estimatedHours +
                ", topicCount=" + getTopicCount() +
                ", questionCount=" + getQuestionCount() +
                '}';
    }
}