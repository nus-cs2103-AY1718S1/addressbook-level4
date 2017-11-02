//@@author hthjthtrh
package seedu.address.model.group;

import seedu.address.commons.exceptions.DuplicateDataException;

/**
 * Signals that the operation will result in duplicate Group objects.
 */
public class DuplicateGroupException extends DuplicateDataException {

    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
//@@author
