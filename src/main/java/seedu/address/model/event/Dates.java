package seedu.address.model.event;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Main class of Dates
 */
public class Dates {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Event date should be 3 number of 2 digit each, separated by '-'";
    public static final String DATE_VALIDATION_REGEX = "\\d{2}" + "-" + "\\d{2}" + "-" + "\\d{2}";

    public final Date date;

    /**
     * Validates given email.
     *
     * @throws IllegalValueException if given email address string is invalid.
     */
    public Dates(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDateFormat(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy");
        format.setLenient(false);
        try {
            //if not valid, it will throw ParseException
            Date value = format.parse(trimmedDate);
            this.date = value;
        } catch (ParseException e) {
            throw new IllegalValueException("Not a real date");
        }
    }

    public Dates(Dates dates) {
        this.date = dates.getDate();
    }

    /**
     * Returns if a given string is in the valid date format. Check the value to be a real date.
     */
    public static boolean isValidDateFormat(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("dd-mm-yy");
        return df.format(date);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Dates // instanceof handles nulls
                && this.date.equals(((Dates) other).date)); // state check
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}

