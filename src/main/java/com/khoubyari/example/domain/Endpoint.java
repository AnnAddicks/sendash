package com.khoubyari.example.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by ann on 5/18/15.
 */
public class Endpoint {

    @Id
    private String id;

    @Column
    private String clientId;

    @Column
    private String hostName;

    @Column
    private String apiKey;

    @Column
    private Date updateScriptRequest;

    public Endpoint() { }

    public Endpoint(String id, String clientId, String hostName, String apiKey, Date updateScriptRequest) {
        this.id = id;
        this.clientId = clientId;
        this.hostName = hostName;
        this.apiKey = apiKey;
        this.updateScriptRequest = updateScriptRequest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
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

    public Date getUpdateScriptRequest() {
        return updateScriptRequest;
    }

    public void setUpdateScriptRequest(Date updateScriptRequest) {
        this.updateScriptRequest = updateScriptRequest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Endpoint endpoint = (Endpoint) o;

        if (id != null ? !id.equals(endpoint.id) : endpoint.id != null) return false;
        if (clientId != null ? !clientId.equals(endpoint.clientId) : endpoint.clientId != null) return false;
        if (hostName != null ? !hostName.equals(endpoint.hostName) : endpoint.hostName != null) return false;
        if (apiKey != null ? !apiKey.equals(endpoint.apiKey) : endpoint.apiKey != null) return false;
        return !(updateScriptRequest != null ? !updateScriptRequest.equals(endpoint.updateScriptRequest) : endpoint.updateScriptRequest != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (hostName != null ? hostName.hashCode() : 0);
        result = 31 * result + (apiKey != null ? apiKey.hashCode() : 0);
        result = 31 * result + (updateScriptRequest != null ? updateScriptRequest.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "id='" + id + '\'' +
                ", clientId='" + clientId + '\'' +
                ", hostName='" + hostName + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", updateScriptRequest=" + updateScriptRequest +
                '}';
    }
}