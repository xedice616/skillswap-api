package com.xadice.skillswap.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LearningRequestRequest {

    @NotBlank(message = "Message is required")
    @Size(min = 5, max = 500, message = "Message must be between 5 and 500 characters")
    private String message;

    @NotNull(message = "Requester id is required")
    private Long requesterId;

    public LearningRequestRequest() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getRequesterId() {
        return requesterId;
    }

    public void setRequesterId(Long requesterId) {
        this.requesterId = requesterId;
    }
}