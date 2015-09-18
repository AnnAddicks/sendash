package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.Endpoint;

/**
 * Created by ann on 5/18/15.
 */

public interface EndpointRepository extends PagingAndSortingRepository<Endpoint, Long> {

  Endpoint findByapiKey(String apiKey);

  Endpoint findByIdAndApiKey(Long id, String apiKey);

}
