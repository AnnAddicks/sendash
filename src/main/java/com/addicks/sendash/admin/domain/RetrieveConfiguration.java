package com.addicks.sendash.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrieveConfiguration {

  @JsonProperty("_page")
  private int page;

  @JsonProperty("_perPage")
  private int perPage;

  @JsonProperty("_sortDir")
  private String sortDir;

  @JsonProperty("_sortField")
  private String sortField;

  public RetrieveConfiguration() {

  }

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public int getPerPage() {
    return perPage;
  }

  public void setPerPage(int perPage) {
    this.perPage = perPage;
  }

  public String getSortDir() {
    return sortDir;
  }

  public void setSortDir(String sortDir) {
    this.sortDir = sortDir;
  }

  public String getSortField() {
    return sortField;
  }

  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

}
