package com.addicks.sendash.admin.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.addicks.sendash.admin.domain.Role;

public interface IRoleService {

  Collection<Role> findAll();

  Set<Role> findByIds(List<Long> roleIds);

}
