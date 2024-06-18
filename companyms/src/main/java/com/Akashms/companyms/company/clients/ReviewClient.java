package com.Akashms.companyms.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Reviewms",url = "${Reviewms.url}")
public interface ReviewClient {

    @GetMapping("/reviews/averageRating")
    public double getAverageRatingOfCompany(@RequestParam("companyId") Long companyId);
}
