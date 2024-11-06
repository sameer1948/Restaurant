package org.app.restaurant.utils;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateUtility {

    public static Timestamp getCurrentDate() {

        //ZoneId.of("Asia/Kolkata")

        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.systemDefault()); // You can specify a zone here

        // Convert ZonedDateTime to java.sql.Timestamp
        return Timestamp.from(zonedDateTime.toInstant());
    }
}