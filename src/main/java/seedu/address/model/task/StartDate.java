package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the Starting Date of a given task in the application.
 * Guarantees: immutable; is valid as declared in {@link #FormatDate(String)}
 */
public class StartDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Dates can only contain a String in the format dd/mm/yyyy";
    public final Date date;

    /**
     * Validates given starting date.
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public StartDate(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        this.date = FormatDate(trimmedDate);
    }

    /**
     * Formats the date of a given string. If the input date format is invalid, an exception is thrown.
     */
    public static Date FormatDate(String date) throws IllegalValueException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            formatter.setLenient(false);
            return formatter.parse(date);
        } catch (ParseException pe) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
    }

    @Override
    public String toString() {
        return date.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StartDate // instanceof handles nulls
                && this.date.equals(((StartDate) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
