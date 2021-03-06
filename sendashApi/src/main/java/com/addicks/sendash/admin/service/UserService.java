package com.addicks.sendash.admin.service;

import java.util.Collection;
import java.util.List;

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
import com.addicks.sendash.admin.exception.ResourceNotFoundException;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private IRoleService roleService;

  @Autowired
  private IClientService clientService;

  @Autowired
  private IRegistrationService registrationService;

  @SuppressWarnings("unused")
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
  public Page<User> findByClientId(User user, Long clientId, Integer page, Integer size) {
    if (user.getClientIds().contains(clientId)) {
      return userRepository.findByClientId(clientId, new PageRequest(page, size));
    }
    throw new ResourceNotFoundException("The client id is not associated with this user.");
  }

  @Override
  public void saveClientToUsers(User user, Client client, Collection<Long> userIds) {
    if (userIds.contains(user.getId())) {
      Collection<User> users = this.findByUserId(userIds);

      for (User userToSave : users) {
        user.addClient(client);
        this.save(userToSave);
      }
    }
    else {
      throw new ResourceNotFoundException("The users are not associated with this user.");
    }

  }

  private Collection<User> findByUserId(Collection<Long> userIds) {
    return userRepository.findByUserIds(userIds);
  }

  @Override
  public User populateAndSaveUser(User userPerformingAction, User newUser, List<Long> clientIds,
      Long roleId) {

    newUser.setRoles(roleService.findById(roleId));
    newUser.setClients(clientService.findByIds(clientIds));
    User savedUser = this.create(newUser);

    // Register user, create email, make the user input a new password.
    registrationService.registerUser(savedUser, true);
    return savedUser;
  }

  @Override
  public void update(User editingUser, User editedUser, List<Long> clientIds, Long role) {
    editedUser.setRoles(roleService.findById(role));
    editedUser.setClients(clientService.findByIds(clientIds));

    this.update(editedUser);
  }
}
