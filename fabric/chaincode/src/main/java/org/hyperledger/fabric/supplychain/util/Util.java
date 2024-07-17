package org.hyperledger.fabric.supplychain.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Util {
    public static String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        ZonedDateTime vnTime = now.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return vnTime.format(formatter);
    }
}
