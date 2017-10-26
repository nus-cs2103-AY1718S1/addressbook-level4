package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.events.storage.BackupFilePresentEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.ui.testutil.EventsCollectorRule;

public class RestoreBackupCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private StorageManager storageManager;
    private AddressBookStorage addressBookStorage;
    private String filePath;
    private String backupFilePath;
    private RestoreBackupCommand restoreBackupCommand;

    @Before
    public void setUp() throws Exception {
        filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        backupFilePath = testFolder.getRoot().getPath() + "TempAddressBook-backup.xml";
        addressBookStorage = new XmlAddressBookStorage(filePath);

        model = new ModelManager(new AddressBook(), new UserPrefs());
        storageManager = new StorageManager(addressBookStorage,
                new JsonUserPrefsStorage("dummy"));

        restoreBackupCommand = new RestoreBackupCommand();
        restoreBackupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_restoreBackup_successful() throws Exception {
        storageManager.saveAddressBook(getTypicalAddressBook(), backupFilePath);
        assertTrue(model.getFilteredPersonList().size() == 0);
        ReadOnlyAddressBook expectedAddressBook = getTypicalAddressBook();
        CommandResult result = restoreBackupCommand.execute();
        assertEquals(result.feedbackToUser, restoreBackupCommand.MESSAGE_SUCCESS);
        ReadOnlyAddressBook retrieved = model.getAddressBook();
        assertEquals(expectedAddressBook, retrieved);
    }

    @Test
    public void execute_restoreBackup_withoutBackupFile() throws Exception {
        CommandResult result = restoreBackupCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BackupFilePresentEvent);
        assertEquals(result.feedbackToUser, restoreBackupCommand.MESSAGE_NO_BACKUP_FILE);
    }

}
