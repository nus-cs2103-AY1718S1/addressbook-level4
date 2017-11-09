package seedu.room.model.person.exceptions;

//@@author shitian007
/**
 * Signals that there is no resident currently highlighted
 */
public class NoneHighlightedException extends IllegalArgumentException {
    public NoneHighlightedException(String message) {
        super(message);
    }
}
