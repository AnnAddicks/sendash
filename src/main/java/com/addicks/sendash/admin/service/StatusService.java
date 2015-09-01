package com.addicks.sendash.admin.service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.EndpointStatus;
import com.addicks.sendash.admin.domain.Script;
import com.addicks.sendash.admin.domain.Status;
import com.addicks.sendash.admin.domain.properties.RepositoryProperties;

/**
 * Created by ann on 5/20/15.
 */

@Service
@EnableConfigurationProperties
public class StatusService {

  private static final Logger log = LoggerFactory.getLogger(StatusService.class);

  @Autowired
  private EndpointService endpointService;

  @Autowired
  private ScriptService scriptService;

  @Autowired
  private RepositoryProperties repositoryProperties;

  private static final String FIRST_POWERSHELL_FILE = "/Worker.ps1";

  public StatusService() {

  }

  public Status getStatus(EndpointStatus endpointStatus) {

    if (endpointStatus == null) {
      return null;
    }

    Endpoint endpoint = endpointService.getEndpoint(endpointStatus.getId(),
        endpointStatus.getApiKey());
    Iterable<Script> scripts = scriptService.getAllScripts();
    Status status = null;

    if (endpoint != null && scripts != null) {
      Date lastUpdatedScriptsOnEndpoint = endpointStatus.getLastUpdatedScripts();
      log.error("***********************");
      log.error("lastUpdatedScriptsOnEndpoint: " + lastUpdatedScriptsOnEndpoint);
      log.error("scripts: " + scripts);

      for (Script script : scripts) {
        log.error("Script: " + script);
        if (script.getScriptLastUpdated().after(lastUpdatedScriptsOnEndpoint)) {
          status = new Status(Boolean.TRUE);
          break;
        }
      }
    }

    if (status == null) {
      status = new Status(Boolean.FALSE);
    }

    status.setData(readWorkerFile());
    log.error("Data: " + status.getData());
    log.error("***********************");
    return status;
  }

  private String readWorkerFile() {
    byte[] encoded;
    try {
      encoded = Files
          .readAllBytes(Paths.get(repositoryProperties.getLocalRepo() + FIRST_POWERSHELL_FILE));
      return new String(encoded, Charset.forName("UTF-8"));
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return new String("Error reading the first powershell file.");
  }
}
