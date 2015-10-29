package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.addicks.sendash.admin.domain.Endpoint;

/**
 * Created by ann on 5/18/15.
 */

public interface EndpointRepository extends PagingAndSortingRepository<Endpoint, Long> {

  Endpoint findByapiKey(String apiKey);

  Endpoint findByIdAndApiKey(Long id, String apiKey);

  @Query("from Endpoint e inner join e.client.users user where user.id = :id")
  Page<Endpoint> findAll(@Param("id") Long userId, Pageable page);
}
