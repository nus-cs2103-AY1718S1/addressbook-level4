package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author zhangshuoyang
/**
 * Represents the date of a certain task in the Address Book.
 */

public class Date {

    public static final String MESSAGE_DATE_FORMAT_CONSTRAINTS =
            "The date must be in the format dd/MM/yyyy";


    /**
     * Returns true if the given date is in the valid format.
     *
     * @throws java.time.format.DateTimeParseException if the date format is invalid
     */
    public static boolean isValidDate (String input) throws IllegalValueException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(input, format);
            return true;
        } catch (DateTimeParseException exc) {
            return false;
        }
    }

    /**
     * Format the given date
     */
    public static LocalDate formatDate (String input) throws IllegalValueException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(input, format);
        return date;
    }


}
