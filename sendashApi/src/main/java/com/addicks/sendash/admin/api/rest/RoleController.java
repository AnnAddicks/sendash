package com.addicks.sendash.admin.api.rest;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.addicks.sendash.admin.domain.Role;
import com.addicks.sendash.admin.service.IRoleService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = RoleController.REQUEST_MAPPING)
@Api(value = "role", description = "CRUD user management.")
public class RoleController extends AbstractRestHandler {

  public static final String REQUEST_MAPPING = "/api/admin/role";

  private Collection<Role> roles;

  private IRoleService roleService;

  @Autowired
  public RoleController(IRoleService roleService) {
    this.roleService = roleService;
    roles = roleService.findAll();
  }

  @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.OK)
  @ApiOperation(value = "Get a list of all roles.", notes = "The list is not paginated.")
  public @ResponseBody Collection<Role> getAllRoles(HttpServletRequest request,
      HttpServletResponse response, OAuth2Authentication oauthUser) {

    return roles;
  }

  @Secured("ROLE_SUPER")
  @RequestMapping(value = "/update", method = RequestMethod.GET, produces = { "application/json",
      "application/xml" })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiOperation(value = "Update the list of all roles.", notes = "This action is limited to specific users.")
  public @ResponseBody void updateAllRoles() {
    roles = roleService.findAll();

  }

}
