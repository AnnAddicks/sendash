package com.addicks.sendash.admin.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.Endpoint;
import com.addicks.sendash.admin.exception.DataFormatException;
import com.addicks.sendash.admin.service.IEndpointService;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = EndpointController.REQUEST_MAPPING)
public class EndpointController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/admin/endpoint";

  @Autowired
  private IEndpointService endpointService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a endpoint resource.", notes = "Returns the URL of the new resource in the Location header.")
  public void createEndpoint(@RequestBody Endpoint endpoint, HttpServletRequest request,
      HttpServletResponse response) {
    Endpoint createdEndpoint = endpointService.create(endpoint);
    response.setHeader("Location",
        request.getRequestURL().append("/").append(createdEndpoint.getId()).toString());
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all users.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<Endpoint> getAllCLients(
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response) {

    Page<Endpoint> endpointPage = endpointService.getAll(page, size);
    response.addHeader("X-Total-Count", "" + endpointPage.getNumberOfElements());
    return endpointPage.getContent();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single endpoint.", notes = "You have to provide a valid endpoint ID.")
  public @ResponseBody Endpoint getEndpoint(
      @ApiParam(value = "The ID of the endpoint.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    Endpoint endpoint = endpointService.findById(id);
    checkResourceFound(endpoint);
    return endpoint;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update an endpoint.", notes = "Provide a valid endpoint ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updateEndpoint(
      @ApiParam(value = "The ID of the existing endpoint resource.", required = true) @PathVariable("id") Long id,
      @RequestBody Endpoint endpoint, HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(endpointService.findById(id));
    if (id != endpoint.getId())
      throw new DataFormatException("ID doesn't match!");
    endpointService.update(endpoint);
  }

  @ApiResponses(value = {
      // @ApiResponse(code = 400, message = "Invalid ID supplied",
      // responseHeader = @ResponseHeader(name = "X-Rack-Cache", description =
      // "Explains whether or not a cache was used", response = Boolean.class)
      // ),
      @ApiResponse(code = 404, message = "Endpoint not found") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete an endpoint.", notes = "You have to provide a valid endpoint ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteEndpoint(
      @ApiParam(value = "The ID of the existing endpoint resource.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(endpointService.findById(id));
    endpointService.delete(id);
  }

}
