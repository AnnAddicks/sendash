package com.khoubyari.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Created by ann on 5/13/15.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Commit implements Serializable {

    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Integer id;

    @ManyToOne( fetch = FetchType.EAGER )
    @JoinColumn(name="payloadId", nullable = false )
    private Payload payload;

    @Column
    private String message;

    @Column(name = "commitTimestamp")
    private String timestamp;

    @Column
    @ElementCollection
    private Collection<String> added;

    @Column
    @ElementCollection
    private Collection<String> modified;


    @ElementCollection
    @Column
    private Collection<String> removed;

    public Commit() {

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commit commit = (Commit) o;

        if (message != null ? !message.equals(commit.message) : commit.message != null) return false;
        if (timestamp != null ? !timestamp.equals(commit.timestamp) : commit.timestamp != null) return false;
        if (added != null ? !added.equals(commit.added) : commit.added != null) return false;
        if (modified != null ? !modified.equals(commit.modified) : commit.modified != null) return false;
        return !(removed != null ? !removed.equals(commit.removed) : commit.removed != null);

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (added != null ? added.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (removed != null ? removed.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "id=" + id +
                ", payload=" + payload +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", added=" + added +
                ", modified=" + modified +
                ", removed=" + removed +
                '}';
    }
}
