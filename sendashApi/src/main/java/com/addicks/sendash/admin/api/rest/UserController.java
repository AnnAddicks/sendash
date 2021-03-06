package com.addicks.sendash.admin.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.domain.json.UserUI;
import com.addicks.sendash.admin.exception.DataFormatException;
import com.addicks.sendash.admin.service.IUserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = UserController.REQUEST_MAPPING)
@Api(value = "user", description = "CRUD user management.")
public class UserController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/admin/user";

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private IUserService userService;

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  public UserController() {

  }

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a user.", notes = "Returns the URL of the new user in the Location header.")
  public void createUser(@RequestBody @Valid UserUI userUI, HttpServletRequest request,
      HttpServletResponse response, OAuth2Authentication oauthUser, BindingResult result) {

    if (result.hasErrors()) {
      log.error("Validation Error: " + result.getAllErrors());
    }
    else {
      User creator = getUserFromAuthentication(oauthUser);
      User user = userUI.getUser();
      user.setPassword(passwordEncoder.encode(user.getPassword()));

      User savedUser = userService.populateAndSaveUser(creator, user, userUI.getClientIds(),
          userUI.getRole());
      response.setHeader("Location",
          request.getRequestURL().append("/").append(savedUser.getId()).toString());
    }
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a paginated list of all users.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
  public @ResponseBody List<User> getAllUsers(
      @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "_page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
      @ApiParam(value = "The page size", required = true) @RequestParam(value = "_perPage", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
      @ApiParam(value = "The sorting direction", required = true) @RequestParam(value = "_sortDir", required = true, defaultValue = DEFAULT_SORT) String sortDir,
      @ApiParam(value = "The sorting field", required = true) @RequestParam(value = "_sortField", required = true, defaultValue = "email") String sortField,
      HttpServletRequest request, HttpServletResponse response, OAuth2Authentication oauthUser) {

    User user = getUserFromAuthentication(oauthUser);
    Page<User> userPage = userService.findAll(user, page, size);
    response.addHeader("X-Total-Count", "" + userPage.getNumberOfElements());
    return userPage.getContent();
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a single user.", notes = "You have to provide a valid user ID.")
  public @ResponseBody User getUser(
      @ApiParam(value = "The ID of the user.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = userService.findById(id);
    checkResourceFound(user);
    return user;
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update a user.", notes = "Provide a valid user ID in the URL and in the payload. The ID attribute can not be updated.")
  public void updateUser(
      @ApiParam(value = "The ID of the existing user resource.", required = true) @PathVariable("id") Long id,
      @RequestBody @Valid UserUI userUI, HttpServletRequest request, HttpServletResponse response,
      OAuth2Authentication oauthUser, BindingResult result) {
    checkResourceFound(userService.findById(id));

    User editingUser = getUserFromAuthentication(oauthUser);
    User editedUser = userUI.getUser();
    if (id != userUI.getId())
      throw new DataFormatException("ID doesn't match!");
    userService.update(editingUser, editedUser, userUI.getClientIds(), userUI.getRole());
  }

  @ApiResponses(value = {
      // @ApiResponse(code = 400, message = "Invalid ID supplied",
      // responseHeader = @ResponseHeader(name = "X-Rack-Cache", description =
      // "Explains whether or not a cache was used", response = Boolean.class)
      // ),
      @ApiResponse(code = 404, message = "User not found") })
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Delete a user.", notes = "You have to provide a valid user ID in the URL. Once deleted the resource can not be recovered.")
  public void deleteUser(
      @ApiParam(value = "The ID of the existing user resource.", required = true) @PathVariable("id") Long id,
      HttpServletRequest request, HttpServletResponse response) {
    checkResourceFound(userService.findById(id));
    userService.delete(id);
  }
}