package com.Akashms.Reviewms.review;

import com.Akashms.Reviewms.review.messaging.ReviewMessageProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {

        this.reviewService = reviewService;
        this.reviewMessageProducer=reviewMessageProducer;
    }

    @GetMapping("/getAllReviews")
    public  ResponseEntity getAllReviews(){
        List<Review> reviews= reviewService.getAllReviews();

        return new ResponseEntity(reviews,HttpStatus.OK);
    }

    @GetMapping("/getAllReviewsOfCompany/{companyId}")
    public ResponseEntity<List<Review>> getAllReviewsOfCompany(@PathVariable Long companyId){
        return new ResponseEntity<>(reviewService.getAllReviewsOfCompany(companyId),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody Review review){
        boolean isReviewSaved = reviewService.addReview( review);
        if (isReviewSaved) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Added Successfully",
                    HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Review Not Saved",
                    HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getReview/{reviewId}")
    public  ResponseEntity getReview( @PathVariable Long reviewId){
        Review review=reviewService.getReview(reviewId);

        return new ResponseEntity(review,HttpStatus.OK);
    }

    @PutMapping("/updateReview/{reviewId}")
    public ResponseEntity updateReview ( @PathVariable Long reviewId, @RequestBody Review updatedReview){
        boolean b = reviewService.updateReview(reviewId,updatedReview);

        return  new ResponseEntity(b, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReview/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable Long reviewId){
        reviewService.deleteReview(reviewId);
        return new ResponseEntity("review delete", HttpStatus.OK);
    }

    @GetMapping("/averageRating")
    public double getAverageRatingOfCompany(@RequestParam Long companyId){
      List<Review> reviewList= reviewService.getAllReviewsOfCompany(companyId);

      return  reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
