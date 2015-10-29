package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.EndpointRepository;
import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.User;

/**
 * Created by ann on 6/10/15.
 */

@Service
public class EndpointService implements IEndpointService {

  @Autowired
  private EndpointRepository endpointRepository;

  public EndpointService() {

  }

  public Endpoint getEndpoint(Long id, String apiKey) {
    return endpointRepository.findByIdAndApiKey(id, apiKey);
  }

  public Iterable<Endpoint> findAll() {
    return endpointRepository.findAll();
  }

  @Override
  public Endpoint save(Endpoint object) {
    return endpointRepository.save(object);
  }

  @Secured({ "ROLE_SUPER" })
  @Override
  public Page<Endpoint> findAll(Integer page, Integer size) {
    return endpointRepository.findAll(new PageRequest(page, size));
  }

  @Override
  public Page<Endpoint> findAll(User user, Integer page, Integer size) {
    return endpointRepository.findAll(user.getId(), new PageRequest(page, size));
  }

  @Override
  public Endpoint findById(Long id) {
    return endpointRepository.findOne(id);
  }

  @Override
  public void delete(Long id) {
    endpointRepository.delete(id);
  }

  @Override
  public Endpoint create(Endpoint object) {
    return endpointRepository.save(object);
  }

  @Override
  public void update(Endpoint object) {
    endpointRepository.save(object);
  }

}
