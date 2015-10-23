package com.addicks.sendash.admin.domain.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;

public class Me {

  private String username;

  private Collection<String> roles;

  public Me() {
    username = "";
    roles = Collections.emptyList();
  }

  public Me(UserDetails details, String sessionId) {
    username = details.getUsername();
    roles = new ArrayList<String>();

    details.getAuthorities().forEach(authority -> roles.add(authority.getAuthority()));
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
    return "Me [username=" + username + ", roles=" + roles + "]";
  }

}
