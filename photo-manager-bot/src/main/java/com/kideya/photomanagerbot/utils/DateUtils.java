package com.kideya.photomanagerbot.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public class DateUtils {

    private static final DateTimeFormatter formatter =
            new DateTimeFormatterBuilder().appendPattern("dd MM yyyy[ HH:mm:ss]")
                                          .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                                          .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                          .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
                                          .toFormatter();;

    public static LocalDateTime convertStringToDate(String date){
        return LocalDateTime.parse(date, formatter);
    }

    public static String convertDateToString(LocalDateTime date) {
        return formatter.format(date);
    }

}