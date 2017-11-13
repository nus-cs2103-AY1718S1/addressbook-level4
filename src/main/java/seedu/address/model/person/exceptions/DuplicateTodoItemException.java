//@@author Hailinx
package seedu.address.model.person.exceptions;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate TodoItem objects.
 */
public class DuplicateTodoItemException extends DuplicateDataException {
    public DuplicateTodoItemException() {
        super("Operation would result in duplicate todoItem");
    }
}
