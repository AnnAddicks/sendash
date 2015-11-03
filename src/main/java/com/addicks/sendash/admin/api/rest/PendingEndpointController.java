package com.addicks.sendash.admin.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.PendingEndpoint;
import com.addicks.sendash.admin.domain.PendingEndpointReviewRequest;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.exception.DataFormatException;
import com.addicks.sendash.admin.service.IPendingEndpointService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = PendingEndpointController.REQUEST_MAPPING)
@Api(value = "pendingEndpoint", description = "CRUD pending endpoint management.")
public class PendingEndpointController extends AbstractRestHandler {
  private static final Logger log = LoggerFactory.getLogger(PendingEndpointController.class);

  public static final String REQUEST_MAPPING = "/api/admin/pending-endpoint";

  @Autowired
  private IPendingEndpointService pendingEndpointService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a pendingEndpoint resource.", notes = "Returns the URL of the new resource in the Location header.")
  public void creatependingEndpoint(@RequestBody PendingEndpoint pendingEndpoint,
      HttpServletRequest request, HttpServletResponse response) {
    PendingEndpoint createdPendingEndpoint = pendingEndpointService.create(pendingEndpoint);
    response.setHeader("Location",
        request.getRequestURL().append("/").append(createdPendingEndpoint.getId()).toString());
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all pending endpoints.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<PendingEndpoint> getAllPendingEndpoints(
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response, OAuth2Authentication oauthUser) {

    User user = getUserFromAuthentication(oauthUser);
    Page<PendingEndpoint> pendingEndpointPage = pendingEndpointService.findAll(user, page, size);
    response.addHeader("X-Total-Count", "" + pendingEndpointPage.getNumberOfElements());
    return pendingEndpointPage.getContent();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single pendingEndpoint.", notes = "You have to provide a valid pendingEndpoint ID.")
  public @ResponseBody PendingEndpoint getpendingEndpoint(
      @ApiParam(value = "The ID of the pendingEndpoint.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    PendingEndpoint pendingEndpoint = pendingEndpointService.findById(id);
    checkResourceFound(pendingEndpoint);
    return pendingEndpoint;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a pendingEndpoint.", notes = "Provide a valid pendingEndpoint ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updatependingEndpoint(
      @ApiParam(value = "The ID of the existing pendingEndpoint resource.", required = true) @PathVariable("id") Long id,
      @RequestBody PendingEndpoint pendingEndpoint, HttpServletRequest request,
      HttpServletResponse response) {
    checkResourceFound(pendingEndpointService.findById(id));
    if (id != pendingEndpoint.getId())
      throw new DataFormatException("ID doesn't match!");
    pendingEndpointService.update(pendingEndpoint);
  }

  @ApiResponses(value = {
      // @ApiResponse(code = 400, message = "Invalid ID supplied",
      // responseHeader = @ResponseHeader(name = "X-Rack-Cache", description =
      // "Explains whether or not a cache was used", response = Boolean.class)
      // ),
      @ApiResponse(code = 404, message = "PendingEndpoint not found") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a pendingEndpoint.", notes = "You have to provide a valid pendingEndpoint ID in the URL. Once deleted the resource can not be recovered.")
  public void deletePendingEndpoint(
      @ApiParam(value = "The ID of the existing pending endpoint resource.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(pendingEndpointService.findById(id));
    pendingEndpointService.delete(id);
  }

  @RequestMapping(value = "/approve", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Approve pendingEndpoint(s).", notes = "Provide one or more valid pendingEndpoint IDs in  the payload.")
  public void approvePendingEndpoint(
      @ApiParam(value = "The ID of the existing pending endpoint resource(s) to approve.", required = true) @RequestBody PendingEndpointReviewRequest request,
      HttpServletResponse response) {

    pendingEndpointService.approve(request.getIds());
  }

  @RequestMapping(value = "/reject", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Reject pendingEndpoint(s).", notes = "Provide one or more valid pendingEndpoint IDs in  the payload.")
  public void rejectPendingEndpoint(
      @ApiParam(value = "The ID of the existing pending endpoint resource(s) to reject.", required = true) @RequestBody PendingEndpointReviewRequest request,
      HttpServletResponse response) {

    pendingEndpointService.reject(request.getIds());
  }

}
