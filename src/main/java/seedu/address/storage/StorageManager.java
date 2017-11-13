package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AccountChangedEvent;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.EventBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccount;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private EventBookStorage eventBookStorage;
    private UserPrefsStorage userPrefsStorage;
    private AccountStorage accountStorage;

    public StorageManager(AddressBookStorage addressBookStorage, EventBookStorage eventBookStorage,
                          UserPrefsStorage userPrefsStorage, AccountStorage accountStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.eventBookStorage = eventBookStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.accountStorage = accountStorage;
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

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        String filePath = getAddressBookFilePath().substring(0, getAddressBookFilePath().indexOf('.')) + "_backup.xml";
        addressBookStorage.saveAddressBook(addressBook, filePath);

    }

    @Override
    public void exportAddressBook() throws FileNotFoundException, ParserConfigurationException,
            IOException, SAXException, TransformerException {
        addressBookStorage.exportAddressBook();
    }

    // ================ Account methods ===================================
    @Override
    public String getAccountFilePath() {
        return accountStorage.getAccountFilePath();
    }

    @Override
    public Optional<ReadOnlyAccount> readAccount() throws FileNotFoundException, DataConversionException {
        return readAccount(accountStorage.getAccountFilePath());
    }

    @Override
    public Optional<ReadOnlyAccount> readAccount(String filePath)
            throws FileNotFoundException, DataConversionException {
        logger.fine("Attempting to read data from account file: " + filePath);
        return accountStorage.readAccount(filePath);
    }

    @Override
    public void saveAccount(ReadOnlyAccount account) throws IOException {
        saveAccount(account, accountStorage.getAccountFilePath());
    }

    @Override
    public void saveAccount(ReadOnlyAccount account, String filePath) throws IOException {
        logger.fine("Attempting to write to account file: " + filePath);
        accountStorage.saveAccount(account, filePath);
    }


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

    @Override
    @Subscribe
    public void handleAccountChangeEvent(AccountChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local accounts changed, saving to accounts file"));
        try {
            saveAccount(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }
    // ================ EventBook methods ==============================

    @Override
    public String getEventBookFilePath() {
        return eventBookStorage.getEventBookFilePath();
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook() throws DataConversionException, IOException {
        return readEventBook(eventBookStorage.getEventBookFilePath());
    }

    @Override
    public Optional<ReadOnlyEventBook> readEventBook(String filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventBookStorage.readEventBook(filePath);
    }

    @Override
    public void exportEventBook() throws FileNotFoundException, ParserConfigurationException,
            IOException, SAXException, TransformerException {
        eventBookStorage.exportEventBook();
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook) throws IOException {
        saveEventBook(eventBook, eventBookStorage.getEventBookFilePath());
    }

    @Override
    public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventBookStorage.saveEventBook(eventBook, filePath);
    }

    @Override
    public void backupEventBook(ReadOnlyEventBook eventBook) throws IOException {
        String filePath = getEventBookFilePath().substring(0, getEventBookFilePath().indexOf('.')) + "_backup.xml";
        eventBookStorage.saveEventBook(eventBook, filePath);
    }

    @Override
    @Subscribe
    public void handleEventBookChangedEvent(EventBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to file"));
        try {
            saveEventBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    public AccountStorage getAccountStorage() {
        return accountStorage;
    }

    public void setAccountStorage(AccountStorage accountStorage) {
        this.accountStorage = accountStorage;
    }
}
