package com.khoubyari.example.domain;

import java.time.LocalDateTime;

/**
 * Created by ann on 4/16/15.
 */
public class Status {

    private final Boolean isUpdateNeeded;

    private final LocalDateTime timestamp;

    private static final String data =  "get-process";



    public Status(Boolean isUpdateNeeded) {
        this.isUpdateNeeded = isUpdateNeeded;
        timestamp = LocalDateTime.now();

    }

    public Boolean isUpdateNeeded() {
        return isUpdateNeeded;
    }

    public LocalDateTime getTimestamp() {

        /*TODO LocalDateTime time = ...;
        ZoneId zoneId = ZoneId.systemDefault(); // or: ZoneId.of("Europe/Oslo");
        long epoch = time.atZone(zoneId).toEpochSecond();*/
        return timestamp;
    }

    public static String getData() {
        return data;
    }
}
