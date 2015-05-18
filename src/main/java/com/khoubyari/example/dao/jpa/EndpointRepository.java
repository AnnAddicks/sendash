package com.khoubyari.example.dao.jpa;

import com.khoubyari.example.domain.Endpoint;
import com.khoubyari.example.domain.Script;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by ann on 5/18/15.
 */

public interface EndpointRepository extends PagingAndSortingRepository<Endpoint, String> {

    Endpoint findByapiKey(String apiKey);

}
