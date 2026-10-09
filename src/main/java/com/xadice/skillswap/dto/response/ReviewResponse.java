package com.xadice.skillswap.dto.response;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long id;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private Long reviewerId;
    private String reviewerName;
    private Long reviewedUserId;
    private String reviewedUserName;

    public ReviewResponse(
            Long id,
            Integer rating,
            String comment,
            LocalDateTime createdAt,
            Long reviewerId,
            String reviewerName,
            Long reviewedUserId,
            String reviewedUserName
    ) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewedUserId = reviewedUserId;
        this.reviewedUserName = reviewedUserName;
    }

    public Long getId() {
        return id;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public Long getReviewedUserId() {
        return reviewedUserId;
    }

    public String getReviewedUserName() {
        return reviewedUserName;
    }
}