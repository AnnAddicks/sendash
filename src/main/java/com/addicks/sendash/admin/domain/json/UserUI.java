package com.addicks.sendash.admin.domain.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.addicks.sendash.admin.domain.Client;
import com.addicks.sendash.admin.domain.Role;
import com.addicks.sendash.admin.domain.User;

public class UserUI {

  @NotEmpty
  @Email
  private String email;

  @NotEmpty
  @Size(min = 2, max = 255)
  private String firstName;

  @NotEmpty
  @Size(min = 2, max = 255)
  private String lastName;

  private String password;

  @NotEmpty
  private List<Long> roles;

  @NotEmpty
  private List<Long> clientIds;

  public UserUI() {
    roles = new ArrayList<>();
    clientIds = new ArrayList<>();
  }

  public UserUI(String email, String firstName, String lastName, String password, List<Long> roles,
      List<Long> clientIds) {
    super();
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.password = password;
    this.roles = roles;
    this.clientIds = clientIds;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Long> getClientIds() {
    return clientIds;
  }

  public void setClientIds(List<Long> clientIds) {
    this.clientIds = clientIds;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }

  public User getUser() {
    User user = new User();
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setEmail(email);
    user.setRoles(getRoles());
    user.setClients(getClients());
    user.setPassword(getPassword(user));

    return user;
  }

  private String getPassword(User user) {
    if (password == null || password.isEmpty()) {
      return UUID.randomUUID().toString();
    }

    return password;

  }

  private Set<Role> getRoles() {
    Set<Role> roles = new HashSet<>();
    roles.forEach(role -> roles.add(new Role(role.getId())));
    return roles;
  }

  private Set<Client> getClients() {
    Set<Client> clients = new HashSet<>();
    clientIds.forEach(clientId -> clients.add(new Client(clientId)));
    return clients;
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
