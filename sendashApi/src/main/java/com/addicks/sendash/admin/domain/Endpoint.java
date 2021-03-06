package com.addicks.sendash.admin.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * Created by ann on 5/18/15.
 */
@Entity
public class Endpoint implements Serializable {

  private static final long serialVersionUID = -710746835945529033L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "CLIENT_ID")
  private Client client;

  // @Id
  // TODO add composit id
  // https://docs.jboss.org/hibernate/orm/3.5/reference/en-US/html/components.html#components-compositeid
  @Column
  private String hostName;

  @Column
  private String apiKey;

  @Column
  private Date updateScriptRequest;

  @Transient
  // @OneToMany
  // @JoinTable(name="EndpointScript",
  // joinColumns={@JoinColumn(name="endpointId", referencedColumnName="id")},
  // inverseJoinColumns={@JoinColumn(name="scriptId",
  // referencedColumnName="id")})
  private Set<Script> scripts;

  public Endpoint() {

  }

  public Endpoint(PendingEndpoint pendingEndpoint) {
    client = pendingEndpoint.getClient();
    hostName = pendingEndpoint.getHostName();
    apiKey = pendingEndpoint.getApiKey();
  }

  public Endpoint(Long id, Client client, String hostName, String apiKey,
      Date updateScriptRequest) {
    this.id = id;
    this.client = client;
    this.hostName = hostName;
    this.apiKey = apiKey;
    this.updateScriptRequest = updateScriptRequest;
    scripts = new HashSet<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
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

  public Date getUpdateScriptRequest() {
    return updateScriptRequest;
  }

  public void setUpdateScriptRequest(Date updateScriptRequest) {
    this.updateScriptRequest = updateScriptRequest;
  }

  public Set<Script> getScripts() {
    return scripts;
  }

  public void setScripts(Set<Script> scripts) {
    this.scripts = scripts;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Endpoint endpoint = (Endpoint) o;

    if (id != null ? !id.equals(endpoint.id) : endpoint.id != null)
      return false;
    if (client.getId() != null ? !client.getId().equals(endpoint.client.getId())
        : endpoint.client.getId() != null)
      return false;
    if (hostName != null ? !hostName.equals(endpoint.hostName) : endpoint.hostName != null)
      return false;
    if (apiKey != null ? !apiKey.equals(endpoint.apiKey) : endpoint.apiKey != null)
      return false;
    return !(updateScriptRequest != null ? !updateScriptRequest.equals(endpoint.updateScriptRequest)
        : endpoint.updateScriptRequest != null);

  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (client.getId() != null ? client.getId().hashCode() : 0);
    result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
    result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
    result = 31 * result + (updateScriptRequest != null ? updateScriptRequest.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Endpoint{" + "id='" + id + '\'' + ", client.getId()='" + client.getId() + '\''
        + ", hostName='" + hostName + '\'' + ", apiKey='" + apiKey + '\'' + ", updateScriptRequest="
        + updateScriptRequest + '}';
  }
}