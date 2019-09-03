/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zhuzhu.itc.geows.bean.GeoClass;

/**
 * @author Chenfeng Zhu
 *
 */
public interface GeoClassRepository extends CrudRepository<GeoClass, Integer> {

  List<GeoClass> findBySectionId(Integer sectionId, Sort sort);

  @Query("select g from GeoClass g where g.name like %:key% or g.code like %:key%")
  List<GeoClass> findByNameOrCode(@Param("key") String key);

}
