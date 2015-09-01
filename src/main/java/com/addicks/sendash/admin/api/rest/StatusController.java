package com.addicks.sendash.admin.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.EndpointStatus;
import com.addicks.sendash.admin.domain.Status;
import com.addicks.sendash.admin.service.StatusService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * Created by ann on 4/16/15.
 */

@RestController
@RequestMapping(value = "/status")
@Api(value = "status", description = "Status update for health check scripts")
public class StatusController extends AbstractRestHandler {
  private static final Logger log = LoggerFactory.getLogger(StatusController.class);

  @Autowired
  private StatusService statusService;

  @RequestMapping(value = "/stub", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Stub that mocks if an update is needed.", notes = "Returns the Status of the scripts:  update or no update.")
  public @ResponseBody Status checkStatusStub(@RequestHeader(value = "API_KEY") String apiKey,
      HttpServletRequest request, HttpServletResponse response) {

    if (apiKey.equalsIgnoreCase("update")) {
      return new Status(Boolean.TRUE);
    }

    return new Status(Boolean.FALSE);
  }

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Check if an update is needed.", notes = "Returns the Status of the scripts:  update or no update.")
  public @ResponseBody Status checkStatus(@RequestBody EndpointStatus endpointStatus,
      HttpServletRequest request, HttpServletResponse response) {

    return statusService.getStatus(endpointStatus);
  }

}
