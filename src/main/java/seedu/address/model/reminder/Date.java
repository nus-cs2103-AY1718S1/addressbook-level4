//@@author duyson98

package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a reminder's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date should conform the following format: dd/mm/yyyy hh:mm";

    public final String date;

    /**
     * Validates given date.
     */
    public Date(String dateAndTime) throws IllegalValueException {
        requireNonNull(dateAndTime);
        if (!isValidDate(dateAndTime)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        String[] splittedDateAndTime = dateAndTime.trim().split("\\s+");
        String date = splittedDateAndTime[0].trim();
        String time = splittedDateAndTime[1].trim();

        this.date = date + " " + time;
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String dateAndTime) {
        String[] splittedDateAndTime = dateAndTime.trim().split("\\s+");
        if (splittedDateAndTime.length != 2) {
            return false;
        }

        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            String date = splittedDateAndTime[0].trim();
            LocalDate.parse(date, dateFormatter);
            String time = splittedDateAndTime[1].trim();
            LocalTime.parse(time, timeFormatter);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && this.date.equals(((Date) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }

}
