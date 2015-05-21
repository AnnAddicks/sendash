package com.khoubyari.example.dao.jpa;

import com.khoubyari.example.domain.Endpoint;
import com.khoubyari.example.domain.Payload;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by ann on 5/21/15.
 */
public interface GithubPayloadDao extends PagingAndSortingRepository<Payload, String> {


}
