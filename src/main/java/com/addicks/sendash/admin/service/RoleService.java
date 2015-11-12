package com.addicks.sendash.admin.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.addicks.sendash.admin.dao.jpa.RoleRepository;
import com.addicks.sendash.admin.domain.Role;

@Service
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

  @Override
  public Set<Role> findByIds(List<Long> roleIds) {
    if (CollectionUtils.isEmpty(roleIds)) {
      return Collections.emptySet();
    }

    return roleRepository.findByIds(roleIds);
  }

}
