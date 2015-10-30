package com.addicks.sendash.admin.api.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.IClientService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = ClientController.REQUEST_MAPPING)
@Api(value = "user-client", description = "CRUD client management.")
public class UserClientController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/admin/user-client";

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(UserClientController.class);

  @Autowired
  private IClientService clientService;

  @RequestMapping(value = "/get-client/for-user/{id}", method = RequestMethod.GET, produces = {
      "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all clients available to a user.", notes = "The user requesting another user's clients must also be associated with thier clients.  If one or more is missing, the user requesting the list is not allowed to view them.  The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<Client> getAllClients(
      @ApiParam(value = "The user to use to find clients", required = true) @PathVariable("id") Long userId,
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "The page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "The sorting direction", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "The sorting field", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response, OAuth2Authentication user) {

    Page<Client> clientPage = clientService.findAll(new User(userId), page, size);
    List<Client> clients = limitClientsWhoAreNotAssociatedWithRequester(user, clientPage);
    response.addHeader("X-Total-Count", "" + clients.size());
    return clients;
  }

  @RequestMapping(value = "/get-user/for-user/{id}", method = RequestMethod.GET, produces = {
      "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all users available to a user based on the clients the user has.", notes = "The users are found by checking all clients the requesting users has access to and then finding all the users for that client.  The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<User> getAllUsers(
      @ApiParam(value = "The user to use to find clients", required = true) @PathVariable("id") Long userId,
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "The page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "The sorting direction", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "The sorting field", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response, OAuth2Authentication user) {

    List<Client> clients = ((User) user.getDetails()).getClients();
    Set<User> users = new HashSet<User>();

    clients.forEach(client -> users.addAll(client.getUsers()));

    response.addHeader("X-Total-Count", "" + users.size());
    return new ArrayList<User>(users);
  }

  @RequestMapping(value = "/get-user/for-client/{id}", method = RequestMethod.GET, produces = {
      "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all users available to a specific client.", notes = "The user requesting the list must have access to the client.  The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<User> getAllUsersForClient(
      @ApiParam(value = "The user to use to find clients", required = true) @PathVariable("id") Long clientId,
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "The page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "The sorting direction", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "The sorting field", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response, OAuth2Authentication user) {

    Client client = ((User) user.getDetails()).getClientById(clientId);
    response.addHeader("X-Total-Count", "" + client.getUsers().size());
    return client.getUsers();
  }

  // TODO methods: addUserToClient, addClientToUser

  private List<Client> limitClientsWhoAreNotAssociatedWithRequester(OAuth2Authentication user,
      Page<Client> clientPage) {
    List<Client> clients = ((User) user.getDetails()).getClients();

    List<Client> requestedClients = clientPage.getContent();
    requestedClients.forEach(client -> {
      if (!clients.contains(client)) {
        clients.remove(client);
      }
    });
    return clients;
  }

}
