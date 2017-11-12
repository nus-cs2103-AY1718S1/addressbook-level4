//@@author Hailinx
package seedu.address.security;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.events.model.AddressBookChangedEvent;
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
        SecurityManager.setInstance(instance1);
        Security instance3 = SecurityManager.getInstance();
        SecurityManager.setInstance(instance2);
        Security instance4 = SecurityManager.getInstance();

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
    public void test_encryptAddressBook() {
        try {
            Security security = SecurityManager.getInstance(storage);
            security.encryptAddressBook("");
        } catch (IOException | EncryptOrDecryptException e) {
            e.printStackTrace();
            Assert.fail("Should not throw exception.");
        }
    }

    @Test
    public void test_decryptAddressBook() {
        try {
            Security security = SecurityManager.getInstance(storage);
            security.decryptAddressBook("");
        } catch (IOException | EncryptOrDecryptException e) {
            e.printStackTrace();
            Assert.fail("Should not throw exception.");
        }
    }

    @After
    public void after() {
        SecurityManager.setInstance(null);
    }

    private class StorageStub implements Storage {

        @Override
        public String getUserPrefsFilePath() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
            fail("This method should not be called.");
        }

        @Override
        public String getAddressBookFilePath() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook() throws
                DataConversionException, IOException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public Optional<ReadOnlyAddressBook> readAddressBook(String filePath)
                throws DataConversionException, IOException {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            fail("This method should not be called.");
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
            fail("This method should not be called.");
        }

        @Override
        public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
            fail("This method should not be called.");
        }

        @Override
        public void handleAddressBookChangedEvent(AddressBookChangedEvent abce) {
            fail("This method should not be called.");
        }

        @Override
        public boolean isEncrypted() throws IOException {
            return false;
        }

        @Override
        public void encryptAddressBook(String password)
                throws IOException, EncryptOrDecryptException {
            // simulate the execution
        }

        @Override
        public void decryptAddressBook(String password)
                throws IOException, EncryptOrDecryptException {
            // simulate the execution
        }
    }
}
