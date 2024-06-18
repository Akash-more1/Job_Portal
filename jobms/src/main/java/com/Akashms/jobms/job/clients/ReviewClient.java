package com.Akashms.jobms.job.clients;

import com.Akashms.jobms.job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "Reviewms",url = "${Reviewms.url}")
public interface ReviewClient {

    @GetMapping("/reviews/getAllReviewsOfCompany/{id}")
    List<Review> getAllReviewsOfCompany(@PathVariable("id") Long id);
}
