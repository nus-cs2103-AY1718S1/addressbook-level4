package seedu.address.testutil;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;

/**
 * A default storage stub that has all of the methods failing.
 */
public class StorageStub implements Storage {
    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public String getAddressBookFilePath() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
        fail("This method should not be called.");
    }

    @Override
    public String getBackupAddressBookFilePath() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public Optional<ReadOnlyAddressBook> readBackupAddressBook() throws DataConversionException, IOException {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        fail("This method should not be called.");
    }

    @Override
    public String getUserPrefsFilePath() {
        fail("This method should not be called.");
        return null;
    }
}
