package com.xadice.skillswap.controller;

import com.xadice.skillswap.dto.request.ReviewRequest;
import com.xadice.skillswap.dto.response.ReviewResponse;
import com.xadice.skillswap.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(
            ReviewService reviewService
    ) {
        this.reviewService = reviewService;
    }

    @PostMapping("/users/{userId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @PathVariable Long userId,
            @Valid @RequestBody ReviewRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        reviewService.createReview(
                                userId,
                                request
                        )
                );
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<List<ReviewResponse>>
    getUserReviews(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                reviewService.getUserReviews(userId)
        );
    }

    @GetMapping("/users/{userId}/written-reviews")
    public ResponseEntity<List<ReviewResponse>>
    getWrittenReviews(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                reviewService.getWrittenReviews(userId)
        );
    }
}