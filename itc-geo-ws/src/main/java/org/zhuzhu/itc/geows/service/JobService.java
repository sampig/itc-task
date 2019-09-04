/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zhuzhu.itc.geows.bean.Job;
import org.zhuzhu.itc.geows.repository.JobRepository;

/**
 * @author Chenfeng Zhu
 *
 */
@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  public Job getJob(Integer id) {
    if (jobRepository.findById(id).isPresent()) {
      return jobRepository.findById(id).get();
    }
    return null;
  }

  public void saveJob(Job job) {
    jobRepository.save(job);
  }

  public void updateJob(Job job) {
    job.setGmtUpdate(Instant.now());
    jobRepository.save(job);
  }

  public List<Job> listAllJobsByPage(int pageSize, int pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Job> list = jobRepository.findAll(pageable).getContent();
    return list;
  }

}
