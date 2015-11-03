package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.addicks.sendash.admin.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {

  @Query("Select u from User u INNER JOIN FETCH u.clients where u.email= :email")
  User findByEmail(@Param("email") String email);

  @Query("Select u from User u inner join u.clients c where c.id = :id")
  Page<User> findAll(@Param("id") Long id, Pageable page);
}
