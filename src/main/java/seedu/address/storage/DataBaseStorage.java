//@@author cqhchan
package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyDatabase;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface DataBaseStorage {

    /**
     * Returns the file path of the data file.
     */
    String getDatabaseFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyDatabase}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDatabase> readDatabase() throws DataConversionException, IOException;

    /**
     * @see #getDatabaseFilePath()
     */
    Optional<ReadOnlyDatabase> readDatabase(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDatabase} to the storage.
     * @param database cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDatabase(ReadOnlyDatabase database) throws IOException;

    /**
     * @see #saveDatabase(ReadOnlyDatabase) (ReadOnlyAddressBook)
     */
    void saveDatabase(ReadOnlyDatabase database, String filePath) throws IOException;

    /**
     * Saves the given {@link ReadOnlyDatabase} to a fixed temporary location.
     * @param database cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void backupDatabase(ReadOnlyDatabase database) throws IOException;
}
