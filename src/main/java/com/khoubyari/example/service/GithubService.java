package com.khoubyari.example.service;

import com.khoubyari.example.domain.Payload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ann on 5/20/15.
 */

@Service
public class GithubService {

    private static final Logger log = LoggerFactory.getLogger(GithubService.class);

    public GithubService() {

    }


    public void updateGithubData(Payload payload) {
        //TODO Service check the secret, update the database, and pull the repo


    }


}
