package seedu.room.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.room.commons.events.model.RoomBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyRoomBook;
import seedu.room.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends RoomBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(UserPrefs userPrefs) throws IOException;

    @Override
    String getRoomBookFilePath();

    @Override
    Optional<ReadOnlyRoomBook> readRoomBook() throws DataConversionException, IOException;

    @Override
    void saveRoomBook(ReadOnlyRoomBook roomBook) throws IOException;

    /**
     * Saves the current version of the Room Book to the hard disk.
     *   Creates the data file if it is missing.
     * Raises {@link DataSavingExceptionEvent} if there was an error during saving.
     */
    void handleRoomBookChangedEvent(RoomBookChangedEvent abce);
}
