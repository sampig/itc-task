/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.zhuzhu.itc.geows.model.ResponseVO;
import org.zhuzhu.itc.geows.service.FileService;
import org.zhuzhu.itc.geows.util.Constants;

/**
 * File controller:
 * <ol>
 * <li>Upload file form.</li>
 * <li>List files.</li>
 * <li>Get a single file.</li>
 * <li>Upload action.</li>
 * </ol>
 * 
 * @author Chenfeng Zhu
 *
 */
@Controller
@RequestMapping(value = "/")
public class FileController {

  @Autowired
  private FileService fileService;

  @RequestMapping(value = "upload", method = RequestMethod.GET)
  public String index() throws IOException {
    return "upload_form";
  }

  @ResponseBody
  @RequestMapping(value = "list-files", method = RequestMethod.GET)
  public ResponseVO listFiles() {
    List<String> list = fileService.loadAll()
        .map(path -> MvcUriComponentsBuilder
            .fromMethodName(FileController.class, "serveFile", path.getFileName().toString())
            .build().toString())
        .collect(Collectors.toList());
    ResponseVO responseVO = new ResponseVO(true);
    responseVO.setMessage("All files.");
    responseVO.setResponse(list);
    return responseVO;
  }

  @ResponseBody
  @RequestMapping(value = "view/{filename:.+}", method = {RequestMethod.GET})
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    try {
      Resource file = fileService.loadAsResource(filename);
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    } catch (RuntimeException e) {
      return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
    }
  }

  @ResponseBody
  @RequestMapping(value = "uploadfile", method = RequestMethod.POST,
      consumes = {"multipart/form-data"})
  public ResponseVO upload(@RequestParam("file") MultipartFile file) {
    String ext = FilenameUtils.getExtension(file.getOriginalFilename());
    // TODO: file.getContentType().contains(Constants.FILE_UPLOAD_ACCEPT_TYPE)
    // application/vnd.ms-excel
    // application/vnd.openoffice.spreadsheet
    if (file != null && Constants.FILE_UPLOAD_ACCEPT_EXTENSION.contains(ext)) {
      try {
        fileService.store(file);
      } catch (RuntimeException e) {
        return new ResponseVO(false, "Failed to upload. " + e.getMessage());
      }
      return new ResponseVO(true, "Upload " + file.getOriginalFilename() + " successfully.");
    }
    return new ResponseVO(false, "Failed to upload file.");
  }

  @RequestMapping(value = "register-form", method = RequestMethod.GET)
  public String registerForm(Model model) throws IOException {
    Map<String, String> files = fileService.loadAll()
        .map(path -> new String[] {MvcUriComponentsBuilder
            .fromMethodName(FileController.class, "serveFile", path.getFileName().toString())
            .build().toString(), path.getFileName().toString()})
        .collect(Collectors.toMap(p -> p[0], p -> p[1]));
    model.addAttribute("files", files);
    return "register_form";
  }

}
