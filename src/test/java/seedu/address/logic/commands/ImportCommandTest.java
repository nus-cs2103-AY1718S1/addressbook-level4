package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_IMPORT_FILE_ERROR;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_INVALID_XML_FORMAT_ERROR;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author Choony93
public class ImportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlUtilTest/");
    private static final String TEST_VALID_BOOK = "validAddressBook.xml";
    private static final String TEST_INVALID_BOOK = "empty.xml";

    private static final String PATH_CURRENT_DIR = (System.getProperty("user.dir"))
        .replaceAll("/", File.separator);
    private static final String PATH_VALID_ABSOLUTE = PATH_CURRENT_DIR
        + TEST_DATA_FOLDER + TEST_VALID_BOOK;
    private static final String PATH_VALID_RELATIVE = TEST_DATA_FOLDER + TEST_VALID_BOOK;
    private static final String PATH_INVALID_MISSING = TEST_DATA_FOLDER;
    private static final String PATH_INVALID_FORMAT = TEST_DATA_FOLDER + TEST_INVALID_BOOK;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Executes the given valid paths, confirms that <br>
     * - the result message verifies path is valid. <br>
     */
    @Test
    public void execute_validAddressbook_import() {

        /*
        Note:
        Absolute path testing will temporarily be disabled due to live Travis pathing restrictions.
        Test passes on appveyor only.
        */
        /*
        String expectedFirstMessage = String.format(MESSAGE_IMPORT_SUCCESS, PATH_VALID_ABSOLUTE);
        ImportCommand importFirstCommand = new ImportCommand(PATH_VALID_ABSOLUTE);
        assertCommandSuccess(importFirstCommand, expectedFirstMessage);
         */

        String expectedSecondMessage = String.format(MESSAGE_IMPORT_SUCCESS, PATH_VALID_RELATIVE);
        ImportCommand importSecondCommand = new ImportCommand(PATH_VALID_RELATIVE);
        assertCommandSuccess(importSecondCommand, expectedSecondMessage);
    }

    @Test
    public void execute_invalidAddressbook_import() {
        String expectedFirstMessage = String.format(MESSAGE_INVALID_IMPORT_FILE_ERROR, PATH_INVALID_MISSING);
        ImportCommand importFirstCommand = new ImportCommand(PATH_INVALID_MISSING);
        assertCommandFailure(importFirstCommand, model, expectedFirstMessage);

        String expectedSecondMessage = String.format(MESSAGE_INVALID_XML_FORMAT_ERROR, PATH_INVALID_FORMAT);
        ImportCommand importSecondCommand = new ImportCommand(PATH_INVALID_FORMAT);
        assertCommandFailure(importSecondCommand, model, expectedSecondMessage);
    }

    @Test
    public void equals() {
        ImportCommand importFirstCommand = new ImportCommand(PATH_VALID_ABSOLUTE);
        ImportCommand importSecondCommand = new ImportCommand(PATH_VALID_RELATIVE);

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
//@@author
