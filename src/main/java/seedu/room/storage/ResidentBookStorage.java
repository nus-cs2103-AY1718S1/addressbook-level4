package seedu.room.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.room.commons.exceptions.DataConversionException;
import seedu.room.model.ReadOnlyResidentBook;

/**
 * Represents a storage for {@link seedu.room.model.ResidentBook}.
 */
public interface ResidentBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getResidentBookFilePath();

    /**
     * Returns ResidentBook data as a {@link ReadOnlyResidentBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyResidentBook> readResidentBook() throws DataConversionException, IOException;

    /**
     * @see #getResidentBookFilePath()
     */
    Optional<ReadOnlyResidentBook> readResidentBook(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyResidentBook} to the storage.
     * @param residentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveResidentBook(ReadOnlyResidentBook residentBook) throws IOException;

    /**
     * @see #saveResidentBook(ReadOnlyResidentBook)
     */
    void saveResidentBook(ReadOnlyResidentBook residentBook, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyResidentBook} as backup at fixed temporary location.
     *
     * @param residentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupResidentBook(ReadOnlyResidentBook residentBook) throws IOException;


}
