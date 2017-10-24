package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Requests to restore backup version of Address Book from the default file path
 */
public class RestoreBackupDataEvent extends BaseEvent {
    private ReadOnlyAddressBook backupAddressBookData;

    public void updateAddressBookData(ReadOnlyAddressBook backupAddressBookData) {
        this.backupAddressBookData = backupAddressBookData;
    }

    public ReadOnlyAddressBook getAddressBookData() {
        return backupAddressBookData;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
