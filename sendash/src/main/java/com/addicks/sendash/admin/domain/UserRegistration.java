package com.addicks.sendash.admin.domain;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserRegistration {

  private Long userId;

  @Id
  private String uuid;

  private boolean needsPassword;

  public UserRegistration() {

  }

  public UserRegistration(User user, boolean needsPassword) {
    this.userId = user.getId();
    this.needsPassword = needsPassword;
    this.uuid = UUID.randomUUID().toString();
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public boolean needsPassword() {
    return needsPassword;
  }

  public void setNeedPassword(boolean needs_password) {
    this.needsPassword = needs_password;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
    UserRegistration other = (UserRegistration) obj;
    if (userId == null) {
      if (other.userId != null)
        return false;
    }
    else if (!userId.equals(other.userId))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "RegisterUser [userId=" + userId + ", uuid=" + uuid + ", needs_password=" + needsPassword
        + "]";
  }

}
