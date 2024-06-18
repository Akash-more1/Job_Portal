package com.Akashms.jobms.job.impl;



import com.Akashms.jobms.job.DTO.JobWithCompanyDTO;
import com.Akashms.jobms.job.Job;
import com.Akashms.jobms.job.JobRepository;
import com.Akashms.jobms.job.JobService;
import com.Akashms.jobms.job.clients.CompanyClient;
import com.Akashms.jobms.job.clients.ReviewClient;
import com.Akashms.jobms.job.external.Company;
import com.Akashms.jobms.job.external.Review;
import com.Akashms.jobms.job.mapper.jobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class jobServiceImpl implements JobService {

   private JobRepository jobRepository;

//   @Autowired
//   RestTemplate restTemplate;

    int attempt=0;




   private CompanyClient companyClient;


   private ReviewClient reviewClient;

    public jobServiceImpl(JobRepository jobRepository, CompanyClient companyClient, ReviewClient reviewClient) {

        this.jobRepository = jobRepository;

        this.companyClient=companyClient;

        this.reviewClient=reviewClient;
    }

    @Override
    //@CircuitBreaker(name ="companyBreaker", fallbackMethod = "companyBreakerFallback")

    //@Retry(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")

    @RateLimiter(name = "companyBreaker",fallbackMethod = "companyBreakerFallback")

    public List<JobWithCompanyDTO> findAll() {

        System.out.println("Attempt"+ ++attempt);

        List<JobWithCompanyDTO> jobWithCompanyDTOList= new ArrayList<>();
        List<Job> jobs=jobRepository.findAll();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());



    }

    public List<String>  companyBreakerFallback(Exception e){
        ArrayList<String> list=new ArrayList<>();
        list.add("Custom made message from fallback mechanism of resilience4J because call not successful");
        return  list;
    }

    private JobWithCompanyDTO convertToDto(Job job){


           // JobWithCompanyDTO jobWithCompanyDTO= new JobWithCompanyDTO();



        //RestTemplate restTemplate= new RestTemplate();

           // Company company= restTemplate.getForObject("http://companyms:8081/company/getCompanyById/"+job.getCompany(), Company.class);
           // jobWithCompanyDTO.setCompany(company);


        //getting list of reviews
//     ResponseEntity<List<Review>>  reviewResponse= restTemplate.exchange("http://Reviewms:8083/reviews/getAllReviewsOfCompany/" + job.getCompany(), HttpMethod.GET, null,
//                new ParameterizedTypeReference<List<Review>>() {
//                });
//
//     List<Review> reviews=reviewResponse.getBody();

       // List<Review> reviews=reviewClient.getAllReviewsOfCompany(job.getCompany());

        //joBMapper will map all atribute and return jobWithCompany object
        Company company= companyClient.getCompany(job.getCompany());

        List<Review> reviews = reviewClient.getAllReviewsOfCompany(job.getCompany());
                  JobWithCompanyDTO jobWithCompanyDTO= jobMapper.jobMapper(job,company,reviews);
            return jobWithCompanyDTO;


    }

    @Override
    public void createJob(Job job) {
          jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDTO getJobById(Long id) {
        Job job= jobRepository.findById(id).orElse(null);

        if(job==null){
            throw new RuntimeException("job Not Found");
        }
        else{
           return  convertToDto(job);
        }
    }

    @Override
    public boolean deleteJobById(long id) {
         try{
             jobRepository.deleteById(id);
             return  true;
         }
         catch (Exception e){
             return false;
         }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional=jobRepository.findById(id);

        if(jobOptional.isPresent()){
            Job job=jobOptional.get();

            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;

        }
        return false;
    }
}
