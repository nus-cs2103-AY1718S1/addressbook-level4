//@@author Hailinx
package seedu.address.storage;

import java.io.IOException;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Represents a secure storage which security methods.
 */
public interface SecureStorage {

    /**
     * @see #isEncrypted()
     */
    boolean isEncrypted() throws IOException;

    /**
     * @see #encryptAddressBook(String)
     */
    void encryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException;

    /**
     * @see #decryptAddressBook(String)
     */
    void decryptAddressBook(String password)
            throws IOException, EncryptOrDecryptException;
}
