package com.addicks.sendash.admin.api.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = ResourceController.REQUEST_MAPPING)
@Api(value = "client", description = "CRUD client management.")
public class ResourceController extends AbstractRestHandler {
  private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

  public static final String REQUEST_MAPPING = "/api/me";

  @RequestMapping
  public Principal user(Principal user) {
    // TODO make a class that takes user in through the constructor and limits
    // what is passed through the API.
    return user;
  }

}
