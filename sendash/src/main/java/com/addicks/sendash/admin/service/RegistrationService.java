package com.addicks.sendash.admin.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.RegistrationRepository;
import com.addicks.sendash.admin.domain.Role;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.domain.UserRegistration;
import com.addicks.sendash.admin.exception.ResourceNotFoundException;

@Service
public class RegistrationService implements IRegistrationService {

  @Autowired
  private RegistrationRepository registrationRepository;

  @Autowired
  private IRoleService roleService;

  @Autowired
  private IUserService userService;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${user.create.queue}")
  private String userQueue;

  private static final String EXCLUDE_ROLE = "SUPER";

  @Override
  public User registerUser(User user) {
    Collection<Role> roles = roleService.findAll();
    roles.forEach(role -> {
      if (role.getName().contains(EXCLUDE_ROLE)) {
        roles.remove(role);
      }
    });

    user.setRoles(new HashSet<>(roles));
    User savedUser = userService.create(user);
    this.registerUser(user, false);

    return savedUser;
  }

  @Override
  public void registerUser(User user, boolean needsPassword) {
    UserRegistration userRegistration = new UserRegistration(user, needsPassword);
    registrationRepository.save(userRegistration);

    // Queue up user for an email.
    rabbitTemplate.convertAndSend(userQueue, "ann.addicks@gmail.com," + UUID.randomUUID());
  }

  @Override
  public boolean confirmUser(String usersUUID) {
    UserRegistration userRegistration = registrationRepository.findOne(usersUUID);

    if (userRegistration == null) {
      throw new ResourceNotFoundException("resource not found");
    }

    return userRegistration.needsPassword();
  }

}
