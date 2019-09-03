/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.util;

import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.zhuzhu.itc.geows.bean.GeoClass;
import org.zhuzhu.itc.geows.bean.Job;
import org.zhuzhu.itc.geows.bean.Section;
import org.zhuzhu.itc.geows.service.GeoClassService;
import org.zhuzhu.itc.geows.service.JobService;
import org.zhuzhu.itc.geows.service.SectionService;

/**
 * @author Chenfeng Zhu
 *
 */
public class ExcelParser implements Runnable {

  private InputStream inputStream;

  // TODO: modify design
  private Job job;
  private JobService jobService;
  private SectionService sectionService;
  private GeoClassService geoClassService;

  public ExcelParser(JobService jobService, SectionService sectionService,
      GeoClassService geoClassService, Job job) {
    this.jobService = jobService;
    this.sectionService = sectionService;
    this.geoClassService = geoClassService;
    this.job = job;
    inputStream = FileUtils.getFileInputStream(job.getFilename());
  }

  @Override
  public void run() {
    long sleep = 1 * 1000;
    try {
      Thread.sleep(sleep);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    job.setGmtUpdate(Instant.now());
    job.setStatus(Constants.JobStatus.IN_PROGRESS);
    jobService.saveJob(job);
    try {
      Thread.sleep(sleep);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    List<Section> list = FileUtils.convertExcelToSections(inputStream);
    List<Integer> listId = new ArrayList<>();
    for (Section section : list) {
      List<GeoClass> listGeo = section.getGeoClasses();
      section.setGeoClasses(null);
      sectionService.saveSection(section);
      listId.add(section.getId());
      for (GeoClass geo : listGeo) {
        geo.setSection(section);
        geoClassService.saveGeoClass(geo);
      }
    }
    StringBuffer results = new StringBuffer();
    for (Integer id : listId) {
      results.append(sectionService.getSection(id) + "\n");
    }
    job.setGmtUpdate(Instant.now());
    job.setStatus(Constants.JobStatus.FINISHED);
    job.setResult(results.toString());
    jobService.saveJob(job);
  }

}
