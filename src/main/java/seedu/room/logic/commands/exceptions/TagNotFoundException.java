package seedu.room.logic.commands.exceptions;

//@@author shitian007
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(String message) {
        super(message);
    }
}

