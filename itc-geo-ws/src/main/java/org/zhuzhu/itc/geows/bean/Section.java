/*
 * Copyright (c) 2019 - Chenfeng Zhu.
 */
package org.zhuzhu.itc.geows.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Chenfeng Zhu
 *
 */
@Entity
@JsonSerialize
@Table(name = "section")
public class Section implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonIgnore
  private Integer id;
  @Column(name = "section_name")
  private String name;
  @OneToMany(mappedBy = "section", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonProperty("geologicalClasses")
  @JsonManagedReference
  private List<GeoClass> geoClasses;
  // TODO: should it be referenced to a job?
  // @ManyToOne(fetch = FetchType.LAZY)
  // @JoinColumn(name = "job_id", nullable = false)
  // @JsonIgnore
  // @JsonBackReference
  // private Job job;

  public Section() {
    geoClasses = new ArrayList<>();
  }

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

  public List<GeoClass> getGeoClasses() {
    return geoClasses;
  }

  public void setGeoClasses(List<GeoClass> geoClasses) {
    this.geoClasses = geoClasses;
  }

  public void addGeoClass(GeoClass geo) {
    if (geoClasses == null) {
      geoClasses = new ArrayList<>(0);
    }
    geoClasses.add(geo);
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
