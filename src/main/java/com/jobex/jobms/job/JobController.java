package com.jobex.jobms.job;

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
    public ResponseEntity<List<Job>> findAll()
    {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id)
    {
        Job job = jobService.getJobById(id);
        if(job!=null)
            return new ResponseEntity<>(job,HttpStatus.OK);
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
