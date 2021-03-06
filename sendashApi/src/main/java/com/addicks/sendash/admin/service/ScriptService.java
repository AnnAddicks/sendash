package com.addicks.sendash.admin.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.EndpointRepository;
import com.addicks.sendash.admin.dao.jpa.ScriptRepository;
import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.EndpointStatus;
import com.addicks.sendash.admin.domain.Script;

/**
 * Created by ann on 5/13/15.
 */
@Service
public class ScriptService {
  private static final Logger log = LoggerFactory.getLogger(ScriptService.class);

  @Autowired
  private ScriptRepository scriptRepository;

  @Autowired
  private EndpointRepository endpointRepository;

  public ScriptService() {
  }

  public boolean isUpdateNeeded(EndpointStatus endpointStatus) {

    Date lastUpdated = endpointStatus.getLastUpdatedScripts();
    Endpoint endpoint = endpointRepository.findByapiKey(endpointStatus.getApiKey());

    return lastUpdated.before(endpoint.getUpdateScriptRequest());

  }

  public Script getScriptByName(String name) {
    if (name == null || name.isEmpty())
      return null;

    return scriptRepository.findByScriptName(name);
  }

  public void saveScripts(Iterable<Script> scripts) {
    if (scripts != null)
      scriptRepository.save(scripts);
  }

  public void save(Script script) {
    if (script != null) {
      scriptRepository.save(script);
    }
  }

  public Iterable<Script> getAllScripts() {
    return scriptRepository.findAll();
  }

}
