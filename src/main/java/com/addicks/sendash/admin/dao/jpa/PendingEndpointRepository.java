package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.PendingEndpoint;

public interface PendingEndpointRepository
    extends PagingAndSortingRepository<PendingEndpoint, Long> {

}
