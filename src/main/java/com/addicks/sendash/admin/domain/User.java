package com.addicks.sendash.admin.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "PERSON")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

  private static final long serialVersionUID = 5651803198978520716L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String firstName;

  private String lastName;

  @JsonIgnore
  @JsonProperty(value = "password")
  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "USER_ROLE", joinColumns = {
      @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
  private Set<Role> roles;

  @JsonIgnore
  @ManyToMany(mappedBy = "users")
  private Set<Client> clients;

  public User() {
    roles = new HashSet<>();
    clients = new HashSet<>();
  }

  public User(User user) {
    this.id = user.getId();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.password = user.getPassword();
    this.roles = user.getRoles();
    this.clients = user.getClients();
  }

  public User(Long id, String email) {
    super();
    this.id = id;
    this.email = email;
  }

  public User(Long userId) {
    super();
    this.id = userId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Set<Client> getClients() {
    return clients;
  }

  public void setClients(Set<Client> clients) {
    this.clients = clients;
  }

  public Client getClientById(Long clientId) {
    for (Client client : clients) {
      if (client.getId().equals(clientId))
        return client;
    }
    return new Client();
  }

  public Collection<Long> getClientIds() {
    Collection<Long> ids = new ArrayList<>();
    clients.forEach(client -> ids.add(client.getId()));
    return ids;
  }

  public List<User> getAllUsersFromClients() {
    Set<User> users = new HashSet<>();
    clients.forEach(client -> client.getUsers().forEach(user -> users.add(user)));
    return new ArrayList<User>(users);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((email == null) ? 0 : email.hashCode());
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
    User other = (User) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    }
    else if (!email.equals(other.email))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", email=" + email + ", firstName=" + firstName + ", lastName="
        + lastName + ", password=" + password + ", roles=" + roles + ", clients=" + clients + "]";
  }

}
