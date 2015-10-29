package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.Payload;

/**
 * Created by ann on 5/21/15.
 */
public interface GithubPayloadDao extends PagingAndSortingRepository<Payload, Integer> {

}
