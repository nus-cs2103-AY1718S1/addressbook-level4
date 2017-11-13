package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author Melvin-leo
/**
 * consist the date and time of an existing meeting in the address book.
 */
public class DateTime {

    public static final String MESSAGE_DATETIME_CONSTRAINTS =
            "Date and time should only contain numeric characters, colon and spaces, and it should not be blank."
                    + " Date and time should be an actual date and time, with the format dd-MM-yyyy";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String DATETIME_VALIDATION_REGEX = "^(((0[1-9]|[12]\\d|3[01])[\\/\\.-](0[13578]|1[02])"
            + "[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))|((0[1-9]|[12]\\d|30)"
            + "[\\/\\.-](0[13456789]|1[012])[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))"
            + "|((0[1-9]|1\\d|2[0-8])[\\/\\.-](02)[\\/\\.-]((19|[2-9]\\d)\\d{2})\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]))"
            + "|((29)[\\/\\.-](02)[\\/\\.-]((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|"
            + "((16|[2468][048]|[3579][26])00))\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])))$";

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
        this.value = date;
    }

    /**
     * Returns true if a given string is a valid dateTime.
     */
    public static boolean isValidDateTime(String test) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(test, formatter);
            return test.matches(DATETIME_VALIDATION_REGEX);
        } catch (DateTimeException e) {
            return false;
        }
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
