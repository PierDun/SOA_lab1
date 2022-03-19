package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateBuilder {
    public static LocalDateTime getLocalDateFromDateAndTime (String dateStr, String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dateStr + " "+ timeStr, formatter);
    }
}