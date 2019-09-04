/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Chenfeng Zhu
 *
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/", "index");
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/main").setViewName("main");
    registry.addViewController("/search-form").setViewName("search_form");
  }

}
