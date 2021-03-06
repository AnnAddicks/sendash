package com.addicks.sendash.admin.service;

import com.addicks.sendash.admin.domain.User;

public interface IRegistrationService {

  /**
   * Create a user that was registered by themselves. This gives them admin
   * privileges to create clients, endpoints, new users, etc.
   */
  User registerUser(User user);

  /**
   * Create the RegisteredUser domain object that is waiting on an email
   * confirmation. <b>This is the one and only method that queues for new user
   * emails!</b>
   * 
   * @param user
   *          User created
   * @param needsPassword
   *          true if created by someone else
   */
  void registerUser(User user, boolean needsPassword);

  /**
   * UUID from the user's email. Throws a ResourceNotFoundException exception if
   * the uuid is invalid.
   * 
   * @param usersUUID
   *          uuid from email
   * 
   * @return boolean if a password needs to be reset - this is the case for when
   *         a user is created by an admin.
   */
  boolean confirmUser(String usersUUID);

}
