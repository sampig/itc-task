/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Chenfeng Zhu
 *
 */
@Entity
@JsonSerialize
@Table(name = "geo_class")
public class GeoClass implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Integer id;
  @Column(name = "class_name")
  private String name;
  @Column(name = "class_code")
  @JsonProperty("Code")
  private String code; // TODO: should it be unique?, unique = true
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "section_id", nullable = false)
  @JsonBackReference
  private Section section;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Section getSection() {
    return section;
  }

  public void setSection(Section section) {
    this.section = section;
  }

  @Override
  public String toString() {
    try {
      return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

}
