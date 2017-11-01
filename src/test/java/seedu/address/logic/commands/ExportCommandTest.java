package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.InvalidFilePathException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;

/**
 * Contains integration tests (interaction with the Model and Storage) and unit tests for {@code ExportCommand}.
 */
public class ExportCommandTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Storage storage;
    private Model model;

    @Before
    public void setUp() {
        AddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("addressbook.xml"));
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("preferences.json"));
        storage = new StorageManager(addressBookStorage, userPrefsStorage);
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validFilePath_success() {
        String filePath = getTempFilePath("validExportA.xml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ExportCommand.MESSAGE_EXPORT_SUCCESS, filePath);

        assertCommandSuccess(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidFileExtension_throwsCommandException() {
        String filePath = getTempFilePath("invalidExportB.notxml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = ExportCommand.MESSAGE_NOT_XML_FILE;

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidName_throwsCommandException() {
        String filePath = getTempFilePath("invalidExportC*.xml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = ExportCommand.MESSAGE_INVALID_NAME;

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_invalidNameSeparator_throwsCommandException() {
        String filePath = getTempFilePath("folder\\folder/invalidExportD.xml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = ExportCommand.MESSAGE_INVALID_NAME_SEPARATOR;

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_consecutiveNameSeparator_throwsCommandException() {
        String filePath = getTempFilePath("folder" + File.separator + File.separator + "invalidExportE.xml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = ExportCommand.MESSAGE_CONSECUTIVE_SEPARATOR;

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void execute_consecutiveExtensionSeparator_throwsCommandException() {
        String filePath = getTempFilePath("invalidExportF..xml");
        ExportCommand command = prepareCommand(filePath);
        String expectedMessage = ExportCommand.MESSAGE_CONSECUTIVE_SEPARATOR;

        assertCommandFailure(command, expectedMessage, filePath);
    }

    @Test
    public void equals() throws Exception {
        ExportCommand exportAddressBookCommand = new ExportCommand(getTempFilePath("exportAddressbook.xml"));
        ExportCommand exportContactBookCommand = new ExportCommand(getTempFilePath("exportContactbook.xml"));

        // same object -> returns true
        assertTrue(exportAddressBookCommand.equals(exportAddressBookCommand));

        // same values -> returns true
        ExportCommand exportAddressBookCommandCopy = new ExportCommand(getTempFilePath("exportAddressbook.xml"));
        assertTrue(exportAddressBookCommand.equals(exportAddressBookCommandCopy));

        // different types -> returns false
        assertFalse(exportAddressBookCommand.equals(1));

        // null -> returns false
        assertFalse(exportAddressBookCommand.equals(null));

        // different person -> returns false
        assertFalse(exportAddressBookCommand.equals(exportContactBookCommand));
    }

    /**
     * Helper method to provide temporary file paths
     */
    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + fileName;
    }

    /**
     * Returns a {@code ExportCommand} with the parameter {@code filePath}.
     */
    private ExportCommand prepareCommand(String filePath) {
        ExportCommand exportCommand = new ExportCommand(filePath);
        exportCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        exportCommand.setStorage(storage);
        return exportCommand;
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the address book data stored in {@code model} matches the the data stored at {@code filePath}
     */
    private void assertCommandSuccess(ExportCommand command, String expectedMessage, String filePath) {
        try {
            CommandResult result = command.execute();
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(model.getAddressBook(), new AddressBook(storage.readAddressBook(filePath).get()));
        } catch (CommandException | DataConversionException | IOException | InvalidFilePathException e) {
            throw new AssertionError("Execution of command should not fail.", e);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book data is not saved at {@code filePath}
     */
    public void assertCommandFailure(ExportCommand command, String expectedMessage, String filePath) {
        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertFalse((new File(filePath)).exists());
        }
    }

}
