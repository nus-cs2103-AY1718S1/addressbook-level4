package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;
//@@author junyango
/**
 * Signals that the operation will result in duplicate Person objects.
 */
public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }
}
