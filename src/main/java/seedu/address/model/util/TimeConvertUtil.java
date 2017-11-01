package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.util.Objects.requireNonNull;

/**
 * Contains utility methods for converting date and time.
 */
public class TimeConvertUtil {

    public static final String EMPTY_STRING = "";

    private static final String TIME_PATTERN = "dd-MM-yyyy HH:mm";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    public static String convertTimeToString(LocalDateTime time) throws DateTimeParseException {
        if (time == null) {
            return EMPTY_STRING;
        }
        return time.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime convertStringToTime(String timeStr) throws DateTimeParseException {
        if (timeStr == null) {
            return null;
        }
        return LocalDateTime.parse(timeStr, DATE_TIME_FORMATTER);
    }
}
