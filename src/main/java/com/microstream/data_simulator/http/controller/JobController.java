package com.microstream.data_simulator.http.controller;

import com.microstream.data_simulator.service.JobService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

  private final JobService jobService;

  public JobController(JobService jobService) {
    this.jobService = jobService;
  }

  @PostMapping("/start/{jobId}")
  public ResponseEntity<String> startJob(@PathVariable String jobId) {
    if (jobService.isRunning(jobId)) {
      return ResponseEntity.ok("Job is already running: " + jobId);
    }

    jobService.add(jobId);

    return ResponseEntity.ok("Job started: " + jobId);
  }

  @PostMapping("/start")
  public ResponseEntity<List<String>> startJobs(@RequestBody List<String> jobIds) {
    List<String> result = new ArrayList<>();
    for (String jobId : jobIds) {
      if (!jobService.isRunning(jobId)) {
        jobService.add(jobId);
      }
    }

    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/start/{jobId}")
  public ResponseEntity<String> deleteJob(@PathVariable String jobId) {
    jobService.remove(jobId);
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

}
