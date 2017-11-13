package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * A Backup event indicating that the user is requesting to save a backup of Tourist Book.
 */
//@@author huyuanrong
public class BackupEvent extends BaseEvent {

    public final ReadOnlyAddressBook data;

    public BackupEvent(ReadOnlyAddressBook data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
//@@author
