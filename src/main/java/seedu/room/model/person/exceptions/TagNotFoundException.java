package seedu.room.model.person.exceptions;

//@@author shitian007
/**
 * Signals that the tag name specified in the operation does not exist in resident book
 */
public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(String message) {
        super(message);
    }
}

