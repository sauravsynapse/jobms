package com.jobex.jobms.job;

import com.jobex.jobms.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll()
    {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id)
    {
        JobDTO jobDTO = jobService.getJobById(id);
        if(jobDTO !=null)
            return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job)
    {
        jobService.createJob(job);
        return new ResponseEntity<>("Job added Successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id)
    {
        if(jobService.deleteJobById(id))
            return ResponseEntity.ok("Job deleted successfully");
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //@PutMapping("jobs/{id}")
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job)
    {
        if(jobService.updateJobById(id, job))
            return ResponseEntity.ok("Job updated Successfully");
        else
            return new ResponseEntity<>("Job not found",HttpStatus.NOT_FOUND);
    }
}
