package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DisplayPictureChangedEvent;
import seedu.address.commons.events.model.RemindersChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, RemindersStorage, DisplayPictureStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getAddressBookFilePath();

    @Override
    String getRemindersFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    //@@author justinpoh
    @Override
    Optional<ReadOnlyUniqueReminderList> readReminders() throws DataConversionException, IOException;
    //@@author

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    //@@author justinpoh
    @Override
    void saveReminders(ReadOnlyUniqueReminderList reminderList) throws IOException;
    //@@author

    /**
     * Saves the current version of the Address Book to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleAddressBookChangedEvent(AddressBookChangedEvent abce);

    //@@author justinpoh
    /**
     * Saves the current version of reminders to the hard disk.
     * Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRemindersChangedEvent(RemindersChangedEvent rce);
    //@@author

    void handleDisplayPictureChangedEvent(DisplayPictureChangedEvent event) throws IOException;

}
