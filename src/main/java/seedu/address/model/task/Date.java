package seedu.address.model.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author zhangshuoyang
/**
 * Represents the date of a certain task in the Address Book.
 */

public class Date {

    public static final String MESSAGE_DATE_FORMAT_CONSTRAINTS =
            "The date must be in the format dd/MM/yyyy";
    public static final String MESSAGE_DATE_INVALID_CONSTRAINTS =
            "The input date is not valid!";


    /**
     * Returns true if the given date is in the valid format.
     *
     * @throws java.time.format.DateTimeParseException if the date format is invalid
     */
    public static boolean isValidDate (String input) throws IllegalValueException {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);
        try {
            LocalDate.parse(input, format);
            return true;
        } catch (DateTimeParseException exc) {
            return false;
        }
    }

}
