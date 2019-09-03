/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

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
import org.zhuzhu.itc.geows.bean.GeoClass;
import org.zhuzhu.itc.geows.bean.Section;

/**
 * @author Chenfeng Zhu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeoClassServiceTests {

  Logger logger = Logger.getLogger(GeoClassServiceTests.class.getName());

  @Autowired
  private SectionService sectionService;

  @Autowired
  private GeoClassService geoClassService;

  @Test
  public void tc1_basic() {
    Section section1 = new Section();
    String sname1 = "section1";
    section1.setName(sname1);
    sectionService.saveSection(section1);
    int id = section1.getId();
    GeoClass geo1 = new GeoClass();
    String gname1 = "geo1";
    String code1 = "code1";
    geo1.setName(gname1);
    geo1.setCode(code1);
    geo1.setSection(section1);
    geoClassService.saveGeoClass(geo1);
    GeoClass geo2 = new GeoClass();
    geo2.setName("geo2");
    geo2.setCode("code2");
    geo2.setSection(section1);
    geoClassService.saveGeoClass(geo2);
    section1 = sectionService.getSection(id);
    logger.log(Level.INFO, "Get section: " + section1);
    List<Section> listSection = sectionService.listSectionByPage(10, 0);
    for (Section s : listSection) {
      logger.log(Level.INFO, "section: " + s);
    }
    List<GeoClass> listgeo = geoClassService.listAllGeoClasses();
    for (GeoClass g : listgeo) {
      logger.log(Level.INFO, "geo: " + g);
    }
  }

}
