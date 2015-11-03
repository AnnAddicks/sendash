package com.addicks.sendash.admin.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Client implements Serializable {

  private static final long serialVersionUID = 7236302321181353033L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "USER_CLIENT", joinColumns = {
      @JoinColumn(name = "CLIENT_ID") }, inverseJoinColumns = { @JoinColumn(name = "USER_ID") })
  private Set<User> users;

  public Client() {
    users = new HashSet<>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public void addUser(User user) {
    users.add(user);

  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    Client other = (Client) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    }
    else if (!name.equals(other.name))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "Client [id=" + id + ", name=" + name + ", users=" + users + "]";
  }

}
