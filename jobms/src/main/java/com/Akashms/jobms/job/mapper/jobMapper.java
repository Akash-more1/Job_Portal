package com.Akashms.jobms.job.mapper;

import com.Akashms.jobms.job.DTO.JobWithCompanyDTO;
import com.Akashms.jobms.job.Job;
import com.Akashms.jobms.job.external.Company;
import com.Akashms.jobms.job.external.Review;

import java.util.List;

public class jobMapper {

    public static JobWithCompanyDTO jobMapper(Job job, Company company, List<Review> reviews){
        JobWithCompanyDTO jobWithCompanyDTO =new JobWithCompanyDTO();

        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaxSalary(job.getMaxSalary());
        jobWithCompanyDTO.setMinSalary(job.getMinSalary());
        jobWithCompanyDTO.setCompany(company);
        jobWithCompanyDTO.setReviews(reviews);

        return jobWithCompanyDTO;
    }
}
