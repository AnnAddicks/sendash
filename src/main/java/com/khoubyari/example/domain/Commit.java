package com.khoubyari.example.domain;

import java.util.List;

/**
 * Created by ann on 5/13/15.
 */
public class Commit {

    private String message;
    private String timestamp;
    private List<String> added;
    private List<String> modified;

    public Commit() {

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

    public List<String> getAdded() {
        return added;
    }

    public void setAdded(List<String> added) {
        this.added = added;
    }

    public List<String> getModified() {
        return modified;
    }

    public void setModified(List<String> modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commit commit = (Commit) o;

        if (message != null ? !message.equals(commit.message) : commit.message != null) return false;
        if (timestamp != null ? !timestamp.equals(commit.timestamp) : commit.timestamp != null) return false;
        if (added != null ? !added.equals(commit.added) : commit.added != null) return false;
        return !(modified != null ? !modified.equals(commit.modified) : commit.modified != null);

    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (added != null ? added.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Commit{" +
                "message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", added=" + added +
                ", modified=" + modified +
                '}';
    }
}
