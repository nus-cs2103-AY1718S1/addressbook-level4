package seedu.address.testutil;

import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;

//@@author LimeFallacie
/**
 * A utility class containing a constructor for a dummy storage object
 */
public class TypicalStorage {

    public StorageManager setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage("data\\ab");
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage("data\\prefs");
        StorageManager storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        return storageManager;
    }
}
