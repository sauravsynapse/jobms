package com.jobex.jobms.job.impl;

import com.jobex.jobms.job.JobRepository;
import com.jobex.jobms.job.JobService;
import com.jobex.jobms.job.Job;
import com.jobex.jobms.job.dto.JobDTO;
import com.jobex.jobms.job.external.Company;
import com.jobex.jobms.job.external.Review;
import com.jobex.jobms.job.mapper.JobMapper;
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
public class JobServiceImpl implements JobService {

    @Autowired
    RestTemplate restTemplate;

    //private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;
    private Long nextId = 1L;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOList = new ArrayList<>();

        return jobs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDTO getJobById(Long id)
    {
        return convertToDTO(jobRepository.findById(id).orElse(null));
    }


    @Override
    public boolean deleteJobById(Long id) {
        if (jobRepository.existsById(id))
        {
            jobRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean updateJobById(Long id, Job updatedjob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setDescription(updatedjob.getDescription());
            job.setLocation(updatedjob.getLocation());
            job.setTitle(updatedjob.getTitle());
            job.setMaxSalary(updatedjob.getMaxSalary());
            job.setMinSalary(updatedjob.getMinSalary());
            job.setLocation(updatedjob.getLocation());
            jobRepository.save(job);
            return true;
        }

        return false;
    }

    private JobDTO convertToDTO(Job job)
    {
        //RestTemplate restTemplate = new RestTemplate();
        Company company = restTemplate.getForObject("http://companyms:8081/companies/"+job.getCompanyId(), Company.class);
        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
                "http://reviewms:8083/reviews?companyId=" + job.getCompanyId(),
                HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Review>>() {
        });

        List<Review> reviews = reviewResponse.getBody();
        JobDTO jobDTO = JobMapper.maptoJobDTO(job,company,reviews);

        return jobDTO;
    }
}
