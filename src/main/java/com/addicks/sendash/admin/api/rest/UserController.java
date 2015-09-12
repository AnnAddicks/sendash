package com.addicks.sendash.admin.api.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.service.IUserService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = UserController.REQUEST_MAPPING)
@Api(value = "user", description = "CRUD user management.")
public class UserController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/user";

  @Autowired
  private IUserService userService;

  public UserController() {

  }

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.CREATED)
  @ApiOperation(value = "Create a user.", notes = "Returns the URL of the new user in the Location header.")
  public void createHotel(@RequestBody User user, HttpServletRequest request,
      HttpServletResponse response) {
    User savedUser = userService.save(user);
    response.setHeader("Location",
        request.getRequestURL().append("/").append(savedUser.getId()).toString());
  }

}
