package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a date for a task which can be formatted.
 */
public abstract class TaskDates {

    public static final String DATE_FORMAT_PATTERN = "EEE, MMM d, ''yy";

    public static final String MESSAGE_DATE_CONSTRAINTS = "Date is invalid";

    /**
     * Formats the last date of a given Date into a String.
     */
    public static String formatDate(Date date) throws IllegalValueException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        return sdf.format(date);
    }

    /**
     * Returns true if the {@code startDate} is before the {@code deadline}} or if one of the parameters is empty.
     * Otherwise, an exception is thrown.
     */
    public static boolean isStartDateBeforeDeadline(StartDate startDate, Deadline deadline)
            throws IllegalValueException {
        if (!startDate.isEmpty() && !deadline.isEmpty()) {
            try {
                Date parsedStartDate = new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(startDate.toString());
                Date parsedDeadline = new SimpleDateFormat(DATE_FORMAT_PATTERN).parse(deadline.toString());
                return parsedDeadline.after(parsedStartDate);
            } catch (ParseException p) {
                throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
            }
        }
        return true;
    }
}
