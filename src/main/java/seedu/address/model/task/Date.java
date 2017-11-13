// @@author tanchc
package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents teh date of the Task in the address book.
 */
public class Date {
    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Date must be in the format of DD/MM/YYYY";

    public static final String DATE_VALIDATION_REGEX =
            "\\d{2}/\\d{2}/\\d{4}";

    public final String value;

    //Validates given Date.
    public Date(String date) throws IllegalValueException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!isValidDate(trimmedDate)) {
            throw new IllegalValueException(MESSAGE_DATE_CONSTRAINTS);
        }
        this.value = trimmedDate;
    }

    /**
     * Returns true if a given String is a valid Date.
     */
    public static boolean isValidDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this //short circuit if same object
                || (other instanceof Date
                && this.value.equals(((Date) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
