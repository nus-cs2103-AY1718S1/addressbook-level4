//@@author edwinghy

package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPath.PATH_IMPORT;
import static seedu.address.testutil.TypicalPath.PATH_IMPORT_2;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    public static final String VALID_PATH = "classmates";
    public static final String INVALID_PATH = "classmate.xml";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_import_failure() throws Exception {
        ImportCommand importCommand = prepareCommand(INVALID_PATH);
        assertCommandFailure(importCommand, model, String.format(importCommand.MESSAGE_INVALID_FILE, INVALID_PATH));
    }

    @Test
    public void execute_import_success() throws Exception {

        String tempFolderPath = folder.getRoot().getPath();

        ExportCommand exportCommand = new ExportCommand("1-3", tempFolderPath + VALID_PATH);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exportCommand.execute();

        ClearCommand clearCommand = new ClearCommand();
        clearCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        clearCommand.executeUndoableCommand();

        ImportCommand importCommand = prepareCommand(tempFolderPath + VALID_PATH + ".xml");
        assertCommandSuccess(importCommand, model, String.format(importCommand.MESSAGE_SUCCESS, "3", "0"), model);


    }

    @Test
    public void equals() {
        final ImportCommand standardCommand = new ImportCommand(PATH_IMPORT);

        // same values -> returns true
        ImportCommand commandWithSameValues = new ImportCommand(PATH_IMPORT);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different range -> returns false
        assertFalse(standardCommand.equals(new ImportCommand(PATH_IMPORT_2)));

    }

    private ImportCommand prepareCommand(String path) {
        ImportCommand importCommand = new ImportCommand(path);
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return importCommand;
    }
}
//@@author
