package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyEventList;

/**
 * Represents a storage for {@link seedu.address.model.EventList}.
 */
public interface EventStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEventStorageFilePath();

    /**
     * Returns Event data as a {@link ReadOnlyEventList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEventList> readEventStorage() throws DataConversionException, IOException;

    /**
     * @see #getEventStorageFilePath()
     */
    Optional<ReadOnlyEventList> readEventStorage(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEventList} to the storage.
     * @param eventStorage cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventStorage(ReadOnlyEventList eventStorage) throws IOException;

    /**
     * @see #saveEventStorage(ReadOnlyEventList)
     */
    void saveEventStorage(ReadOnlyEventList eventStorage, String filePath) throws IOException;

}
