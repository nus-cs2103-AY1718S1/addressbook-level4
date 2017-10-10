package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
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

public class BackupCommandTest {
    private Model model;
    private UserPrefs userPrefs;
    private StorageManager storageManager;
    private AddressBookStorage addressBookStorage;
    private String filePath;
    private String backupFilePath;
    private BackupCommand backupCommand;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        filePath = testFolder.getRoot().getPath() + "TempAddressBook.xml";
        backupFilePath = testFolder.getRoot().getPath() + "TempAddressBook-backup.xml";
        userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(filePath);
        addressBookStorage = new XmlAddressBookStorage(filePath);

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        storageManager = new StorageManager(addressBookStorage,
                new JsonUserPrefsStorage("dummy"));

        backupCommand = new BackupCommand();
        backupCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_successfulBackup() throws Exception {
        ReadOnlyAddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        backupCommand.execute();
        ReadOnlyAddressBook retrieved = storageManager.readAddressBook(backupFilePath).get();
        assertEquals(expectedAddressBook, new AddressBook(retrieved));
    }
}
