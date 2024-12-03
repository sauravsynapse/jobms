package com.jobex.jobms.job.mapper;

import com.jobex.jobms.job.Job;
import com.jobex.jobms.job.dto.JobDTO;
import com.jobex.jobms.job.external.Company;
import com.jobex.jobms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO maptoJobDTO(Job job, Company company, List<Review> reviews)
    {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReview(reviews);
        return jobDTO;
    }
}
