package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.Endpoint;

public interface PendingEndpointRepository extends PagingAndSortingRepository<Endpoint, String> {

}
