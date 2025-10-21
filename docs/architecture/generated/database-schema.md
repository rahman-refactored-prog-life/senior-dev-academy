# Database Schema Documentation

*Generated on Tue Oct 21 16:16:48 CDT 2025*

## JPA Entities

### InterviewQuestion

**File:** ``

**Table:** 16:@Table(name = "interview_questions")

**Fields:**

- 22:    @Id
- 23:    @GeneratedValue(strategy = GenerationType.IDENTITY)
- 29:    @Column(nullable = false, columnDefinition = "TEXT")
- 34:    @Column(columnDefinition = "TEXT")
- 40:    @Column(nullable = false)
- 45:    @Column(nullable = false, length = 100)
- 50:    @Column(nullable = false, length = 200)
- 54:    @Column(length = 500)
- 58:    @Column(name = "frequency_score")
- 62:    @ManyToOne(fetch = FetchType.LAZY)
- 63:    @JoinColumn(name = "module_id", nullable = false)
- 69:    @Column(name = "created_at", nullable = false, updatable = false)
- 74:    @Column(name = "updated_at")

### LearningModule

**File:** ``

**Table:** 20:@Table(name = "learning_modules")

**Fields:**

- 26:    @Id
- 27:    @GeneratedValue(strategy = GenerationType.IDENTITY)
- 32:    @Column(nullable = false, length = 200)
- 37:    @Column(nullable = false, length = 1000)
- 44:    @Column(nullable = false)
- 50:    @Column(nullable = false)
- 55:    @Column(name = "estimated_hours")
- 59:    @Column(name = "sort_order")
- 63:    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
- 68:    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
- 74:    @Column(name = "created_at", nullable = false, updatable = false)
- 79:    @Column(name = "updated_at")

### Topic

**File:** ``

**Table:** 16:@Table(name = "topics")

**Fields:**

- 40:    @Id
- 41:    @GeneratedValue(strategy = GenerationType.IDENTITY)
- 46:    @Column(nullable = false, length = 300)
- 51:    @Column(nullable = false, length = 1000)
- 56:    @Column(columnDefinition = "TEXT")
- 61:    @Column(name = "estimated_minutes")
- 65:    @Column(name = "sort_order")
- 70:    @Column(name = "topic_type", nullable = false)
- 74:    @ManyToOne(fetch = FetchType.LAZY)
- 75:    @JoinColumn(name = "module_id", nullable = false)
- 81:    @Column(name = "created_at", nullable = false, updatable = false)
- 86:    @Column(name = "updated_at")

