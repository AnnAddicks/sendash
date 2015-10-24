package com.addicks.sendash.admin.domain.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.addicks.sendash.admin.service.CustomUserDetailsService.UserRepositoryUserDetails;

public class Me {

  private UUID id;

  private String username;

  private Collection<String> roles;

  public Me() {
    username = "";
    roles = Collections.emptyList();
  }

  public Me(OAuth2Authentication user) {
    UserRepositoryUserDetails details = (UserRepositoryUserDetails) user.getPrincipal();
    id = details.getUuid();
    username = details.getUsername();
    roles = new ArrayList<String>();

    user.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Collection<String> getRoles() {
    return roles;
  }

  public void setRoles(Collection<String> roles) {
    this.roles = roles;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Me other = (Me) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    }
    else if (!id.equals(other.id))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    }
    else if (!roles.equals(other.roles))
      return false;
    if (username == null) {
      if (other.username != null)
        return false;
    }
    else if (!username.equals(other.username))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Me [id=" + id + ", username=" + username + ", roles=" + roles + "]";
  }

}
