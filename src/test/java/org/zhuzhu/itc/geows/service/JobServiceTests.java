/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhuzhu.itc.geows.bean.Job;
import org.zhuzhu.itc.geows.util.Constants.JobStatus;

/**
 * @author Chenfeng Zhu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobServiceTests {

  Logger logger = Logger.getLogger(GeoClassServiceTests.class.getName());

  @Autowired
  private JobService jobService;

  @Test
  public void tc1_basic() {
    Job job1 = new Job();
    job1.setFilename("no_existing.xlsx");
    job1.setGmtAdd(Instant.now());
    job1.setGmtUpdate(Instant.now());
    job1.setStatus(JobStatus.REGISTERED);
    jobService.saveJob(job1);
    logger.log(Level.INFO, "Add a new job: " + job1);
    Job job2 = new Job();
    job2.setGmtAdd(Instant.now());
    job2.setGmtUpdate(Instant.now());
    job2.setStatus(JobStatus.REGISTERED);
    jobService.saveJob(job2);
    logger.log(Level.INFO, "Add another new job: " + job2);
    Job job3 = new Job();
    job3.setFilename("test.xlsx");
    job3.setGmtAdd(Instant.now());
    job3.setGmtUpdate(Instant.now());
    job3.setStatus(JobStatus.REGISTERED);
    jobService.saveJob(job3);
    logger.log(Level.INFO, "Add another new job: " + job3);

    Job job = jobService.getJob(1);
    job.setStatus(JobStatus.IN_PROGRESS);
    jobService.updateJob(job);
    logger.log(Level.INFO, "Update a job: " + job);
    List<Job> list = jobService.listAllJobsByPage(10, 0);
    assertThat(list.size()).isEqualTo(2);
    logger.log(Level.INFO, "Get a list of section: " + list.size());
  }

}
