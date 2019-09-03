/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zhuzhu.itc.geows.bean.GeoClass;
import org.zhuzhu.itc.geows.bean.Section;

/**
 * @author Chenfeng Zhu
 *
 */
public class FileUtils {

  private static final Path DIRECTORY = Paths.get(Constants.FILE_PATH_ROOT);

  public static void convertJobToExcel(List<Section> list, OutputStream outputStream) {
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Sections");
    int numColumns = 0;
    Row rowFirst = sheet.createRow(0);
    int rowNum = 1;
    for (Section section : list) {
      Row row = sheet.createRow(rowNum++);
      row.createCell(0).setCellValue(section.getName());
      int colNum = 1;
      for (GeoClass geo : section.getGeoClasses()) {
        row.createCell(colNum).setCellValue(geo.getName());
        row.createCell(colNum + 1).setCellValue(geo.getCode());
        colNum += 2;
      }
      if (colNum > numColumns) {
        numColumns = colNum;
      }
    }
    rowFirst.createCell(0).setCellValue("Section name");
    for (int i = 1; i < (numColumns + 1) / 2; i++) {
      rowFirst.createCell(i * 2 - 1).setCellValue("Class " + i + " name");
      rowFirst.createCell(i * 2).setCellValue("Class " + i + " code");
    }
    try {
      workbook.write(outputStream);
      workbook.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Section> convertExcelToSections(InputStream inputStream) {
    List<Section> list = new ArrayList<>();
    try {
      Workbook workbook = WorkbookFactory.create(inputStream);
      Sheet sheet = workbook.getSheetAt(0);
      Iterator<Row> iteratorRow = sheet.iterator();
      if (iteratorRow.hasNext()) { // The first row is column name.
        iteratorRow.next();
      }
      while (iteratorRow.hasNext()) {
        Section section = new Section();
        Row currentRow = iteratorRow.next();
        Iterator<Cell> iteratorCell = currentRow.iterator();
        if (iteratorCell.hasNext()) {
          section.setName(iteratorCell.next().getStringCellValue());
        }
        while (iteratorCell.hasNext()) {
          GeoClass geo = new GeoClass();
          geo.setName(iteratorCell.next().getStringCellValue());
          geo.setCode(iteratorCell.next().getStringCellValue());
          section.addGeoClass(geo);
        }
        list.add(section);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncryptedDocumentException e) {
      e.printStackTrace();
    } catch (InvalidFormatException e) {
      e.printStackTrace();
    }
    return list;
  }

  public static FileInputStream getFileInputStream(String filename) {
    Path filepath = DIRECTORY.resolve(filename);
    File file = filepath.toFile();
    FileInputStream fis = null;
    try {
      fis = new FileInputStream(file);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return fis;
  }

}
