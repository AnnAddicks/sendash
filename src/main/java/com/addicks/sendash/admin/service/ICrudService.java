package com.addicks.sendash.admin.service;

import org.springframework.data.domain.Page;

public interface ICrudService<T> {

  T save(T object);

  Page<T> getAll(Integer page, Integer size);

  T findById(Long id);

  void delete(Long id);
}
