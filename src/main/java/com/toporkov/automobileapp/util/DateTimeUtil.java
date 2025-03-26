package com.toporkov.automobileapp.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class DateTimeUtil {

    public static ZonedDateTime toEnterpriseTime(Instant utcTime, String enterpriseTimezone) {
        ZoneId enterpriseZone = ZoneId.of(enterpriseTimezone);
        return utcTime.atZone(enterpriseZone);
    }
}
