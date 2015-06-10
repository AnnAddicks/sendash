package com.khoubyari.example.service;

import com.khoubyari.example.dao.jpa.EndpointRepository;
import com.khoubyari.example.domain.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ann on 6/10/15.
 */

@Service
public class EndpointService {

    @Autowired
    private EndpointRepository endpointRepository;

    public EndpointService() {

    }

    public Endpoint getEndpoint(String id, String apiKey) {
        return endpointRepository.findByIdAndApiKey(id, apiKey);
    }



}

