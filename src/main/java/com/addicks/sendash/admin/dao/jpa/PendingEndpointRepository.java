package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.addicks.sendash.admin.domain.PendingEndpoint;

public interface PendingEndpointRepository
    extends PagingAndSortingRepository<PendingEndpoint, Long> {

  @Query("Select p from PendingEndpoint p inner join p.client.users user where user.id = :id")
  Page<PendingEndpoint> findAll(@Param("id") Long userId, Pageable page);
}
