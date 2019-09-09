/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.zhuzhu.itc.geows.bean.Section;
import org.zhuzhu.itc.geows.util.FileUtils;

/**
 * @author Chenfeng Zhu
 *
 */
public class UtilTests {

  Logger logger = Logger.getLogger(UtilTests.class.getName());

  @Test
  public void testFile() throws Exception {
    String dir = "datafile";
    String filename = "template.xlsx";
    Path path = Paths.get(dir);
    Path filepath = path.resolve(filename);
    File file = filepath.toFile();
    FileInputStream fis = new FileInputStream(file);
    List<Section> list = FileUtils.convertExcelToSections(fis);
    for (Section s : list) {
      logger.log(Level.INFO, "section: " + s);
    }
    String output = dir + "/" + "zhuzhu.xlsx";
    try {
      OutputStream fileOutputStream = new FileOutputStream(output);
      FileUtils.convertJobToExcel(list, fileOutputStream);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

}
