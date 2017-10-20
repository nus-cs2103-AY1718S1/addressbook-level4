package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {
    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {
        private XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab.xml"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs.json"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link JsonUserPrefsStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
     */
    @Test
    public void prefsReadSave() throws Exception {
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    /*
     * Note: This is an integration test that verifies the StorageManager is properly wired to the
     * {@link XmlAddressBookStorage} class.
     * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
     */
    @Test
    public void addressBookReadSave() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    /**
     * Checks whether the addressBook storage file is backed up to the expected location.
     */
    @Test
    public void addressBookBackup() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);

        String oldPath = storageManager.getAddressBookFilePath();
        String newPath = storageManager.removeFileExtension(oldPath) + "-backup.xml";
        Optional<ReadOnlyAddressBook> retrieved = storageManager.readAddressBook(newPath);
        assertTrue(retrieved.isPresent());
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() throws Exception {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy.xml"),
                                             new JsonUserPrefsStorage("dummy.json"));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
}
