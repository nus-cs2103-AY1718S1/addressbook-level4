package seedu.address.model.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a date for a task which can be formatted.
 */
public class TaskDates {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates can only contain a String in the format dd/mm/yyyy";

    public TaskDates() {}

    /**
     * Formats the date of a given string. If the input date format is invalid, an exception is thrown.
     */
    public static Date formatDate(String date) throws IllegalValueException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            return formatter.parse(date);
        } catch (ParseException pe) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
    }
}
