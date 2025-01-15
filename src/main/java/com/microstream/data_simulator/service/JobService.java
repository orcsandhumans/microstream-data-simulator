package com.microstream.data_simulator.service;

import java.util.List;

public interface JobService {
  void add(String jobId);
  void add(List<String> jobIds);
  void remove(String jobId);
  void store();
  void restore();
  boolean isRunning(String jobId);
}
