package com.addicks.sendash.admin.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import com.addicks.sendash.admin.dao.jpa.RoleRepository;
import com.addicks.sendash.admin.domain.Role;

public class RoleService implements IRoleService {
  @Autowired
  private RoleRepository roleRepository;

  @Override
  public Collection<Role> findAll() {
    Iterable<Role> roles = roleRepository.findAll();

    Collection<Role> roleList = new ArrayList<Role>();
    roles.forEach(role -> roleList.add(role));
    return roleList;
  }

}
