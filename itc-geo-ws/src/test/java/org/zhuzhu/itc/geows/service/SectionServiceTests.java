/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.zhuzhu.itc.geows.bean.Section;

/**
 * @author Chenfeng Zhu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SectionServiceTests {

  Logger logger = Logger.getLogger(SectionServiceTests.class.getName());

  @Autowired
  private SectionService sectionService;

  @Test
  public void tc1_basic() {
    Section section1 = new Section();
    String name1 = "section1";
    section1.setName(name1);
    sectionService.saveSection(section1);
    logger.log(Level.INFO, "Add a new section: " + section1);
    Section section2 = new Section();
    section2.setName("section2");
    sectionService.saveSection(section2);
    logger.log(Level.INFO, "Add another new section: " + section2);

    section1.setName(name1 + " updated");
    sectionService.updateSection(section1);
    Section s = sectionService.getSection(1);
    assertThat(s.getName()).contains(name1);
    logger.log(Level.INFO, "Get a section: " + s);
    List<Section> list = sectionService.listSectionByPage(10, 0);
    assertThat(list.size()).isEqualTo(2);
    logger.log(Level.INFO, "Get a list of section: " + list.size());
    List<Section> list2 = sectionService.listSectionByPage(10, 1);
    assertThat(list2.size()).isEqualTo(0);
  }

}
