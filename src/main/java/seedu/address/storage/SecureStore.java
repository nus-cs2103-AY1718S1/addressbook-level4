package seedu.address.storage;

import seedu.address.commons.exceptions.EncryptOrDecryptException;

import java.io.IOException;

public interface SecureStore {

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
