package com.khoubyari.example.service;

import com.khoubyari.example.dao.jpa.GithubPayloadDao;
import com.khoubyari.example.domain.Payload;
import com.khoubyari.example.domain.Script;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ann on 5/20/15.
 */

@Service
public class GithubService {

    private static final Logger log = LoggerFactory.getLogger(GithubService.class);

    @Autowired
    private GithubPayloadDao githubPayloadDao;

    @Autowired
    private ScriptService scriptService;

    public GithubService() {

    }


    public void updateGithubData(Payload payload) {
        //TODO Service check the secret, update the database, and pull the repo
        List<Script> scripts = new ArrayList<>();
        Script script;
        Date lastUpdated = payload.getReceivedTimestamp();
        for(String scriptName : payload.getAllFilesModified()) {
            script = scriptService.getScriptByName(scriptName);
            script.setScriptLastUpdated(lastUpdated);
            scripts.add(script);
        }


        scriptService.saveScripts(scripts);
        githubPayloadDao.save(payload);





    }




}
