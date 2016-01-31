package com.addicks.sendash.admin.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.User;

public interface IClientService extends ICrudService<Client> {

  Client create(User user, Client client, Collection<Long> users);

  Set<Client> findByIds(List<Long> clientIds);

}
