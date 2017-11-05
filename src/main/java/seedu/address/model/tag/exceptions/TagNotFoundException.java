package seedu.address.model.tag.exceptions;

/**
 * Signals that the required property has not been defined yet.
 */
public class TagNotFoundException extends Exception {
    public TagNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
