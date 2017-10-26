package seedu.room.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.room.commons.events.model.ResidentBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.model.ReadOnlyResidentBook;
import seedu.room.model.ResidentBook;
import seedu.room.model.UserPrefs;
import seedu.room.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlResidentBookStorage residentBookStorage = new XmlResidentBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(residentBookStorage, userPrefsStorage);
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
    public void residentBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlResidentBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlResidentBookStorageTest} class.
         */
        ResidentBook original = getTypicalResidentBook();
        storageManager.saveResidentBook(original);
        ReadOnlyResidentBook retrieved = storageManager.readResidentBook().get();
        assertEquals(original, new ResidentBook(retrieved));
    }

    @Test
    public void getResidentBookFilePath() {
        assertNotNull(storageManager.getResidentBookFilePath());
    }

    @Test
    public void handleResidentBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlResidentBookStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        storage.handleResidentBookChangedEvent(new ResidentBookChangedEvent(new ResidentBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleBackupResidentBook() throws Exception {
        ResidentBook original = getTypicalResidentBook();
        storageManager.saveResidentBook(original);
        ReadOnlyResidentBook retrieved = storageManager.readResidentBook().get();

        storageManager.backupResidentBook(retrieved);
        ReadOnlyResidentBook backup = storageManager.readBackupResidentBook().get();

        assertEquals(new ResidentBook(retrieved), new ResidentBook(backup));
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlResidentBookStorageExceptionThrowingStub extends XmlResidentBookStorage {

        public XmlResidentBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveResidentBook(ReadOnlyResidentBook residentBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
