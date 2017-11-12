package seedu.address.model.exceptions;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author Sri-vatsa
/**
 * Raises an exception when authentication/authorization fails
 */
public class AsanaAuthenticationException extends IllegalValueException {
    public AsanaAuthenticationException(String message) {
        super(message);
    }
}
