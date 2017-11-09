package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_IMPORT_FILE_ERROR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCommandTest {

    private static final String PATH_ABSOLUTE = System.getProperty("user.dir") + "/data/addressbook.xml";
    private static final String PATH_RELATIVE = "data/addressbook.xml";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Executes the given valid paths, confirms that <br>
     * - the result message verifies path is valid. <br>
     */
    @Test
    public void execute_validAddressbook_import() {
        String expectedFirstMessage = String.format(MESSAGE_IMPORT_SUCCESS, PATH_ABSOLUTE);
        ImportCommand importFirstCommand = new ImportCommand(PATH_ABSOLUTE);
        assertCommandSuccess(importFirstCommand, expectedFirstMessage);

        String expectedSecondMessage = String.format(MESSAGE_IMPORT_SUCCESS, PATH_RELATIVE);
        ImportCommand importSecondCommand = new ImportCommand(PATH_RELATIVE);
        assertCommandSuccess(importSecondCommand, expectedSecondMessage);
    }

    @Test
    public void execute_invalidAddressbook_import() {
        String invalidPath = "test";
        String expectedMessage = String.format(MESSAGE_INVALID_IMPORT_FILE_ERROR, invalidPath);
        ImportCommand command1 = new ImportCommand(invalidPath);
        assertCommandFailure(command1, model, expectedMessage);
    }

    @Test
    public void equals() {
        ImportCommand importFirstCommand = new ImportCommand(PATH_ABSOLUTE);
        ImportCommand importSecondCommand = new ImportCommand(PATH_RELATIVE);

        // same object -> returns true
        assertTrue(importFirstCommand.equals(importFirstCommand));

        // different types -> returns false
        assertFalse(importFirstCommand.equals(1));

        // null -> returns false
        assertFalse(importFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(importFirstCommand.equals(importSecondCommand));
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     */
    public static void assertCommandSuccess(Command command, String expectedMessage) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }
}
