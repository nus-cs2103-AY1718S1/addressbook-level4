//@@author Hailinx
package seedu.address.security;

import static org.junit.Assert.fail;

import java.io.IOException;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Provides three Security Stubs for testing.
 */
public class SecurityStubUtil {

    /**
     * Represents a basic SecurityManager stub.
     */
    private class BaseSecurityStub implements Security {

        private boolean isSecured;

        public BaseSecurityStub(boolean isSecured) {
            super();
            this.isSecured = isSecured;
        }

        @Override
        public void configSecurity(String... permittedCommands) {
            fail("This method should not be called.");
        }

        @Override
        public boolean isSecured() {
            return isSecured;
        }

        @Override
        public void raise(BaseEvent event) {
            EventsCenter.getInstance().post(event);
        }

        @Override
        public boolean isPermittedCommand(String commandWord) {
            return false;
        }

        @Override
        public boolean isEncrypted() throws IOException {
            return isSecured;
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            // simulate the execution
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            // simulate the execution
        }
    }

    /**
     * Represents a SecurityManager which indicates that the address book is secured.
     */
    private class SecurityStubIoException extends BaseSecurityStub {


        public SecurityStubIoException(boolean isSecured) {
            super(isSecured);
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new IOException();
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new IOException();
        }
    }

    /**
     * Represents a SecurityManager which indicates that the address book is secured.
     * Throws EncryptOrDecryptException when call encryptAddressBook and decryptAddressBook methods.
     */
    private class SecurityStubEncryptOrDecryptException extends BaseSecurityStub {


        public SecurityStubEncryptOrDecryptException(boolean isSecured) {
            super(isSecured);
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new EncryptOrDecryptException();
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
            throw new EncryptOrDecryptException();
        }
    }

    public void initialUnSecuredSecurity() {
        SecurityManager.setInstance(new BaseSecurityStub(false));
    }

    public void initialSecuredSecurity() {
        SecurityManager.setInstance(new BaseSecurityStub(true));
    }

    public void initialSecurityWithIoException(boolean isSecured) {
        SecurityManager.setInstance(new SecurityStubIoException(isSecured));
    }

    public void initialSecurityWithEncryptOrDecryptException(boolean isSecured) {
        SecurityManager.setInstance(new SecurityStubEncryptOrDecryptException(isSecured));
    }
}
