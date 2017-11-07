//@@ author hxy0229
package seedu.address.model.group;

import seedu.address.commons.exceptions.DuplicateDataException;

public class DuplicateGroupException extends DuplicateDataException {
    public DuplicateGroupException() {
        super("Operation would result in duplicate group names");
    }
}
