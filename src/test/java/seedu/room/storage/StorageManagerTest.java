package seedu.room.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.room.testutil.TypicalPersons.getTypicalRoomBook;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.room.commons.events.model.RoomBookChangedEvent;
import seedu.room.commons.events.storage.DataSavingExceptionEvent;
import seedu.room.model.RoomBook;
import seedu.room.model.ReadOnlyRoomBook;
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
        XmlRoomBookStorage roomBookStorage = new XmlRoomBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(roomBookStorage, userPrefsStorage);
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
    public void roomBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlRoomBookStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlRoomBookStorageTest} class.
         */
        RoomBook original = getTypicalRoomBook();
        storageManager.saveRoomBook(original);
        ReadOnlyRoomBook retrieved = storageManager.readRoomBook().get();
        assertEquals(original, new RoomBook(retrieved));
    }

    @Test
    public void getRoomBookFilePath() {
        assertNotNull(storageManager.getRoomBookFilePath());
    }

    @Test
    public void handleRoomBookChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlRoomBookStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"));
        storage.handleRoomBookChangedEvent(new RoomBookChangedEvent(new RoomBook()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }

    @Test
    public void handleBackupRoomBook() throws Exception {
        RoomBook original = getTypicalRoomBook();
        storageManager.saveRoomBook(original);
        ReadOnlyRoomBook retrieved = storageManager.readRoomBook().get();

        storageManager.backupRoomBook(retrieved);
        ReadOnlyRoomBook backup = storageManager.readBackupRoomBook().get();

        assertEquals(new RoomBook(retrieved), new RoomBook(backup));
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRoomBookStorageExceptionThrowingStub extends XmlRoomBookStorage {

        public XmlRoomBookStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveRoomBook(ReadOnlyRoomBook roomBook, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
