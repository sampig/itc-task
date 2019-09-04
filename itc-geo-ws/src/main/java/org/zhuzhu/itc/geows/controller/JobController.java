/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.controller;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zhuzhu.itc.geows.bean.Job;
import org.zhuzhu.itc.geows.model.ResponseVO;
import org.zhuzhu.itc.geows.service.GeoClassService;
import org.zhuzhu.itc.geows.service.JobService;
import org.zhuzhu.itc.geows.service.SectionService;
import org.zhuzhu.itc.geows.util.Constants;
import org.zhuzhu.itc.geows.util.Constants.JobStatus;
import org.zhuzhu.itc.geows.util.ExcelParser;

/**
 * @author Chenfeng Zhu
 *
 */
@RestController
@RequestMapping(value = "/")
public class JobController {

  @Autowired
  private JobService jobService;

  @Autowired
  private SectionService sectionService;

  @Autowired
  private GeoClassService geoClassService;

  @RequestMapping(value = "index", method = {RequestMethod.POST, RequestMethod.GET})
  public String index() {
    return "small restAPI web-application"; // Requirement 1
  }

  @RequestMapping(value = "list-jobs", method = {RequestMethod.POST, RequestMethod.GET})
  public List<Job> listJobs(@RequestParam(value = "size", required = false) Integer pageSize,
      @RequestParam(value = "page", required = false) Integer pageNumber) {
    if (pageSize == null) {
      pageSize = Constants.PAGE_SIZE;
    }
    if (pageNumber == null) {
      pageNumber = Constants.PAGE_NUMBER;
    }
    return jobService.listAllJobsByPage(pageSize, pageNumber);
  }

  @RequestMapping(value = "register-job", method = RequestMethod.GET)
  public ResponseVO registerJob(
      @RequestParam(value = "filename", required = false) String filename) {
    Job job = new Job();
    if (filename != null) {
      job.setFilename(filename);
    }
    return registerJob(job);
  }

  @RequestMapping(value = "register-job", method = RequestMethod.POST)
  public ResponseVO registerJob(@RequestBody Job job) {
    job.setGmtAdd(Instant.now());
    job.setGmtUpdate(Instant.now());
    job.setStatus(JobStatus.REGISTERED);
    if (job.getFilename() == null
        || !Files.exists(Paths.get(Constants.FILE_PATH_ROOT, job.getFilename()))) {
      // if the file doesn't exist, use the default one.
      job.setFilename(Constants.FILE_XLSX_DEFAULT);
    }
    jobService.saveJob(job);
    ExcelParser excelParser = new ExcelParser(jobService, sectionService, geoClassService, job);
    Thread thread = new Thread(excelParser);
    thread.start();
    return new ResponseVO(true, "Add a new job: " + job.getId());
  }

  @RequestMapping(value = "job/{jobId}", method = RequestMethod.GET)
  public Job viewJob(@PathVariable("jobId") Integer jobId) {
    return jobService.getJob(jobId);
  }

}
