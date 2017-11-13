package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private UserPrefsStorage userPrefsStorage;

    private HashSet<String> usedBackupLocationNames;


    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;

        this.usedBackupLocationNames = new HashSet<>();
    }
    //@@author justintkj
    /**
     * Extracts and Returns an Arraylist of strings to be used in autocomplete from XML
     *
     * @return ArrayList of String with valid inputs
     * @throws IOException unable to create new XML file
     */
    public ArrayList<String> updateAutocomplete() throws IOException {
        try {
            return XmlAutocomplete.updateAutocompleteWithStorageFile();
        } catch (Exception ex) {
            return XmlAutocomplete.createNewStorageFile(ex);
        }
    }

    /**
     * Adds a new command string to the XML file for autocomplete
     * @param commandWord command to be added
     * @return new ArrayList including the new valid command
     * @throws CommandException if autocomplete.xml cannot be made.
     */
    public ArrayList<String> setAddSuggestion(String commandWord) throws CommandException {
        return XmlAutocomplete.setAddSuggestion(commandWord);
    }
    //@@author
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
        while (usedBackupLocationNames.contains(filePath)) {
            logger.fine("File path \"" + filePath + "\"is occupied by a backup");
            filePath += "-1";
        }
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook, String backupLocationName) throws IOException {
        while (usedBackupLocationNames.contains(backupLocationName)) {
            logger.fine("File path \"" + backupLocationName + "\" is occupied by an existing backup");
            backupLocationName += "-1";
        }
        logger.fine("Instead saving backup to: " + backupLocationName);
        saveAddressBook(addressBook, backupLocationName);
        usedBackupLocationNames.add(backupLocationName);
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


}
