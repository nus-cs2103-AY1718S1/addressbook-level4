package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccount;

//@@author quanle1994

/**
 * Represents a storage for {@link seedu.address.model.Account}.
 */
public interface AccountStorage {

    /**
     * Returns the file path of the data file.
     */
    String getAccountFilePath();

    /**
     * Returns AddressBook data as a {@link seedu.address.model.ReadOnlyAccount}.
     * Returns {@code Optional.empty()} if storage file is not found.
     */
    Optional<ReadOnlyAccount> readAccount() throws FileNotFoundException, DataConversionException;

    /**
     * @see #getAccountFilePath()
     */
    Optional<ReadOnlyAccount> readAccount(String filePath) throws FileNotFoundException, DataConversionException;

    /**
     * Saves the given {@link ReadOnlyAccount} to the storage.
     *
     * @param addressBook cannot be null.
     */
    void saveAccount(ReadOnlyAccount addressBook) throws IOException;

    /**
     * @see #saveAccount(ReadOnlyAccount)
     */
    void saveAccount(ReadOnlyAccount addressBook, String filePath) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
}
