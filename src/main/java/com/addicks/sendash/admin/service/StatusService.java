package com.addicks.sendash.admin.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.EndpointStatus;
import com.addicks.sendash.admin.domain.Script;
import com.addicks.sendash.admin.domain.Status;

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
    log.error("**********************\n" + endpointService.findAll());
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
