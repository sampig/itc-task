/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author Chenfeng Zhu
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthenticationTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void tc1_loginWithValidUserThenAuthenticated() throws Exception {
    FormLoginRequestBuilder login =
        formLogin().user("username", "zhuzhu").password("password", "asdfasdf");
    mockMvc.perform(login).andExpect(authenticated().withUsername("zhuzhu"));
  }

  @Test
  public void tc2_loginWithInvalidUserThenUnauthenticated() throws Exception {
    FormLoginRequestBuilder login = formLogin().user("invalid").password("invalidpassword");
    mockMvc.perform(login).andExpect(unauthenticated());
  }

  @Test
  public void tc3_accessUnsecuredResourceThenOk() throws Exception {
    mockMvc.perform(get("/index")).andExpect(status().isOk());
  }

  @Test
  public void tc4_accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
    mockMvc.perform(get("/list-files")).andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrlPattern("**/login"));
  }

  @Test
  @WithMockUser
  public void tc5_accessSecuredResourceAuthenticatedThenOk() throws Exception {
    mockMvc.perform(get("/list-files")).andExpect(status().isOk());
  }

}
