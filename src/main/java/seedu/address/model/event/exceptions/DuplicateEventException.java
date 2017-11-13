package seedu.address.model.event.exceptions;

//@@author chernghann
import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Event objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate event");
    }
}
