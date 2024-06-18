package com.Akashms.jobms.job;

import com.Akashms.jobms.job.DTO.JobWithCompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/jobs/createJob")
    public ResponseEntity createJob(@RequestBody Job job){
        jobService.createJob(job);
        return new ResponseEntity("job created Successfully", HttpStatus.CREATED);
    }

    @GetMapping("/jobs/findAll")
    public ResponseEntity findAll(){

    List<JobWithCompanyDTO> jobList=jobService.findAll();

    return new ResponseEntity(jobList, HttpStatus.OK);
    }

    @GetMapping("/jobs/getJobByID/{id}")
    public ResponseEntity getJobById(@PathVariable Long id){
        try{
            JobWithCompanyDTO jobWithCompanyDTO =jobService.getJobById(id);
            return new ResponseEntity(jobWithCompanyDTO,HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/jobs/updateJob")
    public ResponseEntity updateJob (@RequestParam long id, @RequestBody Job job){
        jobService.updateJob(id,job);
        return new ResponseEntity("job with id "+id+" updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/jobs/deleteJob/{id}")
    public ResponseEntity deleteJob(@PathVariable Long id){

        boolean b=jobService.deleteJobById(id);

        return  new ResponseEntity(b,HttpStatus.OK);
    }
}
