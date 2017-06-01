package com.gildaswise.notabook.utils;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.FormatStyle;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Gildaswise on 04/05/2017.
 */

public class DateUtils {

    public static Date toDate(LocalDateTime localDateTime) {
        return new Date(localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneOffset.UTC);
    }

    public static Date now() {
        return toDate(LocalDateTime.now());
    }

    public static String toShortString(Date date) {
        return toLocalDateTime(date).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.getDefault()));
    }

    public static String toShortDateString(Date date) {
        if(date == null) date = now();
        return toLocalDateTime(date).toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.getDefault()));
    }

    public static String toShortTimeString(Date date) {
        if(date == null) date = now();
        return toLocalDateTime(date).toLocalTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(Locale.getDefault()));
    }

}
