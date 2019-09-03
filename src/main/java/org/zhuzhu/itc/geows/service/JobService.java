/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zhuzhu.itc.geows.bean.Job;
import org.zhuzhu.itc.geows.repository.JobRepository;
import org.zhuzhu.itc.geows.util.Constants;

/**
 * @author Chenfeng Zhu
 *
 */
@Service
public class JobService {

  @Autowired
  private JobRepository jobRepository;

  public Job getJob(Integer id) {
    return jobRepository.findById(id).get();
  }

  public void saveJob(Job job) {
    if (job.getFilename() == null
        || !Files.exists(Paths.get(Constants.FILE_PATH_ROOT, job.getFilename()))) {
      // if the file doesn't exist, use the default one.
      job.setFilename(Constants.FILE_XLSX_DEFAULT);
    }
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
