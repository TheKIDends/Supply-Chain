package com.supplychain.userservice.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class TimeUtils {
    // Chuyển đổi ZonedDateTime sang String
    public static String convertZonedDateTimeToStr(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    // Chuyển đổi String sang ZonedDateTime
    public static ZonedDateTime convertStrToZonedDateTime(String dateTimeString) {
        return ZonedDateTime.parse(dateTimeString, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static String getCurrentTimeStrInVietNam() {
        ZonedDateTime currentTimeInVietnam = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        return convertZonedDateTimeToStr(currentTimeInVietnam);
    }
}
