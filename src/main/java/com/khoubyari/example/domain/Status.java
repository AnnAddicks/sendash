package com.khoubyari.example.domain;

/**
 * Created by ann on 4/16/15.
 */
public class Status {

    private final Boolean isUpdateNeeded;

    public Status(Boolean isUpdateNeeded) {
        this.isUpdateNeeded = isUpdateNeeded;
    }

    public Boolean isUpdateNeeded() {
        return isUpdateNeeded;
    }
}
