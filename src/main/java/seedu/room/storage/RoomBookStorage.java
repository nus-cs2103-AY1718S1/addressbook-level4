package seedu.room.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyRoomBook;

/**
 * Represents a storage for {@link seedu.room.model.RoomBook}.
 */
public interface RoomBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRoomBookFilePath();

    /**
     * Returns RoomBook data as a {@link ReadOnlyRoomBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyRoomBook> readRoomBook() throws DataConversionException, IOException;

    /**
     * @see #getRoomBookFilePath()
     */
    Optional<ReadOnlyRoomBook> readRoomBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyRoomBook} to the storage.
     * @param roomBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRoomBook(ReadOnlyRoomBook roomBook) throws IOException;

    /**
     * @see #saveRoomBook(ReadOnlyRoomBook)
     */
    void saveRoomBook(ReadOnlyRoomBook roomBook, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyRoomBook} as backup at fixed temporary location.
     *
     * @param roomBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupRoomBook(ReadOnlyRoomBook roomBook) throws IOException;


}
