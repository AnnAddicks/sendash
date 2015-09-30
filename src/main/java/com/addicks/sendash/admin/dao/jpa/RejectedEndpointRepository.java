package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.RejectedEndpoint;

public interface RejectedEndpointRepository
    extends PagingAndSortingRepository<RejectedEndpoint, Long> {

}
