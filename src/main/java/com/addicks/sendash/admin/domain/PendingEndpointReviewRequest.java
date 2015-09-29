package com.addicks.sendash.admin.domain;

import java.util.Collection;

public class PendingEndpointReviewRequest {

  private Collection<Long> ids;

  public PendingEndpointReviewRequest() {

  }

  public Collection<Long> getIds() {
    return ids;
  }

  public void setIds(Collection<Long> ids) {
    this.ids = ids;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ids == null) ? 0 : ids.hashCode());
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
    PendingEndpointReviewRequest other = (PendingEndpointReviewRequest) obj;
    if (ids == null) {
      if (other.ids != null)
        return false;
    }
    else if (!ids.equals(other.ids))
      return false;
    return true;
  }

}
