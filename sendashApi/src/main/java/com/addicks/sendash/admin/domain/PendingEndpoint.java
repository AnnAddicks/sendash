package com.addicks.sendash.admin.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PendingEndpoint implements Serializable {

  private static final long serialVersionUID = 4863519288808297293L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID")
  private Client client;

  private String hostName;

  private String apiKey;

  public PendingEndpoint() {

  }

  public PendingEndpoint(PendingEndpoint pendingEndpoint) {
    client = pendingEndpoint.getClient();
    hostName = pendingEndpoint.getHostName();
    apiKey = pendingEndpoint.getApiKey();
  }

  public Long getId() {
    return id;
  }

  public void setPendingEndpointId(Long pendingEndpointId) {
    this.id = pendingEndpointId;
  }

  public Client getClient() {
    return client;
  }

  public void setClientId(Client client) {
    this.client = client;
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
    result = prime * result + ((client.getId() == null) ? 0 : client.getId().hashCode());
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
    if (client.getId() == null) {
      if (other.client.getId() != null)
        return false;
    }
    else if (!client.getId().equals(other.client.getId()))
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
    return "PendingEndpoint [client=" + client + ", hostName=" + hostName + ", apiKey=" + apiKey
        + "]";
  }

}
