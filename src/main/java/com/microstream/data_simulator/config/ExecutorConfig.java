package com.microstream.data_simulator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ExecutorConfig {

  @Bean(name = "customTreadPoolTaskExecutor")
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(50);  // Number of threads for concurrent jobs
    executor.setMaxPoolSize(50);   // Fixed to avoid excessive context switching
    executor.setQueueCapacity(0);  // Direct hand-off to threads (no queuing)
    executor.setThreadNamePrefix("PersistentTask-");
    executor.initialize();
    return executor;
  }


}
