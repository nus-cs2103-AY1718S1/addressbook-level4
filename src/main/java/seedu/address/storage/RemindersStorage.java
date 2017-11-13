package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;

//@@author justinpoh
/**
 * Represents a storage for {@link seedu.address.model.reminders.UniqueReminderList}.
 */

public interface RemindersStorage {
    /**
     * Returns the file path of the data file for reminders.
     */
    String getRemindersFilePath();

    /**
     * Returns UniqueReminderList data as a {@link ReadOnlyUniqueReminderList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUniqueReminderList> readReminders() throws DataConversionException, IOException;

    /**
     * @see #getRemindersFilePath()
     */
    Optional<ReadOnlyUniqueReminderList> readReminders(String filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyUniqueReminderList} to the storage.
     * @param reminderList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveReminders(ReadOnlyUniqueReminderList reminderList) throws IOException;

    /**
     * @see #saveReminders(ReadOnlyUniqueReminderList)
     */
    void saveReminders(ReadOnlyUniqueReminderList reminderList, String filePath) throws IOException;
}
