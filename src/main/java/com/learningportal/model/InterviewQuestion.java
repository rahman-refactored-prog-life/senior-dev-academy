package com.learningportal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_questions")

@EntityListeners(AuditingEntityListener.class)
@Schema(description = "Interview question from major tech companies")
public class InterviewQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the interview question", example = "1")
    private Long id;

    @NotBlank(message = "Question text is required")
    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "The interview question text")
    private String question;

    @Lob
    @Column(columnDefinition = "TEXT")
    @Schema(description = "Detailed answer and explanation")
    private String answer;

    @NotNull(message = "Difficulty level is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Difficulty level of the question")
    private Difficulty difficulty;

    @NotBlank(message = "Company is required")
    @Column(nullable = false, length = 100)
    @Schema(description = "Company that asked this question", example = "Google")
    private String company;

    @NotBlank(message = "Topic is required")
    @Column(nullable = false, length = 200)
    @Schema(description = "Topic/subject area of the question", example = "Java")
    private String topic;

    @Column(length = 500)
    @Schema(description = "Additional tags for categorization", example = "algorithms,arrays,sorting")
    private String tags;

    @Column(name = "frequency_score")
    @Schema(description = "How frequently this question is asked (1-10)", example = "8")
    private Integer frequencyScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    @JsonBackReference
    @Schema(description = "The learning module this question belongs to")
    private LearningModule module;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    @Schema(description = "Last update timestamp")
    private LocalDateTime updatedAt;

    @Schema(description = "Difficulty levels for interview questions")
    public enum Difficulty {
        EASY("Easy"),
        MEDIUM("Medium"),
        HARD("Hard"),
        EXPERT("Expert");

        private final String displayName;

        Difficulty(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Helper methods
    @Schema(description = "Get the module ID this question belongs to")
    public Long getModuleId() {
        return module != null ? module.getId() : null;
    }

    @Schema(description = "Get the module name this question belongs to")
    public String getModuleName() {
        return module != null ? module.getName() : null;
    }

    @Schema(description = "Check if question has an answer")
    public boolean hasAnswer() {
        return answer != null && !answer.trim().isEmpty();
    }

    @Schema(description = "Get question length")
    public int getQuestionLength() {
        return question != null ? question.length() : 0;
    }

    @Schema(description = "Get answer length")
    public int getAnswerLength() {
        return answer != null ? answer.length() : 0;
    }

    @Schema(description = "Check if this is a high-frequency question")
    public boolean isHighFrequency() {
        return frequencyScore != null && frequencyScore >= 7;
    }

    // Standard Java getters and setters (replacing Lombok @Data)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    public Difficulty getDifficulty() { return difficulty; }
    public void setDifficulty(Difficulty difficulty) { this.difficulty = difficulty; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public Integer getFrequencyScore() { return frequencyScore; }
    public void setFrequencyScore(Integer frequencyScore) { this.frequencyScore = frequencyScore; }

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
        InterviewQuestion that = (InterviewQuestion) o;
        return Objects.equals(id, that.id) && Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, question);
    }

    @Override
    public String toString() {
        return "InterviewQuestion{" +
                "id=" + id +
                ", difficulty=" + difficulty +
                ", company='" + company + '\'' +
                ", topic='" + topic + '\'' +
                ", frequencyScore=" + frequencyScore +
                ", moduleId=" + getModuleId() +
                '}';
    }
}