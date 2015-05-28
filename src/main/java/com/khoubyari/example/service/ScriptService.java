package com.khoubyari.example.service;

import com.khoubyari.example.dao.jpa.EndpointRepository;
import com.khoubyari.example.dao.jpa.HotelRepository;
import com.khoubyari.example.dao.jpa.ScriptRepository;
import com.khoubyari.example.domain.Endpoint;
import com.khoubyari.example.domain.EndpointStatus;
import com.khoubyari.example.domain.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ann on 5/13/15.
 */
@Service
public class ScriptService {
    private static final Logger log = LoggerFactory.getLogger(ScriptService.class);

    @Autowired
    private ScriptRepository scriptRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    public ScriptService() {
    }

    public boolean isUpdateNeeded(EndpointStatus endpointStatus) {

        Date lastUpdated = endpointStatus.getLastUpdatedScripts();
        Endpoint endpoint = endpointRepository.findByapiKey(endpointStatus.getApiKey());

        return lastUpdated.before(endpoint.getUpdateScriptRequest());

    }

    public Script getScriptByName(String name) {
        return scriptRepository.findByScriptName(name);
    }
    
    public void saveScripts(Iterable<Script> scripts) {
        if(scripts != null)
            scriptRepository.save(scripts);
    }

}
