package com.addicks.sendash.admin.api.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.User;
import com.addicks.sendash.admin.domain.json.UserUI;
import com.addicks.sendash.admin.service.IRegistrationService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = RegisterController.REQUEST_MAPPING)
@Api(value = "register", description = "CRUD pending endpoint management.")
public class RegisterController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/register";

  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

  private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  private IRegistrationService registrationService;

  @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json",
      "application/xml" }, produces = { "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Register a user they create themselves.", notes = "Returns the URL of the new user in the Location header.")
  public User requestToBeRegistered(@RequestBody @Valid UserUI userUI, HttpServletRequest request,
      HttpServletResponse response, BindingResult result) {

    User user = userUI.getUser();
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    User savedUser = registrationService.registerUser(user);
    return savedUser;

  }

  @RequestMapping(value = "/user/{uuid}", method = RequestMethod.POST, produces = {
      "application/json", "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Confirms the user's email.", notes = "Returns true if a password needs to be set.")
  public @ResponseBody Map<String, String> confirmEmail(@PathVariable("uuid") String usersUUID) {

    Map<String, String> returnMap = new HashMap<>();
    returnMap.put("needsPassword", "" + registrationService.confirmUser(usersUUID));
    return returnMap;
  }

}
