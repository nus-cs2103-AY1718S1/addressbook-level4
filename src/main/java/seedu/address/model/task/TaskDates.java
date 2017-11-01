package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a date for a task which can be formatted.
 */
public abstract class TaskDates {

    public static final String DISPLAY_DATE_FORMAT = "EEE, MMM d, ''yy";
    public static final String[] DOTTED_DATE_FORMATS = new String[]{"MM.dd.yyyy", "MM.d.yyyy", "M.d.yyyy",
        "M.d.yy", "M.dd.yy", "MM.d.yy", "MM.dd.yy", "M.dd.yyyy"};
    public static final String[] VALID_DATE_FORMATS = new String[]{"MM-dd-yyyy", "M-dd-yyyy", "M-d-yyyy",
        "MM-d-yyyy", "MM-dd-yy", "M-dd-yy", "MM-d-yy", "M-d-yy", "MM.dd.yyyy", "MM.d.yyyy", "M.d.yyyy",
        "M.d.yy", "M.dd.yy", "MM.d.yy", "MM.dd.yy", "M.dd.yyyy", "MM/dd/yyyy", "M/dd/yyyy", "M/d/yyyy",
        "MM/d/yyyy", "MM/dd/yy", "M/dd/yy", "MM/d/yy", "M/d/yy"};
    public static final int NUMBER_MONTHS = 12;
    public static final int FEBRUARY = 2;
    public static final int MAX_DAYS_IN_MONTH = 31;
    public static final int MAX_DAYS_IN_FEB = 29;

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date is invalid";

    /**
     * Formats the last date of a given Date into a String.
     */
    public static String formatDate(Date date) throws IllegalValueException {
        SimpleDateFormat sdf = new SimpleDateFormat(DISPLAY_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Returns true if the {@code startDate} is before the {@code deadline}} or if one of the parameters is empty.
     * Otherwise, an exception is thrown.
     */
    public static boolean isStartDateBeforeDeadline(StartDate startDate, Deadline deadline) {
        if (!startDate.isEmpty() && !deadline.isEmpty()) {
            try {
                Date parsedStartDate = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(startDate.toString());
                Date parsedDeadline = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(deadline.toString());
                return parsedDeadline.after(parsedStartDate);
            } catch (ParseException p) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the {@code startDate} is before the {@code deadline}} or if one of the parameters is empty.
     * Otherwise, an exception is thrown.
     */
    public static boolean isStartDateBeforeDeadline(Optional<StartDate> startDate, Optional<Deadline> deadline) {
        if (startDate.isPresent() && !startDate.get().isEmpty() && deadline.isPresent() && !deadline.get().isEmpty()) {
            try {
                Date parsedStartDate = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(startDate.get().toString());
                Date parsedDeadline = new SimpleDateFormat(DISPLAY_DATE_FORMAT).parse(deadline.get().toString());
                return parsedDeadline.after(parsedStartDate);
            } catch (ParseException p) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param inputDate
     * @return
     */
    public static boolean isDateValid(String inputDate) {
        String trimmedDate = inputDate.trim();
        for (String format : VALID_DATE_FORMATS) {
            if (doesDateMatchValidFormat(trimmedDate, format)) {
                int firstSeparatorIndex = Math.max(trimmedDate.indexOf('-'), trimmedDate.indexOf('/'));
                firstSeparatorIndex = Math.max(firstSeparatorIndex, trimmedDate.indexOf('.'));
                int secondSeparatorIndex = Math.max(trimmedDate.lastIndexOf('-'),
                        trimmedDate.lastIndexOf('/'));
                secondSeparatorIndex = Math.max(secondSeparatorIndex, trimmedDate.lastIndexOf('.'));
                int month = Integer.parseInt(trimmedDate.substring(0, firstSeparatorIndex));
                int day = Integer.parseInt(trimmedDate.substring(firstSeparatorIndex + 1, secondSeparatorIndex));
                if (month > NUMBER_MONTHS | day > MAX_DAYS_IN_MONTH | (month == FEBRUARY && day > MAX_DAYS_IN_FEB)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines the specific dotted date format used by the {@code String inputDate}
     */
    public static String getDottedFormat(String inputDate) {
        for (String format : DOTTED_DATE_FORMATS) {
            if (isDateInDottedFormat(inputDate, format) && isDateValid(inputDate)) {
                return format;
            }
        }
        return "";
    }

    /**
     * Checks if a given {@code String inputDate} is in (M)M.(d)d.(yy)yy format.
     */
    public static boolean isDateInDottedFormat(String inputDate, String dateFormat) {
        try {
            new SimpleDateFormat(dateFormat).parse(inputDate);
            return true;
        } catch (ParseException p) {
            return false;
        }
    }

    /**
     * Checks if the {@code String inputDate} matches the given {@code String validDateFormat}
     */
    public static boolean doesDateMatchValidFormat(String inputDate, String validDateFormat) {
        try {
            new SimpleDateFormat(validDateFormat).parse(inputDate);
            return true;
        } catch (ParseException p) {
            return false;
        }
    }

}
