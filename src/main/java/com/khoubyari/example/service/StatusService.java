package com.khoubyari.example.service;

import com.khoubyari.example.domain.EndpointStatus;
import com.khoubyari.example.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ann on 5/20/15.
 */

@Service
public class StatusService {

    private static final Logger log = LoggerFactory.getLogger(StatusService.class);

    @Autowired
    private ScriptService scriptService;

    public StatusService() {

    }

    public Status getStatus(EndpointStatus endpointStatus) {
        //TODO populate a status - get data from db, use script service to determine if the script needs updating.

        if(endpointStatus == null) {
            return null;
        }

        return null;
    }
}
