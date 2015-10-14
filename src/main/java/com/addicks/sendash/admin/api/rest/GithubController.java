package com.addicks.sendash.admin.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.Payload;
import com.addicks.sendash.admin.service.GithubService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = GithubController.REQUEST_MAPPING)
public class GithubController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/admin/github";

  @Autowired
  private GithubService githubService;

  @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "returns all the payloads from github.", notes = " ")
  public Iterable<Payload> getLog() {
    return githubService.getPayloadHistory();
  }
}
