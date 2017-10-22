package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.BackupDataEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.events.storage.RestoreBackupDataEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Saves the current version of the Address Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    /**
     * Create a backup of the current version of Address Book to the hard disk.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleBackupDataEvent(BackupDataEvent event);

    /**
     * Replace the current Address Book with the backup version of Address Book.
     */
    void handleRestoreBackupDataEvent(RestoreBackupDataEvent event) throws DataConversionException, IOException;
}
