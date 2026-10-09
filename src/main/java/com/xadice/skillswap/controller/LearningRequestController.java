package com.xadice.skillswap.controller;

import com.xadice.skillswap.dto.request.LearningRequestRequest;
import com.xadice.skillswap.dto.response.LearningRequestResponse;
import com.xadice.skillswap.service.LearningRequestService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LearningRequestController {

    private final LearningRequestService learningRequestService;

    public LearningRequestController(
            LearningRequestService learningRequestService
    ) {
        this.learningRequestService =
                learningRequestService;
    }

    @PostMapping("/skills/{skillId}/requests")
    public ResponseEntity<LearningRequestResponse> createRequest(
            @PathVariable Long skillId,
            @Valid @RequestBody LearningRequestRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        learningRequestService
                                .createRequest(skillId, request)
                );
    }

    @GetMapping("/users/{userId}/sent-requests")
    public ResponseEntity<List<LearningRequestResponse>>
    getSentRequests(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                learningRequestService
                        .getSentRequests(userId)
        );
    }

    @GetMapping("/users/{userId}/received-requests")
    public ResponseEntity<List<LearningRequestResponse>>
    getReceivedRequests(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                learningRequestService
                        .getReceivedRequests(userId)
        );
    }

    @PatchMapping("/requests/{requestId}/accept")
    public ResponseEntity<LearningRequestResponse> acceptRequest(
            @PathVariable Long requestId,
            @RequestParam Long ownerId
    ) {

        return ResponseEntity.ok(
                learningRequestService
                        .acceptRequest(requestId, ownerId)
        );
    }

    @PatchMapping("/requests/{requestId}/reject")
    public ResponseEntity<LearningRequestResponse> rejectRequest(
            @PathVariable Long requestId,
            @RequestParam Long ownerId
    ) {

        return ResponseEntity.ok(
                learningRequestService
                        .rejectRequest(requestId, ownerId)
        );
    }

    @PatchMapping("/requests/{requestId}/complete")
    public ResponseEntity<LearningRequestResponse> completeRequest(
            @PathVariable Long requestId,
            @RequestParam Long ownerId
    ) {

        return ResponseEntity.ok(
                learningRequestService
                        .completeRequest(requestId, ownerId)
        );
    }
}