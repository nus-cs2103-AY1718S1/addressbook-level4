package seedu.address.testutil;

import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;

public class TypicalStorage {

    private StorageManager storageManager;
    private XmlAddressBookStorage addressBookStorage;
    private JsonUserPrefsStorage userPrefsStorage;

    public StorageManager setUp() {
        addressBookStorage = new XmlAddressBookStorage("data\\ab");
        userPrefsStorage = new JsonUserPrefsStorage("data\\prefs");
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        return storageManager;
    }
}
