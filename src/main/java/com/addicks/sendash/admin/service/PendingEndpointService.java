package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.PendingEndpointRepository;
import com.addicks.sendash.admin.domain.PendingEndpoint;

@Service
public class PendingEndpointService implements IPendingEndpointService {

  @Autowired
  private PendingEndpointRepository pendingEndpointRepository;

  @Override
  public PendingEndpoint save(PendingEndpoint object) {
    return pendingEndpointRepository.save(object);
  }

  @Override
  public Page<PendingEndpoint> getAll(Integer page, Integer size) {
    return pendingEndpointRepository.findAll(new PageRequest(page, size));
  }

  @Override
  public void approve(PendingEndpoint endpoint) {
    // TODO Auto-generated method stub

  }

}
