/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.zhuzhu.itc.geows.bean.Section;

/**
 * @author Chenfeng Zhu
 *
 */
public interface SectionRepository extends CrudRepository<Section, Integer> {

  Page<Section> findAll(Pageable pageable);

  @Query("select s from Section s where s.name like %:name%")
  Page<Section> findByName(@Param("name") String name, Pageable pageable);

}
