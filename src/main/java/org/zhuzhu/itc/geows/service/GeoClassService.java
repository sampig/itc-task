/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zhuzhu.itc.geows.bean.GeoClass;
import org.zhuzhu.itc.geows.repository.GeoClassRepository;

/**
 * @author Chenfeng Zhu
 *
 */
@Service
public class GeoClassService {

  @Autowired
  private GeoClassRepository geoClassRepository;

  public GeoClass getGeoClass(Integer id) {
    return geoClassRepository.findById(id).get();
  }

  public void saveGeoClass(GeoClass geoClass) {
    geoClassRepository.save(geoClass);
  }

  public List<GeoClass> listAllGeoClasses() {
    Iterable<GeoClass> iterator = geoClassRepository.findAll();
    List<GeoClass> list = new ArrayList<>(0);
    iterator.forEach(list::add);
    return list;
  }

  public List<GeoClass> listAllGeoClassesBySectionId(int sectionId, Sort sort) {
    return geoClassRepository.findBySectionId(sectionId, sort);
  }

  public List<GeoClass> listAllGeoClassesByNameOrCode(String key) {
    return geoClassRepository.findByNameOrCode(key);
  }

}
