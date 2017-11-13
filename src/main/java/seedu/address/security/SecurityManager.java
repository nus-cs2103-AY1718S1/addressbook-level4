//@@author Hailinx
package seedu.address.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.storage.Storage;

/**
 * Manages the data security of AddressBook.
 */
public class SecurityManager extends ComponentManager implements Security {

    private static Security instance;

    private Storage storage;
    private List<String> permittedCommandList;

    private SecurityManager(Storage storage) {
        this.storage = storage;
        permittedCommandList = new ArrayList<>();
    }

    public static Security getInstance() {
        return instance;
    }

    public static Security getInstance(Storage storage) {
        if (instance == null) {
            instance = new SecurityManager(storage);
        }
        return instance;
    }

    /**
     * Is used for testing.
     */
    public static void setInstance(Security security) {
        instance = security;
    }

    @Override
    public void configSecurity(String... permittedCommands) {
        permittedCommandList.addAll(Arrays.asList(permittedCommands));
    }

    @Override
    public boolean isSecured() {
        try {
            return isEncrypted();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void raise(BaseEvent event) {
        super.raise(event);
    }

    @Override
    public boolean isPermittedCommand(String commandName) {
        return permittedCommandList.contains(commandName);
    }

    @Override
    public boolean isEncrypted() throws IOException {
        return storage.isEncrypted();
    }

    @Override
    public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        storage.encryptAddressBook(password);
    }

    @Override
    public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        storage.decryptAddressBook(password);
    }
}
