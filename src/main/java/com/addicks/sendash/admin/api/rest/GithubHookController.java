package com.addicks.sendash.admin.api.rest;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.Payload;
import com.addicks.sendash.admin.service.GithubService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Created by ann on 5/8/15.
 */
@RestController
@RequestMapping(value = GithubHookController.REQUEST_MAPPING)
@Api(value = "github", description = "Destination for a github web hook for when a repository has had a push event.")
public class GithubHookController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/github";

  private static final Logger log = LoggerFactory.getLogger(GithubHookController.class);

  @Autowired
  private GithubService githubService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Listens for a github webhook to say an update is needed.", notes = "When a github payload is received, we need to update the powershell scripts. ")
  public void pushEvent(@RequestBody Payload payload, HttpServletRequest request,
      HttpServletResponse response) {

    log.debug("*******************************************");
    log.debug("Payload: " + payload);
    log.debug("*******************************************");

    payload.setReceivedTimestamp(Calendar.getInstance().getTime());
    githubService.updateGithubData(payload);

  }

}
