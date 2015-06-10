package com.khoubyari.example.domain;

import java.time.LocalDateTime;

/**
 * Created by ann on 4/16/15.
 */
public class Status {

    private final Boolean isUpdateNeeded;

    private final LocalDateTime timestamp;

    private final boolean runHealthCheckNow;

    private static final String data =  "get-process";

    private static final String cronSchedule = " 0 08 * * * ";



    public Status(Boolean isUpdateNeeded) {
        this.isUpdateNeeded = isUpdateNeeded;
        timestamp = LocalDateTime.now();
        runHealthCheckNow = false;

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

    public static String getCronSchedule() {
        return cronSchedule;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (isUpdateNeeded != null ? !isUpdateNeeded.equals(status.isUpdateNeeded) : status.isUpdateNeeded != null)
            return false;
        return !(timestamp != null ? !timestamp.equals(status.timestamp) : status.timestamp != null);

    }

    @Override
    public int hashCode() {
        int result = isUpdateNeeded != null ? isUpdateNeeded.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isUpdateNeeded=" + isUpdateNeeded +
                ", timestamp=" + timestamp +
                '}';
    }
}
