package com.addicks.sendash.admin.service;

import org.springframework.data.domain.Page;

import com.addicks.sendash.admin.domain.User;

public interface ICrudService<T> {

  T save(T object);

  Page<T> findAll(User user, Integer page, Integer size);

  Page<T> findAll(Integer page, Integer size);

  T findById(Long id);

  void delete(Long id);

  T create(T object);

  void update(T object);
}
