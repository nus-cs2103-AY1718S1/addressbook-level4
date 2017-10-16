package seedu.address.model.event.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

public class DuplicateEventException extends DuplicateDataException {
    public DuplicateEventException() {
        super("Operation would result in duplicate events");
    }

}
