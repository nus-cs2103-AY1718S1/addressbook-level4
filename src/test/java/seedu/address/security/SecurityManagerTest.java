package seedu.address.security;

import java.io.IOException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.EncryptOrDecryptException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.Storage;

public class SecurityManagerTest {

    private Storage storage = new StorageStub();

    @Test
    public void test_equalInstance() {
        Security instance1 = SecurityManager.getInstance(storage);
        Security instance2 = SecurityManager.getInstance();
        Security instance3 = SecurityManager.getInstance(instance1);
        Security instance4 = SecurityManager.getInstance(instance2);

        Assert.assertTrue(instance1 == instance2);
        Assert.assertTrue(instance2 == instance3);
        Assert.assertTrue(instance3 == instance4);
    }

    @Test
    public void test_commandBlocking() {
        Security security = SecurityManager.getInstance(storage);
        security.configSecurity("add", "find");

        Assert.assertTrue(security.isPermittedCommand("add"));
        Assert.assertTrue(security.isPermittedCommand("find"));

        Assert.assertFalse(security.isPermittedCommand(""));
        Assert.assertFalse(security.isPermittedCommand("list"));
    }

    @Test
    public void test_raise() {
        Security security = SecurityManager.getInstance(storage);
        security.raise(new NewResultAvailableEvent("new result"));
    }

    @Test
    public void test_isSecured() {
        Security security = SecurityManager.getInstance(storage);
        Assert.assertFalse(security.isSecured());
    }

    @Test
    public void test_isEncrypted() throws IOException {
        Security security = SecurityManager.getInstance(storage);
        Assert.assertFalse(security.isEncrypted());
    }

    @Test
    public void test_encryptAddressBook() throws IOException, EncryptOrDecryptException {
        Security security = SecurityManager.getInstance(storage);
        security.encryptAddressBook("");
    }

    @Test
    public void test_decryptAddressBook() throws IOException, EncryptOrDecryptException {
        Security security = SecurityManager.getInstance(storage);
        security.decryptAddressBook("");
    }

    private class StorageStub implements Storage {

        @Override
        public String getUserPrefsFilePath() {
            return null;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
            return null;
        }

        @Override
        public void saveUserPrefs(UserPrefs userPrefs) throws IOException {

        }

        @Override
        public String getAddressBookFilePath() {
            return null;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws
                DataConversionException, IOException {
            return null;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(String filePath)
                throws DataConversionException, IOException {
            return null;
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {

        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {

        }

        @Override
        public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {

        }

        @Override
        public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {

        }

        @Override
        public boolean isEncrypted() throws IOException {
            return false;
        }

        @Override
        public void encryptAddressBook(String password)
                throws IOException, EncryptOrDecryptException {

        }

        @Override
        public void decryptAddressBook(String password)
                throws IOException, EncryptOrDecryptException {

        }
    }
}
