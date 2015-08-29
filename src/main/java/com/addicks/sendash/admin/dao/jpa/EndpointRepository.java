package com.addicks.sendash.admin.dao.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.domain.Script;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ann on 5/18/15.
 */

public interface EndpointRepository extends PagingAndSortingRepository<Endpoint, String> {

    Endpoint findByapiKey(String apiKey);

    Endpoint findByIdAndApiKey(String id, String apiKey);

}
