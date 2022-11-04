package com.ckt.ecommercecybersoft.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy";
    public static long now() {
        return System.currentTimeMillis();
    }
}
