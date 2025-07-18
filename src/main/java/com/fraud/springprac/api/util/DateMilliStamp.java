package com.fraud.springprac.api.util;

public class DateMilliStamp {

    private DateMilliStamp() {} // Prevent instantiation

    public static long timeStamp() {
        return System.currentTimeMillis(); // Returns current time in milliseconds
    }

    public static long timeStamp(java.util.Date date) {
        return date.getTime(); // Returns given date in milliseconds
    }
}
