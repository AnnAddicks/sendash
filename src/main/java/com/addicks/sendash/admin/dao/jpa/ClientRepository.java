package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.Client;

public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

}
