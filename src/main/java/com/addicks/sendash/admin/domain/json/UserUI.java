package com.addicks.sendash.admin.domain.json;

import java.util.List;

public class UserUI {

  private final String email;

  private final String firstName;

  private final String lastName;

  private final List<Long> roles;

  private final List<Long> clientIds;

  public UserUI(String email, String firstName, String lastName, List<Long> roles,
      List<Long> clientIds) {
    super();
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.roles = roles;
    this.clientIds = clientIds;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public List<Long> getRoles() {
    return roles;
  }

  public List<Long> getClientIds() {
    return clientIds;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((clientIds == null) ? 0 : clientIds.hashCode());
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
    result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
    UserUI other = (UserUI) obj;
    if (clientIds == null) {
      if (other.clientIds != null)
        return false;
    }
    else if (!clientIds.equals(other.clientIds))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    }
    else if (!email.equals(other.email))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    }
    else if (!firstName.equals(other.firstName))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    }
    else if (!lastName.equals(other.lastName))
      return false;
    if (roles == null) {
      if (other.roles != null)
        return false;
    }
    else if (!roles.equals(other.roles))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "UserUI [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName
        + ", roles=" + roles + ", clientIds=" + clientIds + "]";
  }

}
