//@@author Hailinx
package seedu.address.model.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Contains utility methods for converting date and time.
 */
public class TimeConvertUtil {

    public static final String EMPTY_STRING = "";

    public static final String TIME_PATTERN = "dd-MM-yyyy HH:mm";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * Converts a {@code LocalDateTime} to string.
     * Returns a empty string if time is null.
     */
    public static String convertTimeToString(LocalDateTime time) throws DateTimeParseException {
        if (time == null) {
            return EMPTY_STRING;
        }
        return time.format(DATE_TIME_FORMATTER);
    }

    /**
     * Converts a {@code String} to {@code LocalDateTime}.
     * Returns null if timeStr is null.
     * @throws DateTimeParseException when timeStr does not match {@code DATE_TIME_FORMATTER}
     */
    public static LocalDateTime convertStringToTime(String timeStr) throws DateTimeParseException {
        if (timeStr == null || timeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(timeStr, DATE_TIME_FORMATTER);
    }
}
