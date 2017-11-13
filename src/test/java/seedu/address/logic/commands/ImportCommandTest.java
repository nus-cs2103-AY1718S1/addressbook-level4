package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_IMPORT_SUCCESS;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.XmlAddressBookStorage;

//@@author k-l-a
public class ImportCommandTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("src/test/data/ImportExportCommandTest/");
    private static final String EMPTY_FILE_PATH = TEST_DATA_FOLDER + "empty.xml";
    private static final String MISSING_FILE_PATH = TEST_DATA_FOLDER + "missing.xml";
    private static final String ILLEGAL_ADDRESSBOOK_PATH = TEST_DATA_FOLDER + "illegalAddressBook.xml";
    private static final String VALID_ADDRESSBOOK_PATH = TEST_DATA_FOLDER + "validAddressBook.xml";

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String validFilePath = "data/backup.xml";
        final ImportCommand typicalCommand = new ImportCommand(validFilePath);

        //same object -> true
        assertTrue(typicalCommand.equals(typicalCommand));

        //same value -> true
        assertTrue(typicalCommand.equals(new ImportCommand(validFilePath)));

        //null -> false
        assertFalse(typicalCommand.equals(null));

        //different type -> false
        assertFalse(typicalCommand.equals(new ClearCommand()));

        //different value -> false
        assertFalse(typicalCommand.equals(new ImportCommand("InvalidPath")));

    }

    @Test
    public void execute_missingFilePath_failure() throws Exception {
        ImportCommand command = prepareCommand(MISSING_FILE_PATH);
        String expectedMessage = String.format(ImportCommand.MESSAGE_FILE_NOT_FOUND, MISSING_FILE_PATH);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_emptyFilePath_failure() throws Exception {
        ImportCommand command = prepareCommand(EMPTY_FILE_PATH);
        String expectedMessage = ImportCommand.MESSAGE_FILE_UNKNOWN;
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_illegalFilePath_failure() {
        ImportCommand command = prepareCommand(ILLEGAL_ADDRESSBOOK_PATH);
        String expectedMessage = ImportCommand.MESSAGE_ILLEGAL_VALUE;
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_validFilePath_success() throws Exception {
        ImportCommand command = prepareCommand(VALID_ADDRESSBOOK_PATH);
        String expectedMessage = String.format(MESSAGE_IMPORT_SUCCESS, VALID_ADDRESSBOOK_PATH);
        Model expectedModel = new ModelManager(new XmlAddressBookStorage(VALID_ADDRESSBOOK_PATH)
                .readAddressBook().get(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    /**
     * Prepares an ImportCommand for testing based on the given filepath.
     */
    public ImportCommand prepareCommand(String filepath) {
        ImportCommand importCommand = new ImportCommand(filepath);
        importCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return importCommand;
    }
}
