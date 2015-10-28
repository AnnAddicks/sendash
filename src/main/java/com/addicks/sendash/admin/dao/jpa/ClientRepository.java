package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.addicks.sendash.admin.domain.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

  @Query("from Client client inner join client.users user where user.id = :id")
  Page<Client> findAll(@Param("id") Long id, Pageable page);
}
