package com.addicks.sendash.admin.api.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wordnik.swagger.annotations.Api;

@RestController
@RequestMapping(value = UserController.REQUEST_MAPPING)
@Api(value = "user", description = "CRUD user management.")
public class UserController {

  public static final String REQUEST_MAPPING = "/user";

}
