package com.addicks.sendash.admin.api.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.json.Me;
import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = ResourceController.REQUEST_MAPPING)
@Api(value = "client", description = "CRUD client management.")
public class ResourceController extends AbstractRestHandler {
  private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

  public static final String REQUEST_MAPPING = "/api/me";

  @RequestMapping
  public Me user(Principal user) {
    return new Me((OAuth2Authentication) user);
  }

}
