package com.addicks.sendash.admin.api.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = ResourceController.REQUEST_MAPPING)
@Api(value = "client", description = "CRUD client management.")
public class ResourceController extends AbstractRestHandler {
  private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

  public static final String REQUEST_MAPPING = "/resource";

  @RequestMapping(method = RequestMethod.GET)
  public Map<String, Object> home() {
    log.error("made it home!");
    Map<String, Object> model = new HashMap<String, Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Hello World");

    return model;
  }
}
