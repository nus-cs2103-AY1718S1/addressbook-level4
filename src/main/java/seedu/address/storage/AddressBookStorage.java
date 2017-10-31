package seedu.address.storage;

import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidFilePathException;
import seedu.address.model.ReadOnlyAddressBook;

/**
 * Represents a storage for {@link seedu.address.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException, InvalidFilePathException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws DataConversionException, IOException,
            InvalidFilePathException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException, InvalidFilePathException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException,
            InvalidFilePathException;

    /**
     * Makes a local backup of the address book storage file.
     */
    void backupAddressBook (ReadOnlyAddressBook addressBook) throws IOException, InvalidFilePathException;

    /**
     * Removes the extension part of a file name (the part after the last dot {@code .} in the given string).
     *
     * @param fileName is the file name with an extension.
     * @return the file name without the extension part.
     */
    default String removeFileExtension(String fileName) {
        return fileName.replaceFirst("[.][^.]+$", "");
    }
}
