package com.addicks.sendash.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.addicks.sendash.admin.dao.jpa.GithubPayloadDao;
import com.addicks.sendash.admin.domain.Payload;
import com.addicks.sendash.admin.domain.Script;

/**
 * Created by ann on 5/20/15.
 */

@Service
public class GithubService {

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(GithubService.class);

  @Autowired
  private GithubPayloadDao githubPayloadDao;

  @Autowired
  private ScriptService scriptService;

  @Autowired
  private GitService gitmanager;

  @Autowired
  private FileService fileService;

  public GithubService() {

  }

  @Transactional
  public void updateGithubData(Payload payload) {
    gitmanager.updateLocalRepository();
    fileService.createZip();

    List<Script> scripts = new ArrayList<>();
    Script script;
    Date lastUpdated = payload.getReceivedTimestamp();
    for (String scriptName : payload.getAllFilesModified()) {
      script = scriptService.getScriptByName(scriptName);

      if (script != null) {
        script.setScriptLastUpdated(lastUpdated);
        scripts.add(script);
      }
      else {
        script = new Script();
        script.setScriptName(scriptName);
        script.setScriptLastUpdated(lastUpdated);
        scripts.add(script);
      }
    }

    payload.prepareCommitsForSave();
    githubPayloadDao.save(payload);
    scriptService.saveScripts(scripts);
  }

  @Transactional
  public Iterable<Payload> getPayloadHistory() {
    Iterable<Payload> payloads = githubPayloadDao.findAll();

    for (Payload payload : payloads) {
      payload.getCommits();
    }

    return payloads;
  }

}
