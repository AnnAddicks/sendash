package com.addicks.sendash.admin.service;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.EndpointRepository;
import com.addicks.sendash.admin.dao.jpa.PendingEndpointRepository;
import com.addicks.sendash.admin.dao.jpa.RejectedEndpointRepository;
import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.PendingEndpoint;
import com.addicks.sendash.admin.domain.RejectedEndpoint;
import com.addicks.sendash.admin.domain.User;

@Service
public class PendingEndpointService implements IPendingEndpointService {
  private static final Logger log = LoggerFactory.getLogger(PendingEndpointService.class);

  @Autowired
  private PendingEndpointRepository pendingEndpointRepository;

  @Autowired
  private EndpointRepository endpointRepository;

  @Autowired
  RejectedEndpointRepository rejectedEndpointRepository;

  @Override
  public PendingEndpoint save(PendingEndpoint object) {
    return pendingEndpointRepository.save(object);
  }

  @Secured({ "ROLE_SUPER" })
  @Override
  public Page<PendingEndpoint> findAll(Integer page, Integer size) {
    return pendingEndpointRepository.findAll(new PageRequest(page, size));
  }

  @Override
  public Page<PendingEndpoint> findAll(User user, Integer page, Integer size) {
    return pendingEndpointRepository.findAll(user.getId(), new PageRequest(page, size));
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

  @Override
  public void reject(Collection<Long> ids) {
    Iterable<PendingEndpoint> pendingEndpoints = pendingEndpointRepository.findAll(ids);
    Collection<RejectedEndpoint> rejectedEndpoints = new ArrayList<>();

    pendingEndpoints
        .forEach(pendingEndpoint -> rejectedEndpoints.add(new RejectedEndpoint(pendingEndpoint)));

    rejectedEndpointRepository.save(rejectedEndpoints);
    pendingEndpointRepository.delete(pendingEndpoints);
  }

}
