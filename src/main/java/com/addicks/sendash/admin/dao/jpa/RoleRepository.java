package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

}
