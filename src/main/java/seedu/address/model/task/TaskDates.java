package seedu.address.model.task;

import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
     * Formats the first date of a given DateGroup into a String.
     */
    public static String formatDate(DateGroup date) throws IllegalValueException {
        List<Date> dates = date.getDates();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        return sdf.format(dates.get(0));
    }
}
