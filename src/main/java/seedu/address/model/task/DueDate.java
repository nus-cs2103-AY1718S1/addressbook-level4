package seedu.address.model.task;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author zhangshuoyang
/**
 * Represents the date of a certain task in the Address Book.
 */

public class DueDate extends Date {


    public final String date;


    /**
     * Validates given due date.
     *
     * @throws IllegalValueException if the given date string is invalid
     */
    public DueDate (String input) throws IllegalValueException {
        requireNonNull(input);
        String trimmedInput = input.trim();
        if (!Date.isValidDate(trimmedInput) && !trimmedInput.isEmpty()) {
            if (!Date.isValidDate(trimmedInput)) {
                throw new IllegalValueException(MESSAGE_DATE_INVALID_CONSTRAINTS);
            }
            throw new IllegalValueException(MESSAGE_DATE_FORMAT_CONSTRAINTS);
        }
        this.date = trimmedInput;
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if sme object
                        || (other instanceof DueDate // instanceof handles nulls
                        && this.date.equals(((DueDate) other).date));
    }

    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
