package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.getTypicalEventBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.EventBookChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.AddressBook;
import seedu.address.model.EventBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEventBook;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"),
                getTempFilePath("abcsv"), "Name,Phone,Address,Birthday,Email,Group,Remark,Tagged");
        XmlEventBookStorage eventBookStorage = new XmlEventBookStorage(getTempFilePath("eb"),
                getTempFilePath("ebcsv"), "Title,Description,Location,Datetime");
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        XmlAccountStorage account = new XmlAccountStorage(getTempFilePath("ac"));
        storageManager = new StorageManager(addressBookStorage, eventBookStorage, userPrefsStorage, account);
    }

    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }


    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(300, 600, 4, 6);
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlAddressBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlAddressBookStorageTest} class.
         */
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook().get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy",
                "dummy", "dummy"),
                new XmlEventBookStorageExceptionThrowingStub("dummy", "dummy", "dummy"),
                new JsonUserPrefsStorage("dummy"), new XmlAccountStorage("dummy"));
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void eventBookReadSave() throws Exception {

        EventBook original = getTypicalEventBook();
        storageManager.saveEventBook(original);
        ReadOnlyEventBook retrieved = storageManager.readEventBook().get();
        assertEquals(original, new EventBook(retrieved));
    }

    @Test
    public void getEventBookFilePath() {
        assertNotNull(storageManager.getEventBookFilePath());
    }

    @Test
    public void handleEventBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy",
                "dummy", "dummy"),
                new XmlEventBookStorageExceptionThrowingStub("dummy", "dummy", "dummy"),
                new JsonUserPrefsStorage("dummy"), new XmlAccountStorage("dummy"));
        storage.handleEventBookChangedEvent(new EventBookChangedEvent(new EventBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath, String exportPath, String header) {
            super(filePath, exportPath, header);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlEventBookStorageExceptionThrowingStub extends XmlEventBookStorage {

        public XmlEventBookStorageExceptionThrowingStub(String filePath, String exportPath, String header) {
            super(filePath, exportPath, header);
        }

        @Override
        public void saveEventBook(ReadOnlyEventBook eventBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
}
