package com.microstream.data_simulator.service;

import com.microstream.data_simulator.jobs.Job;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class JobStatelessServiceImpl implements JobService {

  private static final Logger LOG = LoggerFactory.getLogger(JobStatelessServiceImpl.class);

  private final Map<String, Future<?>> jobMap = new ConcurrentHashMap<>();
  private final ThreadPoolTaskExecutor taskExecutor;
  private final StockProducer stockProducer;

  public JobStatelessServiceImpl(@Qualifier("customTreadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor,
      StockProducer stockProducer) {
    this.taskExecutor = taskExecutor;
    this.stockProducer = stockProducer;
  }

  @Override
  public void add(String jobId) {
    if (!jobMap.containsKey(jobId)) {
      jobMap.put(jobId, taskExecutor.submit(new Job(jobId, stockProducer)));
    }
  }

  @Override
  public void add(List<String> jobIds) {
    for (String jobId : jobIds) {
      if (!jobMap.containsKey(jobId)) {
        jobMap.put(jobId, taskExecutor.submit(new Job(jobId, stockProducer)));
      }
    }
  }

  @Override
  public void remove(String jobId) {
    Future<?> future = jobMap.remove(jobId);
    if (future != null) {
      future.cancel(true);
    }
  }

  @Override
  public boolean isRunning(String jobId) {
    return jobMap.containsKey(jobId);
  }
}
