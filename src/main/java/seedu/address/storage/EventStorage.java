package seedu.address.storage;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.event.ReadOnlyEventStorage;
import java.io.IOException;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.address.model.event.Event).
 */
public interface EventStorage {

    /**
     * Returns the file path of the data file.
     */
    String getEventStorageFilePath();

    /**
     * Returns Event data as a {@link ReadOnlyEventStorage}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyEventStorage> readEventStorage() throws DataConversionException, IOException;

    /**
     * @see #getEventStorageFilePath()
     */
    Optional<ReadOnlyEventStorage> readEventStorage(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyEventStorage} to the storage.
     * @param eventStorage cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveEventStorage(ReadOnlyEventStorage eventStorage) throws IOException;

    /**
     * @see #saveEventStorage(ReadOnlyEventStorage)
     */
    void saveEventStorage(ReadOnlyEventStorage eventStorage, String filePath) throws IOException;

}
