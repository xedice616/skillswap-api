package com.xadice.skillswap.repository;

import com.xadice.skillswap.model.Review;
import com.xadice.skillswap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    List<Review> findAllByReviewedUser(
            User reviewedUser
    );

    List<Review> findAllByReviewer(
            User reviewer
    );

    boolean existsByReviewerAndReviewedUser(
            User reviewer,
            User reviewedUser
    );
}