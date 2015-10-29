package com.addicks.sendash.admin.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
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

}
