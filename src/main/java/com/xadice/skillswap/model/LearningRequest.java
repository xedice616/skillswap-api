package com.xadice.skillswap.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "learning_requests")
public class LearningRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LearningRequestStatus status;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public LearningRequest() {
    }

    public LearningRequest(
            Long id,
            String message,
            LearningRequestStatus status,
            LocalDateTime createdAt,
            User requester,
            Skill skill
    ) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.requester = requester;
        this.skill = skill;
    }

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = LearningRequestStatus.PENDING;
        }

        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LearningRequestStatus getStatus() {
        return status;
    }

    public void setStatus(LearningRequestStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}