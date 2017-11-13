package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DisplayPictureChangedEvent;
import seedu.address.commons.events.model.RemindersChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImageDisplayPicture/");

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        RemindersStorage reminderStorage = new XmlRemindersStorage(getTempFilePath("reminder"));
        DisplayPictureStorage displayPictureStorage = new ImageDisplayPictureStorage();
        storageManager = new StorageManager(addressBookStorage, reminderStorage,
                userPrefsStorage, displayPictureStorage);
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

    //@@author justinpoh
    @Test
    public void remindersReadSave() throws Exception {
        UniqueReminderList original = getUniqueTypicalReminders();
        storageManager.saveReminders(original);
        UniqueReminderList retrieved = new UniqueReminderList(storageManager.readReminders().get());
        assertEquals(original, retrieved);
    }
    //@@author

    @Test
    public void addressBookReadBackUp() throws Exception {
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        String filePath = storageManager.getAddressBookFilePath() + "-backup.xml";
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getAddressBookFilePath());
    }

    //@@author justinpoh
    @Test
    public void getRemindersFilePath() {
        assertNotNull(storageManager.getRemindersFilePath());
    }
    //@@author

    @Test
    public void handleAddressBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorageExceptionThrowingStub("dummy"),
                                             new XmlRemindersStorage("dummy"),
                                             new JsonUserPrefsStorage("dummy"),
                new ImageDisplayPictureStorage());
        storage.handleAddressBookChangedEvent(new AddressBookChangedEvent(new AddressBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlAddressBookStorageExceptionThrowingStub extends XmlAddressBookStorage {

        public XmlAddressBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }

    //@@author justinpoh
    @Test
    public void handleRemindersChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage("dummy"),
                new XmlRemindersStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), new ImageDisplayPictureStorage());
        storage.handleRemindersChangedEvent(new RemindersChangedEvent(new UniqueReminderList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRemindersStorageExceptionThrowingStub extends XmlRemindersStorage {

        public XmlRemindersStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveReminders(ReadOnlyUniqueReminderList reminderList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
    //@@author

    //@@author tshradheya

    @Test
    public void handleDisplayPictureChangedEvent_exceptionThrown_eventRaised() throws IOException {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage("dummy"),
                new XmlRemindersStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), new ImageDisplayPictureStorage());
        storage.handleDisplayPictureChangedEvent(new DisplayPictureChangedEvent("dummy", 123));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }
    //@@author

}
