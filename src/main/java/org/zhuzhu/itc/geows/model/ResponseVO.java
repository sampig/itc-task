/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Chenfeng Zhu
 *
 */
@JsonSerialize
public class ResponseVO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Boolean result;
  private String message;
  private Object response;

  public ResponseVO() {}

  public ResponseVO(Boolean result) {
    this.result = result;
  }

  public ResponseVO(Boolean result, String message) {
    this.result = result;
    this.message = message;
  }

  public Boolean getResult() {
    return result;
  }

  public void setResult(Boolean result) {
    this.result = result;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getResponse() {
    return response;
  }

  public void setResponse(Object response) {
    this.response = response;
  }

}
