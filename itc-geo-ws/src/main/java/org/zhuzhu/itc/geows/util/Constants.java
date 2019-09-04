/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chenfeng Zhu
 *
 */
public abstract class Constants {

  public static final String FILE_PATH_ROOT = "datafile";

  public static final String FILE_XLSX_DEFAULT = "template.xlsx";

  public static final Integer PAGE_SIZE = 10;

  public static final Integer PAGE_NUMBER = 0;

  public static final List<String> FILE_UPLOAD_ACCEPT_EXTENSION = Arrays.asList("xlsx", "xls");
  public static final String FILE_UPLOAD_ACCEPT_TYPE = "excel"; // spreadsheet

  public static abstract class JobStatus {
    public static final String REGISTERED = "registered";
    public static final String IN_PROGRESS = "in progress";
    public static final String FINISHED = "finished";
  }

}
