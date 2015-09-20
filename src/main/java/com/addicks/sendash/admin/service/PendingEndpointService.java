package com.addicks.sendash.admin.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.EndpointRepository;
import com.addicks.sendash.admin.dao.jpa.PendingEndpointRepository;
import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.PendingEndpoint;

@Service
public class PendingEndpointService implements IPendingEndpointService {

  @Autowired
  private PendingEndpointRepository pendingEndpointRepository;

  @Autowired
  private EndpointRepository endpointRepository;

  @Override
  public PendingEndpoint save(PendingEndpoint object) {
    return pendingEndpointRepository.save(object);
  }

  @Override
  public Page<PendingEndpoint> getAll(Integer page, Integer size) {
    return pendingEndpointRepository.findAll(new PageRequest(page, size));
  }

  @Override
  public void approve(Collection<Long> idsToApprove) {
    Iterable<PendingEndpoint> pendingEndpoints = pendingEndpointRepository.findAll(idsToApprove);

    Collection<Endpoint> approvedEndpoints = new ArrayList<>();
    Endpoint endpoint;
    for (PendingEndpoint pendingEndpoint : pendingEndpoints) {
      endpoint = new Endpoint(pendingEndpoint);

      approvedEndpoints.add(endpoint);
    }

    pendingEndpointRepository.delete(pendingEndpoints);
    endpointRepository.save(approvedEndpoints);
  }

  @Override
  public PendingEndpoint findById(Long id) {
    return pendingEndpointRepository.findOne(id);
  }

  @Override
  public void delete(Long id) {
    pendingEndpointRepository.delete(id);

  }

  @Override
  public PendingEndpoint create(PendingEndpoint object) {
    return pendingEndpointRepository.save(object);
  }

  @Override
  public void update(PendingEndpoint object) {
    pendingEndpointRepository.save(object);
  }

}
