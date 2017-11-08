package seedu.address.logic.commands.imports;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;
import seedu.address.testutil.TypicalPersons;

//@@author low5545
/**
 * Contains integration tests (interaction with the Model and Storage) and unit tests for {@code ImportXmlCommand}.
 */
public class ImportXmlCommandTest {
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
    public void executeUndoableCommand_validFilePath_success() throws Exception {
        String filePath = getTempFilePath("validImportXmlA.xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, filePath);

        Model modelToAdd = new ModelManager();

        // duplicates
        modelToAdd.addPerson(TypicalPersons.FIONA);
        modelToAdd.addPerson(TypicalPersons.GEORGE);
        // non-duplicates
        modelToAdd.addPerson(TypicalPersons.HOON);
        modelToAdd.addPerson(TypicalPersons.IDA);

        ReadOnlyAddressBook addressBookToAdd = modelToAdd.getAddressBook();
        storage.saveAddressBook(addressBookToAdd, filePath);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addData(addressBookToAdd);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeUndoableCommand_fileNotFound_throwsCommandException() {
        String filePath = getTempFilePath("invalidImportXmlB.xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_FILE_NOT_FOUND;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_invalidFileExtension_throwsCommandException() {
        String filePath = getTempFilePath("invalidImportXmlC.notxml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_NOT_XML_FILE;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_invalidName_throwsCommandException() {
        String filePath = getTempFilePath("invalidImportXmlD*.xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_INVALID_NAME;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_missingFileName_throwsCommandException() {
        String filePath = getTempFilePath(".xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_INVALID_NAME;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_invalidNameSeparator_throwsCommandException() {
        String filePath = getTempFilePath("folder\\folder/invalidImportXmlE.xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_INVALID_NAME_SEPARATOR;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_consecutiveNameSeparator_throwsCommandException() {
        String filePath = getTempFilePath("folder" + File.separator + File.separator + "invalidImportXmlF.xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_CONSECUTIVE_SEPARATOR;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_consecutiveExtensionSeparator_throwsCommandException() {
        String filePath = getTempFilePath("invalidExportG..xml");
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_CONSECUTIVE_SEPARATOR;

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void executeUndoableCommand_invalidXmlDataFormat_throwsCommandException() throws Exception {
        String filePath = getTempFilePath("invalidExportH.xml");
        File file = new File(filePath);
        ImportXmlCommand command = prepareCommand(filePath);
        String expectedMessage = ImportCommand.MESSAGE_INVALID_XML_DATA_FORMAT;

        file.createNewFile(); // empty XML file
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void equals() throws Exception {
        ImportXmlCommand importXmlAddressBookCommand = new ImportXmlCommand(getTempFilePath(
                "importXmlAddressbook.xml"));
        ImportXmlCommand importXmlContactBookCommand = new ImportXmlCommand(getTempFilePath(
                "importXmlContactbook.xml"));

        // same object -> returns true
        assertTrue(importXmlAddressBookCommand.equals(importXmlAddressBookCommand));

        // same values -> returns true
        ImportXmlCommand importXmlAddressBookCommandCopy = new ImportXmlCommand(getTempFilePath(
                "importXmlAddressbook.xml"));
        assertTrue(importXmlAddressBookCommand.equals(importXmlAddressBookCommandCopy));

        // different types -> returns false
        assertFalse(importXmlAddressBookCommand.equals(1));

        // null -> returns false
        assertFalse(importXmlAddressBookCommand.equals(null));

        // different person -> returns false
        assertFalse(importXmlAddressBookCommand.equals(importXmlContactBookCommand));
    }

    /**
     * Helper method to provide temporary file paths
     */
    private String getTempFilePath(String fileName) {
        return testFolder.getRoot().getPath() + File.separator + fileName;
    }

    /**
     * Returns a {@code ImportXmlCommand} with the parameter {@code filePath}.
     */
    private ImportXmlCommand prepareCommand(String filePath) {
        ImportXmlCommand importXmlCommand = new ImportXmlCommand(filePath);
        importXmlCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        importXmlCommand.setStorage(storage);
        return importXmlCommand;
    }

}
