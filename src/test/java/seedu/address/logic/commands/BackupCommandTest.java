package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.XmlAddressBookStorage;

public class BackupCommandTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private BackupCommand backupCommand;
    private XmlAddressBookStorage addressBookStorage;

    @Before
    public void setUp() {
        try {
            backupCommand = new BackupCommand();
            addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
            addressBookStorage.saveAddressBook(getTypicalAddressBook());
            JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));

            backupCommand.setData(null, null, null, new StorageManager(addressBookStorage, userPrefsStorage));
        } catch (IOException ioe) {
            fail(ioe.getMessage());
        }
    }

    private String getTempFilePath(String filename) {
        return testFolder.getRoot().getPath() + filename;
    }

    @Test
    public void execute() throws Exception {
        CommandResult result = backupCommand.execute();
        assertEquals(BackupCommand.MESSAGE_SUCCESS, result.feedbackToUser);

        byte[] original = Files.readAllBytes(Paths.get(addressBookStorage.getAddressBookFilePath()));
        byte[] backup = Files.readAllBytes(Paths.get(addressBookStorage.getBackupAddressBookFilePath()));
        assertTrue(Arrays.equals(original, backup));
    }
}
