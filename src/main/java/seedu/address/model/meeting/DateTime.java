package seedu.address.model.meeting;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and time should only contain numeric characters, colon and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATETIME_VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Validates given date and time.
     *
     * @throws IllegalValueException if given datetime string is invalid.
     */
    public DateTime(String date) throws IllegalValueException {
        requireNonNull(date);
        if (!isValidDateTime(date)) {
            throw new IllegalValueException(MESSAGE_DATETIME_CONSTRAINTS);
        }
        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy HH:mm");
        Date newDate;
        try{
            newDate = df.parse(date);
            date = newDate.toString();
        } catch (ParseException e){
            e.printStackTrace();
        }
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        return test.matches(DATETIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.model.meeting.DateTime // instanceof handles nulls
                && this.value.equals(((seedu.address.model.meeting.DateTime) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
