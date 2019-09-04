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
    Job[] jobs = new Job[3];
    for (int i = 0; i < jobs.length; i++) {
      jobs[i] = new Job();
      jobs[i].setGmtAdd(Instant.now());
      jobs[i].setGmtUpdate(Instant.now());
      jobs[i].setStatus(JobStatus.REGISTERED);
    }
    jobs[0].setFilename("no_existing.xlsx");
    jobService.saveJob(jobs[0]);
    logger.log(Level.INFO, "Add a new job: " + jobs[0]);
    jobService.saveJob(jobs[1]);
    logger.log(Level.INFO, "Add another new job: " + jobs[1]);
    jobs[2].setFilename("test.xlsx");
    jobService.saveJob(jobs[2]);
    logger.log(Level.INFO, "Add another new job: " + jobs[2]);

    Job job = jobService.getJob(1);
    job.setStatus(JobStatus.IN_PROGRESS);
    jobService.updateJob(job);
    logger.log(Level.INFO, "Update a job: " + job);
    List<Job> list = jobService.listAllJobsByPage(10, 0);
    assertThat(list.size()).isEqualTo(jobs.length);
    logger.log(Level.INFO, "Get a list of section: " + list.size());
  }

}
