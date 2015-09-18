package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.addicks.sendash.admin.dao.jpa.ClientRepository;
import com.addicks.sendash.admin.domain.Client;

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

}
