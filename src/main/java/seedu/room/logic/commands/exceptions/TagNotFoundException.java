package seedu.room.logic.commands.exceptions;

/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class TagNotFoundException extends IllegalArgumentException {
    public TagNotFoundException(String message) {
        super(message);
    }
}

