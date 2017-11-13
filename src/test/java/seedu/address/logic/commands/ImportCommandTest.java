package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.user.UserCreds;
import seedu.address.model.user.UserPrefs;
import seedu.address.testutil.TypicalPersons;

//@@author HanYaodong
public class ImportCommandTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImportCommandTest/");

    private static final String MESSAGE_DUPLICATED_PERSON_WARNING =
        "Duplicated persons are found in import process. Duplicated information is ignored.\n";
    private static final String MESSAGE_SUCCESS_WITH_DUPLICATED_PERSON_IN_FILE =
         MESSAGE_DUPLICATED_PERSON_WARNING + ImportCommand.MESSAGE_IMPORT_SUCCESS;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());

    //@@author zenghou-unused
    /** Initially used to test for invalid login since the check was done by command. However, this check will be
     * conducted by LogicManager now. As such, all commands will be executed.
     */
    public void execute_invalidUser_failure() throws Exception {
        String userNotLoggedInMessage = "Invalid session! Please log in first! \n"
                + LoginCommand.MESSAGE_USAGE;

        Model userCredsNotValidatedModel = new ModelManager(model.getAddressBook(), new UserPrefs(), new UserCreds());

        String filePath = "SomeFile.xml";
        ImportCommand importCommand = new ImportCommand(filePath);

        importCommand.setData(userCredsNotValidatedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(importCommand, userCredsNotValidatedModel,
                userNotLoggedInMessage);
    }
    //@@author

    @Test
    public void equals() {
        String filePath = "SomeFile.xml";
        ImportCommand importCommand = new ImportCommand(filePath);

        // the same object -> true
        assertTrue(importCommand.equals(importCommand));

        // different object same value -> true
        assertTrue(importCommand.equals(new ImportCommand(filePath)));

        // null -> false
        assertFalse(importCommand.equals(null));

        // different classes -> false
        assertFalse(importCommand.equals(1));

        // different values -> false
        assertFalse(importCommand.equals(new ImportCommand("OtherFile.xml")));
    }

    @Test
    public void constructor_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        new ImportCommand(null).executeUndoableCommand();
    }

    @Test
    public void execute_missingFile_failure() throws Exception {
        String filePath = addToTestDataPathIfNotNull("MissingFile.xml");
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_MISSING_FILE, filePath));
    }

    @Test
    public void execute_notXmlFormat_failure() throws Exception {
        String filePath = addToTestDataPathIfNotNull("NotXmlFormatExportFile.xml");
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_INVALID_XML_FILE, filePath));
    }

    @Test
    public void execute_emptyFile_failure() {
        String filePath = addToTestDataPathIfNotNull("EmptyFile.xml");
        //TODO: empty file should return Optional.empty()?
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_INVALID_XML_FILE, filePath));
    }

    @Test
    public void execute_duplicatedPersonInFile_failure() throws Exception {
        String filePath = addToTestDataPathIfNotNull("DuplicatedPersonsExportFile.xml");
        assertCommandException(new ImportCommand(filePath),
            String.format(ImportCommand.MESSAGE_DUPLICATED_PERSON_IN_FILE, filePath));
    }

    @Test
    public void execute_duplicatedPersonInAddressBook_successWithWarning() throws Exception {
        String filePath = addToTestDataPathIfNotNull("DuplicatedPersonInAddressBook.xml");
        ImportCommand command = prepareCommand(filePath);
        CommandResult result = command.executeUndoableCommand();
        assertEquals(result.feedbackToUser, String.format(MESSAGE_SUCCESS_WITH_DUPLICATED_PERSON_IN_FILE, 1));
    }

    @Test
    public void execute_validFilePathAndFile_success() throws Exception {
        String filePath = addToTestDataPathIfNotNull("ImportTestFile.xml");
        ImportCommand command = prepareCommand(filePath);
        CommandResult result = command.execute();

        // check CommandResult
        assertEquals(result.feedbackToUser, String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, 1));
        // check Model
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());
        expectedModel.getUserCreds().validateCurrentSession(); // validate user
        expectedModel.addPerson(TypicalPersons.HOON);
        assertEquals(command.model, expectedModel);
    }

    /**
     * @return an ImportCommand with given {@code filePath} and typical Model.
     */
    private ImportCommand prepareCommand(String filePath) {
        ImportCommand command = new ImportCommand(filePath);
        // typical address model consists of ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE
        Model typicalAddressBookModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), new UserCreds());
        typicalAddressBookModel.getUserCreds().validateCurrentSession(); // validate user
        command.setData(typicalAddressBookModel, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts if executing the given command throws {@code CommandException} with {@code exceptionMessage}.
     */
    private void assertCommandException(ImportCommand command, String exceptionMessage) {
        model.getUserCreds().validateCurrentSession(); // validate user
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        try {
            command.executeUndoableCommand();
        } catch (CommandException ce) {
            assertEquals(ce.getMessage(), exceptionMessage);
        }
    }

    /**
     * Adds test folder path to {@code prefsFileInTestDataFolder}.
     */
    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
               ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
               : null;
    }
}
