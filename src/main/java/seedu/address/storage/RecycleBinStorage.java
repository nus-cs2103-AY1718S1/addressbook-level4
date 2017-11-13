package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
//@@author Pengyuz
/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface RecycleBinStorage {

    /**
     * Returns the file path of the data file.
     */
    String getRecycleBinFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readRecycleBin() throws DataConversionException, IOException;

    /**
     * @see #getRecycleBinFilePath() ()
     */
    Optional<ReadOnlyAddressBook> readRecycleBin(String filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveRecycleBin(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveRecycleBin(ReadOnlyAddressBook) (ReadOnlyAddressBook)
     */
    void saveRecycleBin(ReadOnlyAddressBook addressBook, String filePath) throws IOException;

}
