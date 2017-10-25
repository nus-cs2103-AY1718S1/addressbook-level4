package seedu.address.security;

import java.io.IOException;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;

/**
 * Provides Security Stubs for testing.
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
        }

        @Override
        public boolean isSecured() {
            return isSecured;
        }

        @Override
        public void raise(BaseEvent event) {
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
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        }
    }

    /**
     * Represents a SecurityManager which indicates that the address book is secured.
     */
    private class SecurityStubIoexception extends BaseSecurityStub {


        public SecurityStubIoexception(boolean isSecured) {
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
        SecurityManager.getInstance(new BaseSecurityStub(false));
    }

    public void initialSecuredSecurity() {
        SecurityManager.getInstance(new BaseSecurityStub(true));
    }

    public void initialSecurityWithIoexception(boolean isSecured) {
        SecurityManager.getInstance(new SecurityStubIoexception(isSecured));
    }

    public void initialSecurityWithEncryptOrDecryptException(boolean isSecured) {
        SecurityManager.getInstance(new SecurityStubEncryptOrDecryptException(isSecured));
    }
}
