package com.addicks.sendash.admin.service;

import java.util.Set;

import com.addicks.sendash.admin.domain.User;

public interface IUserService extends ICrudService<User> {

  Set<User> getUsersAssociatedWithUser(User user);

}
