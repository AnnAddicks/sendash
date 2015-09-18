package com.addicks.sendash.admin.service;

import org.springframework.data.domain.Page;

public interface ICrudService<T> {

  T save(T object);

  Page<T> getAllUsers(Integer page, Integer size);
}
