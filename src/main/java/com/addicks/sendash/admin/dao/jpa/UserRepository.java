package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  User findByEmail(String email);
}
