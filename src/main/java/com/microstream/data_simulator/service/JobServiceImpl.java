package com.microstream.data_simulator.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microstream.data_simulator.jobs.Job;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

  private static final Logger LOG = LoggerFactory.getLogger(JobServiceImpl.class);

  private static final String FILE_PATH = "stocks_in_work.json";
  private final Map<String, Future<?>> jobMap = new ConcurrentHashMap<>();
  private final ThreadPoolTaskExecutor taskExecutor;
  private final StockProducer stockProducer;
  private final ObjectMapper objectMapper;

  private Lock lock = new ReentrantLock();

  public JobServiceImpl(@Qualifier("customTreadPoolTaskExecutor") ThreadPoolTaskExecutor taskExecutor,
      StockProducer stockProducer, ObjectMapper objectMapper) {
    this.taskExecutor = taskExecutor;
    this.stockProducer = stockProducer;
    this.objectMapper = objectMapper;
    restore();
  }

  @Override
  public void add(String jobId) {
    if (!jobMap.containsKey(jobId)) {
      jobMap.put(jobId, taskExecutor.submit(new Job(jobId, stockProducer)));
      store();
    }
  }

  @Override
  public void add(List<String> jobIds) {
    for (String jobId : jobIds) {
      if (!jobMap.containsKey(jobId)) {
        jobMap.put(jobId, taskExecutor.submit(new Job(jobId, stockProducer)));
      }
    }
    store();
  }

  @Override
  public void remove(String jobId) {
    Future<?> future = jobMap.remove(jobId);
    if (future != null) {
      future.cancel(true);
      store();
    }
  }

  @Override
  public void store() {
    lock.lock();
    try {
      List<String> jobKeys = jobMap.keySet().stream().toList();
      objectMapper.writeValue(new File(FILE_PATH), jobKeys);
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      throw new RuntimeException("Failed to store job keys", e);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public void restore() {
    lock.lock();
    try {
      File file = new File(FILE_PATH);

      if (file.exists()) {
        List<String> jobKeys = objectMapper.readValue(file, List.class);
        this.add(jobKeys);
      }
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      throw new RuntimeException("Failed to restore job keys", e);
    } finally {
      lock.unlock();
    }
  }

  @Override
  public boolean isRunning(String jobId) {
    return jobMap.containsKey(jobId);
  }
}
