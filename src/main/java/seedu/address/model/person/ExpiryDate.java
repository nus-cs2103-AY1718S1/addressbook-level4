package seedu.address.model.person;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's expiry date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidExpiryDate(String)}
 */
public class ExpiryDate {

    public static final String MESSAGE_EXPIRY_DATE_CONSTRAINTS =
            "Person's expiry date should be dates later than current date, in the form of YYYY-MM-DD";

    /*
     * Date format is YYYY-MM-DD
     */
    public static final String DATE_VALIDATION_REGEX = "\\d{4}-\\d{2}-\\d{2}";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DATE_FORMAT);

    public final Date value;

    /**
     * Validates given date.
     *     Input string can be empty (expiry date = NULL)
     *
     * @throws IllegalValueException if given date string is invalid.
     */
    public ExpiryDate(String date) throws IllegalValueException {
        if (date == null) {
            this.value = null;
            return;
        } else if (date.isEmpty()) {
            this.value = null;
            return;
        } else if (!isValidExpiryDate(date)) {
            throw new IllegalValueException(MESSAGE_EXPIRY_DATE_CONSTRAINTS);
        }
        // catches invalid month/day combination .NOT
        ParsePosition parsePos = new ParsePosition(0);
        this.value = DATE_FORMATTER.parse(date, parsePos);

        // date formatter parse error
        if (parsePos.getIndex() == 0) {
            throw new IllegalValueException(MESSAGE_EXPIRY_DATE_CONSTRAINTS);
        }
    }

    /**
     * Returns true if a given string is a valid date format.
     */
    public static boolean isValidExpiryDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value == null) {
            return "";
        } else {
            return DATE_FORMATTER.format(value);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // same object
            return true;
        } else if (this.value == null) {
            // to prevent NullPointerException from expiry date
            // both objects have null expiry date
            return ((ExpiryDate) other).value == null;
        } else {
            // date must not be null
            return this.value.equals(((ExpiryDate) other).value);
        }
    }
}
