package com.addicks.sendash.admin.dao.jpa;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.addicks.sendash.admin.domain.Role;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

  @Query("from Role r where r.id in (:ids)")
  Set<Role> findByIds(@Param("ids") List<Long> roleIds);

  @Query("from Role r where r.id = :id or r.name='ROLE_USER'")
  Set<Role> findByIdsIncluedUser(@Param("id") Long roleId);

}
