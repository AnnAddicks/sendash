package com.addicks.sendash.admin.domain;

import java.util.Calendar;
import java.util.Date;

import com.addicks.sendash.admin.domain.properties.RepositoryProperties;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by ann on 4/16/15.
 */
public class Status {

  private static final RepositoryProperties repositoryProperties = new RepositoryProperties();
  private static final String firstPowershellFile = repositoryProperties.getLocalRepo() + "/Worker.ps1";	
  
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "EST")
  private final Date timestamp;
  private final boolean runHealthCheckNow;
  private final String healthCheckCronSchedule = " 0 08 * * * ";
  private final Boolean isUpdateNeeded;
  
  private String data = "get-process";
  
  public Status(Boolean isUpdateNeeded) {
    this.isUpdateNeeded = isUpdateNeeded;
    timestamp = Calendar.getInstance().getTime();
    runHealthCheckNow = false;

  }

  public Boolean isUpdateNeeded() {
    return isUpdateNeeded;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getData() {

    return data;
  }

  public String getHealthCheckCronSchedule() {
    return healthCheckCronSchedule;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Status status = (Status) o;

    if (isUpdateNeeded != null ? !isUpdateNeeded.equals(status.isUpdateNeeded)
        : status.isUpdateNeeded != null)
      return false;
    return !(timestamp != null ? !timestamp.equals(status.timestamp) : status.timestamp != null);

  }

  @Override
  public int hashCode() {
    int result = isUpdateNeeded != null ? isUpdateNeeded.hashCode() : 0;
    result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Status{" + "isUpdateNeeded=" + isUpdateNeeded + ", timestamp=" + timestamp + '}';
  }
}
