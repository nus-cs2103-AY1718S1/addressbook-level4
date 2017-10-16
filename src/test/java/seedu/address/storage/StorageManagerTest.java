package seedu.address.storage;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.model.RolodexChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.Rolodex;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class StorageManagerTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private StorageManager storageManager;

    @Before
    public void setUp() {
        XmlRolodexStorage rolodexStorage = new XmlRolodexStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(rolodexStorage, userPrefsStorage);
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
    public void rolodexReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link XmlRolodexStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link XmlRolodexStorageTest} class.
         */
        Rolodex original = getTypicalRolodex();
        storageManager.saveRolodex(original);
        ReadOnlyRolodex retrieved = storageManager.readRolodex().get();
        assertEquals(original, new Rolodex(retrieved));
    }

    @Test
    public void getRolodexFilePath() {
        assertNotNull(storageManager.getRolodexFilePath());
    }

    @Test
    public void handleRolodexChangedEventExceptionThrownEventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlRolodexStorageExceptionThrowingStub("dummy"),
                                             new JsonUserPrefsStorage("dummy"));
        storage.handleRolodexChangedEvent(new RolodexChangedEvent(new Rolodex()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRolodexStorageExceptionThrowingStub extends XmlRolodexStorage {

        public XmlRolodexStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveRolodex(ReadOnlyRolodex rolodex, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }


}
