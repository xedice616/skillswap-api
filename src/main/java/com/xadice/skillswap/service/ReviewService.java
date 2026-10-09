package com.xadice.skillswap.service;

import com.xadice.skillswap.dto.request.ReviewRequest;
import com.xadice.skillswap.dto.response.ReviewResponse;
import com.xadice.skillswap.exception.DuplicateResourceException;
import com.xadice.skillswap.exception.InvalidOperationException;
import com.xadice.skillswap.exception.ResourceNotFoundException;
import com.xadice.skillswap.model.LearningRequestStatus;
import com.xadice.skillswap.model.Review;
import com.xadice.skillswap.model.User;
import com.xadice.skillswap.repository.LearningRequestRepository;
import com.xadice.skillswap.repository.ReviewRepository;
import com.xadice.skillswap.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LearningRequestRepository learningRequestRepository;

    public ReviewService(
            ReviewRepository reviewRepository,
            UserRepository userRepository,
            LearningRequestRepository learningRequestRepository
    ) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.learningRequestRepository =
                learningRequestRepository;
    }

    public ReviewResponse createReview(
            Long reviewedUserId,
            ReviewRequest request
    ) {

        User reviewer = findUser(
                request.getReviewerId()
        );

        User reviewedUser = findUser(
                reviewedUserId
        );

        if (reviewer.getId()
                .equals(reviewedUser.getId())) {

            throw new InvalidOperationException(
                    "You cannot review yourself"
            );
        }

        boolean completed =
                learningRequestRepository
                        .existsByRequesterAndSkillOwnerAndStatus(
                                reviewer,
                                reviewedUser,
                                LearningRequestStatus.COMPLETED
                        );

        if (!completed) {
            throw new InvalidOperationException(
                    "A review can only be submitted after a completed learning request"
            );
        }

        if (reviewRepository
                .existsByReviewerAndReviewedUser(
                        reviewer,
                        reviewedUser
                )) {

            throw new DuplicateResourceException(
                    "You already reviewed this user"
            );
        }

        Review review = new Review();

        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setReviewer(reviewer);
        review.setReviewedUser(reviewedUser);

        Review savedReview =
                reviewRepository.save(review);

        return mapToResponse(savedReview);
    }

    public List<ReviewResponse> getUserReviews(
            Long userId
    ) {

        User user = findUser(userId);

        return reviewRepository
                .findAllByReviewedUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ReviewResponse> getWrittenReviews(
            Long userId
    ) {

        User user = findUser(userId);

        return reviewRepository
                .findAllByReviewer(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private User findUser(Long id) {

        return userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id
                        )
                );
    }

    private ReviewResponse mapToResponse(
            Review review
    ) {

        return new ReviewResponse(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getReviewer().getId(),
                review.getReviewer().getName(),
                review.getReviewedUser().getId(),
                review.getReviewedUser().getName()
        );
    }
}