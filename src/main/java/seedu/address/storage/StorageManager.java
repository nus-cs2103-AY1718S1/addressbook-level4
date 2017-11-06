package seedu.address.storage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.BackupDataEvent;
import seedu.address.commons.events.storage.BackupFilePresentEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.RestoreBackupDataEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

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

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    //@@author LimYangSheng
    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) {
        String backupAddressBookFilePath = createBackupAddressBookFilePath(addressBookStorage.getAddressBookFilePath());
        logger.info("Attempting to backup data to data file: " + backupAddressBookFilePath);
        try {
            saveAddressBook(addressBook, backupAddressBookFilePath);
        } catch (IOException e) {
            raise (new DataSavingExceptionEvent(e));
        }
    }

    /**
     * Creates file path of the backup data file.
     * @param addressBookFilePath cannot be null.
     * @return file path for backup address book.
     */
    private String createBackupAddressBookFilePath(String addressBookFilePath) {
        String nameOfFile = addressBookFilePath.split("[.]")[0];
        String nameOfBackupFile = nameOfFile + "-backup.xml";
        return nameOfBackupFile;
    }
    //@@author

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

    //@@author LimYangSheng
    @Override
    @Subscribe
    public void handleBackupDataEvent(BackupDataEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        backupAddressBook(event.getAddressBookData());
    }

    @Override
    @Subscribe
    public void handleRestoreBackupDataEvent(RestoreBackupDataEvent event) throws DataConversionException, IOException {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        ReadOnlyAddressBook backupAddressBookData;
        String backupFilePath = createBackupAddressBookFilePath(addressBookStorage.getAddressBookFilePath());
        backupAddressBookData = readAddressBook(backupFilePath).get();
        event.updateAddressBookData(backupAddressBookData);
    }

    @Override
    @Subscribe
    public void handleBackupFilePresentEvent(BackupFilePresentEvent event) {
        String backupAddressBookFilePath = createBackupAddressBookFilePath(addressBookStorage.getAddressBookFilePath());
        File f = new File(backupAddressBookFilePath);
        if (f.exists()) {
            event.updateBackupFilePresenceStatus(true);
        } else {
            event.updateBackupFilePresenceStatus(false);
        }
    }
    //@@author

}
