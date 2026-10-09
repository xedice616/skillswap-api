package com.xadice.skillswap.dto.response;

import com.xadice.skillswap.model.LearningRequestStatus;

import java.time.LocalDateTime;

public class LearningRequestResponse {

    private Long id;
    private String message;
    private LearningRequestStatus status;
    private LocalDateTime createdAt;
    private Long requesterId;
    private String requesterName;
    private Long skillId;
    private String skillTitle;

    public LearningRequestResponse(
            Long id,
            String message,
            LearningRequestStatus status,
            LocalDateTime createdAt,
            Long requesterId,
            String requesterName,
            Long skillId,
            String skillTitle
    ) {
        this.id = id;
        this.message = message;
        this.status = status;
        this.createdAt = createdAt;
        this.requesterId = requesterId;
        this.requesterName = requesterName;
        this.skillId = skillId;
        this.skillTitle = skillTitle;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public LearningRequestStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public Long getSkillId() {
        return skillId;
    }

    public String getSkillTitle() {
        return skillTitle;
    }
}