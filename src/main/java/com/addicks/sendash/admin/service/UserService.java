package com.addicks.sendash.admin.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.UserRepository;
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

  @Override
  public Page<User> getAllUsers(Integer page, Integer size) {
    return userRepository.findAll(new PageRequest(page, size));
  }

}
