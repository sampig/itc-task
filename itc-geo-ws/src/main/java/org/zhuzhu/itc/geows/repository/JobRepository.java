/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.zhuzhu.itc.geows.bean.Job;

/**
 * @author Chenfeng Zhu
 *
 */
public interface JobRepository extends CrudRepository<Job, Integer> {

  Page<Job> findAll(Pageable pageable);

}
