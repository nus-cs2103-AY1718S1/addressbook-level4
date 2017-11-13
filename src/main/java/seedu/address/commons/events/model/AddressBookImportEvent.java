package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ReadOnlyAddressBook;

//@@author Choony93

/**
 * Indicates the AddressBook calling for import
 */
public class AddressBookImportEvent extends BaseEvent {

    public final String filePath;
    public final ReadOnlyAddressBook importedBook;

    public AddressBookImportEvent(String filePath, ReadOnlyAddressBook importedBook) {
        this.filePath = filePath;
        this.importedBook = importedBook;
    }

    @Override
    public String toString() {
        return "New Addressbook imported from file: " + filePath;
    }
}
//@@author
