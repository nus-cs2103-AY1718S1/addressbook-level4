package seedu.room.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.room.commons.events.model.ResidentBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ResidentBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getResidentBookFilePath();

    @Override
    Optional<ReadOnlyResidentBook> readResidentBook() throws DataConversionException, IOException;

    @Override
    void saveResidentBook(ReadOnlyResidentBook residentBook) throws IOException;

    /**
     * Saves the current version of the Resident Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleResidentBookChangedEvent(ResidentBookChangedEvent abce);
}
