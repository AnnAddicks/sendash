package com.addicks.sendash.admin.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ann on 5/13/15.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit implements Serializable {

  private static final long serialVersionUID = -5419699145299244678L;

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Integer id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "payloadId", nullable = false)
  private Payload payload;

  private String message;

  @Temporal(TemporalType.TIMESTAMP)
  private Date commitDate;

  @CollectionTable(name = "ADD_SCRIPT_COMMITS", joinColumns = @JoinColumn(name = "COMMIT_ID") )
  @ElementCollection
  @Column(name = "SCRIPT_NAME")
  private Collection<String> added;

  @CollectionTable(name = "MODIFIED_SCRIPT_COMMITS", joinColumns = @JoinColumn(name = "COMMIT_ID") )
  @ElementCollection
  @Column(name = "SCRIPT_NAME")
  private Collection<String> modified;

  @CollectionTable(name = "REMOVED_SCRIPT_COMMITS", joinColumns = @JoinColumn(name = "COMMIT_ID") )
  @ElementCollection
  @Column(name = "SCRIPT_NAME")
  private Collection<String> removed;

  public Commit() {
    added = new ArrayList<>();
    modified = new ArrayList<>();
    removed = new ArrayList<>();
  }

  public void setPayload(Payload payload) {
    this.payload = payload;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getTimestamp() {
    return commitDate;
  }

  public void setTimestamp(Date timestamp) {
    this.commitDate = timestamp;
  }

  public Collection<String> getAdded() {
    return added;
  }

  public void setAdded(List<String> added) {
    this.added = added;
  }

  public Collection<String> getModified() {
    return modified;
  }

  public void setModified(List<String> modified) {
    this.modified = modified;
  }

  public Collection<String> getRemoved() {
    return removed;
  }

  public void setRemoved(List<String> removed) {
    this.removed = removed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    Commit commit = (Commit) o;

    if (message != null ? !message.equals(commit.message) : commit.message != null)
      return false;
    if (commitDate != null ? !commitDate.equals(commit.commitDate) : commit.commitDate != null)
      return false;
    if (added != null ? !added.equals(commit.added) : commit.added != null)
      return false;
    if (modified != null ? !modified.equals(commit.modified) : commit.modified != null)
      return false;
    return !(removed != null ? !removed.equals(commit.removed) : commit.removed != null);

  }

  @Override
  public int hashCode() {
    int result = message != null ? message.hashCode() : 0;
    result = 31 * result + (commitDate != null ? commitDate.hashCode() : 0);
    result = 31 * result + (added != null ? added.hashCode() : 0);
    result = 31 * result + (modified != null ? modified.hashCode() : 0);
    result = 31 * result + (removed != null ? removed.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Commit{" + "id=" + id + ", message='" + message + '\'' + ", timestamp='" + commitDate
        + '\'' + ", added=" + added + ", modified=" + modified + ", removed=" + removed + '}';
  }
}
