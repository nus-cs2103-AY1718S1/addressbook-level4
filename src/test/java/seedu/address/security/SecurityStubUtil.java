package seedu.address.security;

import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.exceptions.EncryptOrDecryptException;

import java.io.IOException;

public class SecurityStubUtil {

    private class UnSecuredSecurityStub implements Security {

        @Override
        public void configSecurity(String... permittedCommands) {
        }

        @Override
        public boolean isSecured() {
            return false;
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
            return false;
        }

        @Override
        public void encryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        }

        @Override
        public void decryptAddressBook(String password) throws IOException, EncryptOrDecryptException {
        }
    }

    public void initialUnSecuredSecurity() {
        SecurityManager.getInstance(new UnSecuredSecurityStub());
    }

}
