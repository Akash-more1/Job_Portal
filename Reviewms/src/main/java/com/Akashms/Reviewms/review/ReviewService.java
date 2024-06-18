package com.Akashms.Reviewms.review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviewsOfCompany(Long companyId);

    boolean addReview( Review review);

    Review getReview( Long reviewId);

    boolean updateReview( Long reviewId, Review updatedReview);

    List<Review> getAllReviews();

    void deleteReview(Long reviewId);
}
