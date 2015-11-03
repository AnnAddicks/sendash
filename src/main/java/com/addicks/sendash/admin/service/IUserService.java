package com.addicks.sendash.admin.service;

import java.util.Collection;

import org.springframework.data.domain.Page;

import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.User;

public interface IUserService extends ICrudService<User> {

  Page<User> findByClientId(User user, Long clientId, Integer page, Integer size);

  void saveClientToUsers(User user, Client client, Collection<Long> userIds);

}
