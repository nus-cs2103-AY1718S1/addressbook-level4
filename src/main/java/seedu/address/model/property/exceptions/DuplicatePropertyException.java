package seedu.address.model.property.exceptions;

//@@author yunpengn
/**
 * Signals that the property with the same short name already exists.
 */
public class DuplicatePropertyException extends Exception {
    public DuplicatePropertyException(String message) {
        super(message);
    }
}
