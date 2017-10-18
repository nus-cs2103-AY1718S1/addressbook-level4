package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a date for a task which can be formatted.
 */
public class TaskDates {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates can only contain a String in the format dd-MM-yyyy";

    /**
     * Returns true if the given String is a valid date. If the input date format is invalid, an exception is thrown.
     */
    public static boolean isDateValid(String date) throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException pe) {
            return false;
        }
    }

    /**
     * Formats the date of a given string into LocalDate.
     */
    public static LocalDate formatDate(String date) throws IllegalValueException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }
}
