package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.RegistrationRepository;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.domain.UserRegistration;
import com.addicks.sendash.admin.exception.ResourceNotFoundException;

@Service
public class RegistrationService implements IRegistrationService {

  @Autowired
  private RegistrationRepository registrationRepository;

  @Override
  public User registerUser(User user, Long role) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void registerUser(User user, boolean needsPassword) {
    UserRegistration userRegistration = new UserRegistration(user, needsPassword);
    registrationRepository.save(userRegistration);
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
