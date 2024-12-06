package com.supplychain.productservice.Util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class TimeUtils {
    public static String convertZonedDateTimeToStr(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static ZonedDateTime convertStrToZonedDateTime(String dateTimeString) {
        return ZonedDateTime.parse(dateTimeString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static String getCurrentTimeStrInVietNam() {
        ZonedDateTime currentTimeInVietnam = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        return convertZonedDateTimeToStr(currentTimeInVietnam);
    }

    public static String convertToStandardTimeFormat(String time) {
        ZonedDateTime zonedDateTime = convertStrToZonedDateTime(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

        return zonedDateTime.format(formatter);
    }

    public static boolean isTimeInRange(String time, String startTime, String endTime) {
        ZonedDateTime targetTime = convertStrToZonedDateTime(time);
        ZonedDateTime start = convertStrToZonedDateTime(startTime);
        ZonedDateTime end = convertStrToZonedDateTime(endTime);

        return (targetTime.isEqual(start) || targetTime.isAfter(start)) &&
                (targetTime.isEqual(end) || targetTime.isBefore(end));
    }
}