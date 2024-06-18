package com.Akashms.Reviewms.review.impl;


import com.Akashms.Reviewms.review.Review;
import com.Akashms.Reviewms.review.ReviewRepository;
import com.Akashms.Reviewms.review.ReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;


    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;

    }

    @Override
    public List<Review> getAllReviewsOfCompany(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return reviews;
    }



    @Override
    public boolean addReview( Review review) {

        if ( review !=null){

            reviewRepository.save(review);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Review getReview( Long reviewId) {
        return reviewRepository.findById(reviewId).orElse(null);
    }

    @Override
    public boolean updateReview( Long reviewId, Review updatedReview) {

        Review review= reviewRepository.findById(reviewId).orElse(null);

        if (review !=null){
            review.setTitle(updatedReview.getTitle());
            review.setDescription(updatedReview.getDescription());
            review.setRating(updatedReview.getRating());

            reviewRepository.save(review);
            return true;
        }

        return false;




    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void deleteReview(Long reviewId) {
         reviewRepository.deleteById(reviewId);
    }
}
