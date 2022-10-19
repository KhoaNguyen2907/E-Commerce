package com.ckt.ecommercecybersoft.common.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String DATE_TIME_FORMAT = "dd-MM-yyyy";
    public static String now() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }
}
