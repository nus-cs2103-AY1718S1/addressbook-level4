package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


//@@author icehawker

/**
 * Contains integration tests (interaction with the Model) for {@code BackupCommand}.
 */
public class BackupCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests may require making new folders/files, deleteFolder to ensure clean up after testing.
     */
    private static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f: files) {
                if (f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    @Test
    public void equals() {
        // create two test directories
        File dir = new File("data/testBackup");
        dir.mkdir();
        File dir2 = new File("data/testBackup2");
        dir2.mkdir();
        BackupCommand locationOne = new BackupCommand("data/testBackup/addressbook.xml");
        BackupCommand locationTwo = new BackupCommand("data/testBackup2/addressbook.xml");

        // same values -> returns true
        BackupCommand locationOneCopy = new BackupCommand("data/testBackup/addressbook.xml");
        assertTrue(locationOneCopy.getLocation().equals(locationOne.getLocation()));

        // same values, different objects -> returns true (does not discriminate)
        assertTrue(locationOneCopy.equals(locationOne));

        // different locations -> returns false
        assertFalse(locationOne.getLocation().equals(locationTwo.getLocation()));

        // different locations, different objects -> returns false
        assertFalse(locationOne.equals(locationTwo));

        // different types -> returns false
        assertFalse(locationOne.getLocation().equals(1));

        // null -> returns false
        assertFalse(locationOne.getLocation().equals(null));

        // clean up
        deleteFolder(dir);
        deleteFolder(dir2);
    }
    /* Works for Gradle allTests locally, fails in TravisCI
    @Test
    public void execute_backup_success() throws Exception {
        File dir = new File("data/testBackup");
        dir.mkdir();
        BackupCommand locationOne = new BackupCommand("data/testBackup/addressbook.xml");
        BackupCommand backupCommand = prepareCommand("data/testBackup/addressbook.xml");

        String expectedMessage = String.format(BackupCommand.BACKUP_SUCCESS_MESSAGE, locationOne.getLocation());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(backupCommand, model, expectedMessage, expectedModel);

        // clean up
        deleteFolder(dir);
    }
    */
    // No CommandExceptions to test: IO Exception always thrown before Command Exception within BackupCommand
    @Test
    public void execute_invalidFilePath_throwsIoException() {
        // directory testBackup does not exist
        String invalidAddress = "v:\\Gibberish Folder\\Gibberish Name";
        BackupCommand backupCommand = prepareCommand(invalidAddress);

        // Note: expected message is exclusively returned as a result of caught IOException in BackupCommand
        String expectedMessage = String.format(BackupCommand.BACKUP_FAILURE_MESSAGE, invalidAddress);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(backupCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        // File addressbook.xml already exists, cannot overwrite
        String validAddress = "data/testBackup/addressbook.xml";
        BackupCommand locationOne = new BackupCommand("data/testBackup/addressbook.xml");
        BackupCommand backupCommand = prepareCommand(validAddress);

        // Note: expected message is exclusively returned as a result of caught IOException in BackupCommand
        String expectedMessage = String.format(BackupCommand.BACKUP_FAILURE_MESSAGE, validAddress);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(backupCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private BackupCommand prepareCommand(String address) {
        UserPrefs prefs = new UserPrefs();
        BackupCommand backupCommand = new BackupCommand(address);
        backupCommand.setData(model, prefs, new CommandHistory(), new UndoRedoStack());
        return backupCommand;
    }
}
