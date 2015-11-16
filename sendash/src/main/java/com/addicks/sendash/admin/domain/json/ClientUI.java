package com.addicks.sendash.admin.domain.json;

import java.util.ArrayList;
import java.util.List;

import com.addicks.sendash.admin.domain.Client;

public class ClientUI extends Client {

  private static final long serialVersionUID = 7648214255821295013L;

  private List<Long> userIds;

  public ClientUI() {
    super();
    userIds = new ArrayList<>();
  }

  public List<Long> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<Long> userIds) {
    this.userIds = userIds;
  }

  public Client getClientData() {
    Client client = new Client();
    client.setId(this.getId());
    client.setName(this.getName());
    client.setUsers(this.getUsers());
    return client;
  }

  @Override
  public String toString() {
    return "ClientUI [userIds=" + userIds + ", toString()=" + super.toString() + "]";
  }

}
