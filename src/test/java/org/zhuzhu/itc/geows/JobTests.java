/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.zhuzhu.itc.geows.bean.Job;
import org.zhuzhu.itc.geows.service.JobService;
import org.zhuzhu.itc.geows.util.Constants.JobStatus;

/**
 * @author Chenfeng Zhu
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JobTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JobService jobService;

  @Test
  public void tc1_shouldListAllJobs() throws Exception {
    String filename1 = "template.xlsx";
    String filename2 = "test.xlsx";
    Job job1 = new Job();
    job1.setId(1);
    job1.setFilename(filename1);
    job1.setGmtAdd(Instant.now());
    job1.setGmtUpdate(Instant.now());
    job1.setStatus(JobStatus.REGISTERED);
    Job job2 = new Job();
    job2.setId(2);
    job2.setFilename(filename2);
    job2.setGmtAdd(Instant.now());
    job2.setGmtUpdate(Instant.now());
    job2.setStatus(JobStatus.REGISTERED);
    List<Job> list = Arrays.asList(job1, job2);
    given(jobService.listAllJobsByPage(10, 0)).willReturn(list);
    mockMvc.perform(get("/list-jobs?size=10&page=0")).andDo(print()).andExpect(status().isOk())
        .andExpect(
            content().string(Matchers.allOf(containsString(filename1), containsString(filename2))));
  }

  @Test
  public void tc2_shouldAddJob() throws Exception {
    JSONObject jobJsonObject = new JSONObject();
    try {
      jobJsonObject.put("filename", "test.xlsx");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    mockMvc
        .perform(post("/register-job").contentType(MediaType.APPLICATION_JSON)
            .content(jobJsonObject.toString()))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("true")));
    mockMvc.perform(get("/register-job")).andDo(print()).andExpect(status().isOk());
  }

}
