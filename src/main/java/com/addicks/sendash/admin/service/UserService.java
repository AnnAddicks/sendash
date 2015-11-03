package com.addicks.sendash.admin.service;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.addicks.sendash.admin.dao.jpa.UserRepository;
import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.User;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  @Transactional
  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Secured({ "ROLE_SUPER" })
  @Override
  public Page<User> findAll(Integer page, Integer size) {
    return userRepository.findAll(new PageRequest(page, size));
  }

  @Override
  public Page<User> findAll(User user, Integer page, Integer size) {
    return userRepository.findAll(user.getId(), new PageRequest(page, size));
  }

  @Override
  public User findById(Long id) {
    return userRepository.findOne(id);
  }

  @Override
  public void delete(Long id) {
    userRepository.delete(id);
  }

  @Override
  public User create(User object) {
    return userRepository.save(object);
  }

  @Override
  public void update(User object) {
    userRepository.save(object);
  }

  @Override
  public Set<User> getUsersAssociatedWithUser(User user) {
    Set<Client> clients = user.getClients();
    final Set<User> users = new HashSet<User>();

    // Check if the users are already attached
    try {
      clients.forEach(client -> users.addAll(client.getUsers()));
    }
    catch (LazyInitializationException e) {
      users.addAll(this.findAll(user, 0, 1000).getContent());
    }
    return users;
  }

}
