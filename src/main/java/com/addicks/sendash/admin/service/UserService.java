package com.addicks.sendash.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.UserRepository;
import com.addicks.sendash.admin.domain.User;

@Service
public class UserService implements IUserService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

}
