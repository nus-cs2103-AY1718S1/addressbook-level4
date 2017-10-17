package seedu.address.model.task;

import java.text.ParseException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a date for a task which can be formatted.
 */
public class TaskDates {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates can only contain a String in the format dd/mm/yyyy";

    /**
     * Formats the date of a given string. If the input date format is invalid, an exception is thrown.
     */
    public static LocalDate formatDate(String date) throws IllegalValueException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(date, formatter);
        } catch (DateTimeParseException pe) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
    }
}
