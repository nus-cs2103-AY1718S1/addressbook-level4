package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();
    String getBackUpAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<AddressBookData> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<AddressBookData> readAddressBook(String filePath) throws DataConversionException, IOException;

    /**
     * Returns AddressBook back up data as a {@link ReadOnlyAddressBook}.
     *  Returns {@code Optional.empty()} if storage file is not found.
     *  @throws DataConversionException if the data in storage is not in the expected format.
     *  @throws IOException if there was any problem when reading from the stoage.
     */
    Optional<AddressBookData> readBackUpAddressBook() throws DataConversionException, IOException;

    /**
     * Saves the given {@link AddressBookData} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(AddressBookData addressBook) throws IOException;

    /**
     * @see #saveAddressBook(AddressBookData)
     */
    void saveAddressBook(AddressBookData addressBook, String filePath)
            throws IOException;

}
