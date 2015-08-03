package com.khoubyari.example.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ann on 5/18/15.
 */
// @Entity
/// @Table(name = "hotel")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndpointStatus {

  // @Id
  private String id;

  // @Column
  private String apiKey;

  // @Column
  private Date lastUpdatedScripts;

  public EndpointStatus() {

  }

  public EndpointStatus(String id, String apiKey, Date lastUpdatedScripts) {
    this.id = id;
    this.apiKey = apiKey;
    this.lastUpdatedScripts = lastUpdatedScripts;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public Date getLastUpdatedScripts() {
    return lastUpdatedScripts;
  }

  public void setLastUpdatedScripts(Date lastUpdatedScripts) {
    this.lastUpdatedScripts = lastUpdatedScripts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    EndpointStatus that = (EndpointStatus) o;

    if (id != null ? !id.equals(that.id) : that.id != null)
      return false;
    if (apiKey != null ? !apiKey.equals(that.apiKey) : that.apiKey != null)
      return false;
    return !(lastUpdatedScripts != null ? !lastUpdatedScripts.equals(that.lastUpdatedScripts)
        : that.lastUpdatedScripts != null);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
    result = 31 * result + (lastUpdatedScripts != null ? lastUpdatedScripts.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "EndpointStatus{" + "id='" + id + '\'' + ", ApiKey='" + apiKey + '\''
        + ", lastUpdatedScripts=" + lastUpdatedScripts + '}';
  }
}
