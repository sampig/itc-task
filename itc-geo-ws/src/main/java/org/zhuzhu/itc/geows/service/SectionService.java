/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zhuzhu.itc.geows.bean.Section;
import org.zhuzhu.itc.geows.repository.SectionRepository;

/**
 * @author Chenfeng Zhu
 *
 */
@Service
public class SectionService {

  @Autowired
  private SectionRepository sectionRepository;

  public Section getSection(Integer id) {
    if (sectionRepository.findById(id).isPresent()) {
      return sectionRepository.findById(id).get();
    }
    return null;
  }

  public void saveSection(Section section) {
    sectionRepository.save(section);
  }

  public void updateSection(Section section) {
    sectionRepository.save(section);
  }

  public List<Section> listSectionByPage(int pageSize, int pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Section> list = sectionRepository.findAll(pageable).getContent();
    return list;
  }

  public List<Section> listSectionByName(String name, int pageSize, int pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    List<Section> list = sectionRepository.findByName(name, pageable).getContent();
    return list;
  }

}
