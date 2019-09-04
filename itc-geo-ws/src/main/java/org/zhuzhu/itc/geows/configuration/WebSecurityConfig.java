/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * @author spring.io
 * @author Chenfeng Zhu
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private String username = "zhuzhu";
  private String password = "asdfasdf";

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // TODO: to be optimized.
    http.authorizeRequests().antMatchers("/", "/index").permitAll();
    http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login")
        .usernameParameter("username").passwordParameter("password").permitAll().and().logout()
        .permitAll();
    http.csrf().ignoringAntMatchers("/h2-console/**"); // In order to use H2 console.
    http.headers().frameOptions().sameOrigin();
  }

  @Bean
  @Override
  public UserDetailsService userDetailsService() {
    UserDetails user = User.withUsername(username).password(passwordEncoder().encode(password))
        .roles("USER").build();
    return new InMemoryUserDetailsManager(user);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
