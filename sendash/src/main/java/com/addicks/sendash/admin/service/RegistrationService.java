package com.addicks.sendash.admin.service;

import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.domain.User;

@Service
public class RegistrationService implements IRegistrationService {

  @Override
  public User registerUser(User user, Long role) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void registerUser(User user, boolean needsPassword) {
    // TODO Auto-generated method stub

  }

  @Override
  public boolean confirmUser(String usersUUID) {
    // TODO Auto-generated method stub

    return true;
  }

}
