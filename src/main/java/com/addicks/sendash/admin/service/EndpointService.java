package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.EndpointRepository;
import com.addicks.sendash.admin.domain.Endpoint;

/**
 * Created by ann on 6/10/15.
 */

@Service
public class EndpointService {

  @Autowired
  private EndpointRepository endpointRepository;

  public EndpointService() {

  }

  public Endpoint getEndpoint(String id, String apiKey) {
    return endpointRepository.findByIdAndApiKey(id, apiKey);
  }

  public Iterable<Endpoint> findAll() {
    return endpointRepository.findAll();
  }

}
