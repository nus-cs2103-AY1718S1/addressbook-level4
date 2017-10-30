package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Signals that some given data does not fulfill some constraints.
 */
public class InvalidSortTypeException extends IllegalValueException {
    public InvalidSortTypeException(String message) {
        super(message);
    }

    public InvalidSortTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
