package com.Akashms.jobms.job;

import com.Akashms.jobms.job.DTO.JobWithCompanyDTO;

import java.util.List;

public interface JobService {

    List<JobWithCompanyDTO> findAll();
    void createJob(Job job);

    JobWithCompanyDTO getJobById(Long id);

    boolean deleteJobById(long id);

    boolean updateJob(Long id, Job updatedJob);

}
