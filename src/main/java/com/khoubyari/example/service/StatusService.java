package com.khoubyari.example.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.khoubyari.example.domain.Endpoint;
import com.khoubyari.example.domain.EndpointStatus;
import com.khoubyari.example.domain.Script;
import com.khoubyari.example.domain.Status;

/**
 * Created by ann on 5/20/15.
 */

@Service
public class StatusService {

  private static final Logger log = LoggerFactory.getLogger(StatusService.class);

  @Autowired
  private EndpointService endpointService;

  public StatusService() {

  }

  public Status getStatus(EndpointStatus endpointStatus) {

    if (endpointStatus == null) {
      return null;
    }

    Endpoint endpoint = endpointService.getEndpoint(endpointStatus.getId(),
        endpointStatus.getApiKey());
    log.error("**********************\n" + endpoint);
    Status status = null;

    if (endpoint != null) {
      Date lastUpdatedScriptsOnEndpoint = endpointStatus.getLastUpdatedScripts();

      for (Script script : endpoint.getScripts()) {
        if (script.getScriptLastUpdated().after(lastUpdatedScriptsOnEndpoint)) {
          status = new Status(Boolean.TRUE);
        }
      }
    }

    if (status == null) {
      status = new Status(Boolean.FALSE);
    }
    return status;
  }
}
