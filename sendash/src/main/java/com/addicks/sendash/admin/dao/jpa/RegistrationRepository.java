package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.UserRegistration;

public interface RegistrationRepository
    extends PagingAndSortingRepository<UserRegistration, String> {

}
