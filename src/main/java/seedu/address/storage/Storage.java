package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.UserPersonChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.UserPerson;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, UserProfileStorage {

    @Override
    String getUserPrefsFilePath();

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

    //@@author bladerail
    @Override
    String getUserProfileFilePath();

    @Override
    Optional<UserPerson> readUserProfile() throws DataConversionException, IOException;

    @Override
    void saveUserPerson(UserPerson userPerson, String filePath) throws IOException;

    void handleUserPersonChangedEvent(UserPersonChangedEvent upce);

    //@@author

    /**
     * Saves the current version of the Address Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */

    void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);
}
