package seedu.address.storage;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.RecycleBin;

//@@author frozventus
/**
 * A storage data holder to hold both addressBook and recycleBin data
 */
public class AddressBookData {

    private ReadOnlyAddressBook addressBook;
    private ReadOnlyAddressBook recycleBin;

    public AddressBookData() {
        addressBook = new AddressBook();
        recycleBin = new RecycleBin();
    }

    public AddressBookData(ReadOnlyAddressBook addressBook, ReadOnlyAddressBook recycleBin) {
        this();
        this.addressBook = addressBook;
        this.recycleBin = recycleBin;
    }

    public void setAddressBook (ReadOnlyAddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void setRecycleBin (ReadOnlyAddressBook recycleBin) {
        this.recycleBin = recycleBin;
    }

    public ReadOnlyAddressBook getAddressBook () {
        return addressBook;
    }

    public ReadOnlyAddressBook getRecycleBin () {
        return recycleBin;
    }
}
