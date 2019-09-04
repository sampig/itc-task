/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zhuzhu.itc.geows.bean.GeoClass;
import org.zhuzhu.itc.geows.bean.Section;
import org.zhuzhu.itc.geows.service.GeoClassService;
import org.zhuzhu.itc.geows.service.SectionService;
import org.zhuzhu.itc.geows.util.Constants;

/**
 * @author Chenfeng Zhu
 *
 */
@RestController
@RequestMapping(value = "/")
public class SectionController {

  @Autowired
  private SectionService sectionService;

  @Autowired
  private GeoClassService geoClassService;

  @RequestMapping(value = "list-sections", method = RequestMethod.GET)
  public List<Section> listSections(
      @RequestParam(value = "size", required = false) Integer pageSize,
      @RequestParam(value = "page", required = false) Integer pageNumber) {
    if (pageSize == null) {
      pageSize = Constants.PAGE_SIZE;
    }
    if (pageNumber == null) {
      pageNumber = Constants.PAGE_NUMBER;
    }
    return sectionService.listSectionByPage(pageSize, pageNumber);
  }

  @RequestMapping(value = "list-geoclasses", method = RequestMethod.GET)
  public List<GeoClass> listGeoClass() {
    return geoClassService.listAllGeoClasses();
  }

  @RequestMapping(value = "section/{sectionId}/list-geoclasses", method = RequestMethod.GET)
  public List<GeoClass> listGeoClassBySection(@PathVariable("sectionId") Integer sectionId) {
    return geoClassService.listAllGeoClassesBySectionId(sectionId, Sort.by("code"));
  }

  @RequestMapping(value = "search-sections", method = {RequestMethod.GET, RequestMethod.POST})
  public List<Section> searchSections(@RequestParam(value = "key") String key,
      @RequestParam(value = "size", required = false) Integer pageSize,
      @RequestParam(value = "page", required = false) Integer pageNumber) {
    if (pageSize == null) {
      pageSize = Constants.PAGE_SIZE;
    }
    if (pageNumber == null) {
      pageNumber = Constants.PAGE_NUMBER;
    }
    // TODO: optimize
    List<Section> list = new ArrayList<>();
    Set<Integer> setSectionId = new HashSet<>();
    list.addAll(sectionService.listSectionByName(key, pageSize, pageNumber));
    for (Section section : list) {
      setSectionId.add(section.getId());
    }
    List<GeoClass> listGeo = geoClassService.listAllGeoClassesByNameOrCode(key);
    for (GeoClass geo : listGeo) {
      int id = geo.getSection().getId();
      if (setSectionId.add(id)) {
        list.add((Section) Hibernate.unproxy(sectionService.getSection(id)));
      }
    }
    return list;
  }

}
