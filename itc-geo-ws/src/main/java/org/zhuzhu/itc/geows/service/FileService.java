/*
 * Copyright (c) 2019.
 */
package org.zhuzhu.itc.geows.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.zhuzhu.itc.geows.util.Constants;

/**
 * A service to deal with files.
 * 
 * @author spring.io
 * @author Chenfeng Zhu
 *
 */
@Service
public class FileService {

  private final Path rootLocation = Paths.get(Constants.FILE_PATH_ROOT);

  public void store(MultipartFile file) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    try {
      if (file.isEmpty()) {
        throw new RuntimeException("Failed to store empty file " + filename);
      }
      if (filename.contains("..")) {
        throw new RuntimeException(
            "Cannot store file with relative path outside current directory " + filename);
      }
      if (Files.exists(Paths.get(Constants.FILE_PATH_ROOT, filename))) {
        // Do NOT replace the original file.
        throw new RuntimeException(filename + " exists.");
      }
      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, rootLocation.resolve(filename),
            StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file " + filename, e);
    }
  }

  public Stream<Path> loadAll() {
    try {
      return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation))
          .map(rootLocation::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Failed to read stored files", e);
    }

  }

  public Path load(String filename) {
    return rootLocation.resolve(filename);
  }

  public Resource loadAsResource(String filename) {
    try {
      Path file = load(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read file: " + filename);

      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filename, e);
    }
  }

  public void deleteAll() {
    FileSystemUtils.deleteRecursively(rootLocation.toFile());
  }

  public void init() {
    try {
      if (!Files.isDirectory(rootLocation)) {
        Files.createDirectories(rootLocation);
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize storage", e);
    }
  }

}
