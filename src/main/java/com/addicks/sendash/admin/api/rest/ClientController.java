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

import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.exception.DataFormatException;
import com.addicks.sendash.admin.service.CustomUserDetailsService.UserRepositoryUserDetails;
import com.addicks.sendash.admin.service.IClientService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = ClientController.REQUEST_MAPPING)
@Api(value = "client", description = "CRUD client management.")
public class ClientController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/admin/client";

  private static final Logger log = LoggerFactory.getLogger(ClientController.class);

  @Autowired
  private IClientService clientService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a client resource.", notes = "Returns the URL of the new resource in the Location header.")
  public void createClient(@RequestBody Client client, HttpServletRequest request,
      HttpServletResponse response) {
    Client createdClient = clientService.create(client);
    response.setHeader("Location",
        request.getRequestURL().append("/").append(createdClient.getId()).toString());
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all users.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<Client> getAllCLients(
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "Tha page size", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response, OAuth2Authentication user) {

    Page<Client> clientPage = clientService.getAll((UserRepositoryUserDetails) user.getPrincipal(),
        page, size);
    response.addHeader("X-Total-Count", "" + clientPage.getNumberOfElements());
    return clientPage.getContent();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single client.", notes = "You have to provide a valid client ID.")
  public @ResponseBody Client getClient(
      @ApiParam(value = "The ID of the client.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    Client client = clientService.findById(id);
    checkResourceFound(client);
    return client;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a client.", notes = "Provide a valid client ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updateClient(
      @ApiParam(value = "The ID of the existing client resource.", required = true) @PathVariable("id") Long id,
      @RequestBody Client client, HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(clientService.findById(id));
    if (id != client.getId())
      throw new DataFormatException("ID doesn't match!");
    clientService.update(client);
  }

  @ApiResponses(value = {
      // @ApiResponse(code = 400, message = "Invalid ID supplied",
      // responseHeader = @ResponseHeader(name = "X-Rack-Cache", description =
      // "Explains whether or not a cache was used", response = Boolean.class)
      // ),
      @ApiResponse(code = 404, message = "Client not found") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a client.", notes = "You have to provide a valid client ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteClient(
      @ApiParam(value = "The ID of the existing client resource.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(clientService.findById(id));
    clientService.delete(id);
  }

}
