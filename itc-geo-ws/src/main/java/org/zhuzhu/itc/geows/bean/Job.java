/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.bean;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Chenfeng Zhu
 *
 */
@Entity
@JsonSerialize
@JsonPropertyOrder({"id", "filename", "gmtAdd", "gmtUpdate", "status", "result"})
@Table(name = "file_job")
public class Job implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("Job ID")
  private Integer id;
  @Column(name = "filename")
  private String filename;
  @Column(name = "gmt_add")
  @JsonProperty("Create time:")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z",
      timezone = "GMT+2")
  private Instant gmtAdd;
  @Column(name = "gmt_update")
  @JsonProperty("Update time:")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss Z",
      timezone = "GMT+2")
  private Instant gmtUpdate;
  // TODO:
  // @Column(name = "gmt_expect")
  // @JsonProperty("Expect start time:")
  // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
  // private Instant gmtExpectStart;
  @Column(name = "status", nullable = true)
  private String status;
  @Column(name = "result", nullable = true, length = 10240)
  private String result;
  // TODO: can it list all its section?
  // @OneToMany(mappedBy = "job", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  // @JsonProperty("Section list")
  // @JsonManagedReference
  // private List<Section> sections;


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public Instant getGmtAdd() {
    return gmtAdd;
  }

  public void setGmtAdd(Instant gmtAdd) {
    this.gmtAdd = gmtAdd;
  }

  public Instant getGmtUpdate() {
    return gmtUpdate;
  }

  public void setGmtUpdate(Instant gmtUpdate) {
    this.gmtUpdate = gmtUpdate;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  @Override
  public String toString() {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.registerModule(new JavaTimeModule());
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

}
