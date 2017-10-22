package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

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

public class RestoreBackupCommandTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model;
    private UserPrefs userPrefs;
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
        storageManager.saveAddressBook(getTypicalAddressBook(), backupFilePath);

        restoreBackupCommand = new RestoreBackupCommand();
        restoreBackupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_restoreBackup_successful() throws Exception {
        assertTrue(model.getFilteredPersonList().size() == 0);
        ReadOnlyAddressBook expectedAddressBook = getTypicalAddressBook();
        restoreBackupCommand.execute();
        ReadOnlyAddressBook retrieved = model.getAddressBook();
        assertEquals(expectedAddressBook, retrieved);
    }

}
