/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Paths;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.zhuzhu.itc.geows.service.FileService;

/**
 * Test cases:
 * <ol>
 * <li>List all files.</li>
 * <li>Upload a valid file.</li>
 * <li>Upload an invalid file.</li>
 * <li>Get an non-existing file.</li>
 * </ol>
 * 
 * @author Chenfeng Zhu
 *
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WithMockUser
public class FileUploadTests {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FileService fileService;

  @Test
  public void tc1_shouldListAllFiles() throws Exception {
    given(fileService.loadAll())
        .willReturn(Stream.of(Paths.get("first.xlsx"), Paths.get("second.xlsx")));
    this.mockMvc.perform(get("/list-files")).andExpect(status().isOk()).andExpect(content().string(
        Matchers.allOf(containsString("/view/first.xlsx"), containsString("/view/second.xlsx"))));
  }

  @Test
  public void tc2_shouldSaveUploadedFile() throws Exception {
    MockMultipartFile multipartFile = new MockMultipartFile("file", "test_success.xlsx",
        "application/vnd.ms-excel", "Test successfully".getBytes());
    this.mockMvc.perform(multipart("/uploadfile").file(multipartFile).with(csrf()))
        .andExpect(status().isOk()).andExpect(content().string(containsString("true")));
    then(fileService).should().store(multipartFile);
  }

  @Test
  public void tc3_shouldFaliedUploadedFile() throws Exception {
    MockMultipartFile multipartFile =
        new MockMultipartFile("file", "test_fail.txt", "text/plain", "Failed testing".getBytes());
    this.mockMvc.perform(multipart("/uploadfile").file(multipartFile).with(csrf()))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Failed to upload file.")));
  }

  @Test
  public void tc4_should404WhenMissingFile() throws Exception {
    given(fileService.loadAsResource("test.txt")).willThrow(RuntimeException.class);
    this.mockMvc.perform(get("/view/test.txt")).andExpect(status().isNotFound());
  }

}
