package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.ClientRepository;
import com.addicks.sendash.admin.domain.Client;

@Service
public class ClientService implements IClientService {

  @Autowired
  private ClientRepository clientRepository;

  @Override
  public Client save(Client object) {
    return clientRepository.save(object);
  }

  @Override
  public Page<Client> getAll(Integer page, Integer size) {
    return clientRepository.findAll(new PageRequest(page, size));
  }

  @Override
  public Client findById(Long id) {
    return clientRepository.findOne(id);
  }

  @Override
  public void delete(Long id) {
    clientRepository.delete(id);
  }

  @Override
  public Client create(Client object) {
    return clientRepository.save(object);
  }

}
