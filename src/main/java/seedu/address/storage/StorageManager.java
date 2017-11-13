package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    //@@author darrinloh
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;

        Optional<AddressBookData> addressBookOptional;
        try {
            addressBookOptional = addressBookStorage.readAddressBook();
            if (addressBookOptional.isPresent()) {
                backUpAddressBook(addressBookOptional.get());
                logger.info("AddressBook found, backup initiated.");
            } else {
                logger.warning("AddressBook not found, backup will not initiate.");
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Backup will not initiate.");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Backup will not initiate.");
        }

    }
    //@@author darrinloh

    // ================ UserPrefs methods ==============================

    @Override
    public String getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public String getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    //@@author darrinloh
    @Override
    public String getBackUpAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath() + "-backup.xml";
    }


    @Override
    public Optional<AddressBookData> readBackUpAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath() + "-backup.xml");
    }
    //@@author darrinloh

    @Override
    public Optional<AddressBookData> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<AddressBookData> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(AddressBookData addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(AddressBookData addressBook, String filePath)
            throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    //@@author darrinloh
    private void backUpAddressBook(AddressBookData addressBook) throws IOException {
        saveAddressBook(addressBook, getLocalBackUpAddressBookFilePath());
    }

    public String getLocalBackUpAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath() + "-backup.xml";
    }
    //@@author darrinloh

    //@@author frozventus
    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
