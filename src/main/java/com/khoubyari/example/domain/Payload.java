package com.khoubyari.example.domain;

import java.util.List;

/**
 * Created by ann on 5/13/15.
 */
public class Payload {

    private String ref;
    private String before;
    private String after;
    private List<Commit> commits;

    public Payload() {


    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payload payload = (Payload) o;

        if (ref != null ? !ref.equals(payload.ref) : payload.ref != null) return false;
        if (before != null ? !before.equals(payload.before) : payload.before != null) return false;
        if (after != null ? !after.equals(payload.after) : payload.after != null) return false;
        return !(commits != null ? !commits.equals(payload.commits) : payload.commits != null);

    }

    @Override
    public int hashCode() {
        int result = ref != null ? ref.hashCode() : 0;
        result = 31 * result + (before != null ? before.hashCode() : 0);
        result = 31 * result + (after != null ? after.hashCode() : 0);
        result = 31 * result + (commits != null ? commits.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Payload{" +
                "ref='" + ref + '\'' +
                ", before='" + before + '\'' +
                ", after='" + after + '\'' +
                ", commits=" + commits +
                '}';
    }
}
