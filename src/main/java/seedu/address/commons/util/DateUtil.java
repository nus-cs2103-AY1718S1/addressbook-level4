package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

/**
 * Utility methods related to dates
 */
public class DateUtil {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "The date for %1$s must exist and follow DD-MM-YYYY or DD-MM format";

    private static final String DATE_VALIDATION_REGEX_1 = "\\d{2}-\\d{2}-\\d{4}";
    private static final String DATE_VALIDATION_REGEX_2 = "\\d{2}-\\d{2}";

    /**
     * Returns true if the date follows the correct date format and exists.
     */
    public static boolean isValid(String date) {
        return (hasValidFormat(date) && isActualDate(date));
    }

    /**
     * Returns the day of a given {@code date} string in integer.
     * Returns 0 if {@code date} is not in valid date format.
     */
    public static int getDay(String date) {
        if (hasValidFormat(date)) {
            return Integer.parseInt(date.substring(0, 2));
        }
        return 0;
    }

    /**
     * Returns the month of a given {@code date} string in integer.
     * Returns 0 if {@code date} is not in valid date format.
     */
    public static int getMonth(String date) {
        if (hasValidFormat(date)) {
            return Integer.parseInt(date.substring(3, 5));
        }
        return 0;
    }

    /**
     * Returns the year of a given {@code date} string in integer.
     * Returns 0 if {@code date} is not in valid date format or year is not specified.
     */
    public static int getYear(String date) {
        if (hasValidFormat(date) && hasYear(date)) {
            return Integer.parseInt(date.substring(6));
        }
        return 0;
    }

    /**
     * Returns true if a given {@code date} occurs in a leap year or the {@code date} does not contain year.
     * Returns false by default if the {@code date} follows invalid date format.
     */
    public static boolean isInLeapYear(String date) {
        if (hasValidFormat(date) && hasYear(date)) {
            int year = getYear(date);
            return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
        }
        return false;
    }

    /**
     * Returns true if the date has a valid format
     */
    public static boolean hasValidFormat(String date) {
        requireNonNull(date);
        date = date.trim();
        return date.matches(DATE_VALIDATION_REGEX_1) || date.matches(DATE_VALIDATION_REGEX_2);
    }

    /**
     * Checks if the date provided contains year
     */
    private static boolean hasYear(String date) {
        return date.length() == 10;
    }

    /**
     * Returns true if the given {@code date} exists
     */
    private static boolean isActualDate (String date) {
        int day = getDay(date);
        int month = getMonth(date);

        // checks month
        if (month > 12 || month < 1) {
            return false;
        }

        // checks day
        if (day < 1) {
            return false;
        }

        // checks day, month, year
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (day > 30) {
                return false;
            }
        } else if (month == 2) {
            if (isInLeapYear(date)) {
                if (day > 29) {
                    return false;
                }
            } else if (day > 28) {
                return false;
            }
        } else if (day > 31) {
            return false;
        }
        return true;
    }
}
