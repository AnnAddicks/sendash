package com.addicks.sendash.admin.service;

import com.addicks.sendash.admin.domain.User;

public interface IRegistrationService {

  User registerUser(User user, Long role);

  void confirmUser(String usersUUID);

}
