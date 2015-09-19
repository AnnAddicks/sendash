package com.addicks.sendash.admin.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PendingEndpoint implements Serializable {

  private static final long serialVersionUID = 4863519288808297293L;

  @Id
  private Long id;

  private Long clientId;

  private String hostName;

  private String apiKey;

  public PendingEndpoint() {

  }

  public Long getId() {
    return id;
  }

  public void setPendingEndpointId(Long pendingEndpointId) {
    this.id = pendingEndpointId;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
    result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
    result = prime * result + ((hostName == null) ? 0 : hostName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PendingEndpoint other = (PendingEndpoint) obj;
    if (apiKey == null) {
      if (other.apiKey != null)
        return false;
    }
    else if (!apiKey.equals(other.apiKey))
      return false;
    if (clientId == null) {
      if (other.clientId != null)
        return false;
    }
    else if (!clientId.equals(other.clientId))
      return false;
    if (hostName == null) {
      if (other.hostName != null)
        return false;
    }
    else if (!hostName.equals(other.hostName))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "PendingEndpoint [clientId=" + clientId + ", hostName=" + hostName + ", apiKey=" + apiKey
        + "]";
  }

}
