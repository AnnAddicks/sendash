package com.addicks.sendash.admin.service;

import org.springframework.data.domain.Page;

import com.addicks.sendash.admin.domain.User;

public interface IUserService {

  User save(User user);

  Page<User> getAllUsers(Integer page, Integer size);
}
