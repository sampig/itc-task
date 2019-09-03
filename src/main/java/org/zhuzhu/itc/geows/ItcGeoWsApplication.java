/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author Chenfeng Zhu
 *
 */
@SpringBootApplication
@ComponentScan("org.zhuzhu.itc.geows.*")
public class ItcGeoWsApplication {

  public static void main(String[] args) {
    SpringApplication.run(ItcGeoWsApplication.class, args);
  }

}
