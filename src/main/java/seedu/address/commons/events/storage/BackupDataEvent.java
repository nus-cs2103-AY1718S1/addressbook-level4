package seedu.address.commons.events.storage;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author LimYangSheng
/**
 * Requests for a backup of the current address book.
 */
public class BackupDataEvent extends BaseEvent {
    private ReadOnlyAddressBook addressBookData;

    public BackupDataEvent(ReadOnlyAddressBook addressBookData) {
        this.addressBookData = addressBookData;
    }

    public ReadOnlyAddressBook getAddressBookData() {
        return addressBookData;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
