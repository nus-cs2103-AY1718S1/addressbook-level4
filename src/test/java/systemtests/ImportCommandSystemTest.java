package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.AutoComplete.autoComplete;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.HOON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.PersonBuilder;

//@@author HanYaodong
public class ImportCommandSystemTest extends AddressBookSystemTest {

    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/ImportCommandSystemTest/");

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void importCommand() throws Exception {
        Model expectedModel = getModel();
        expectedModel.getUserCreds().validateCurrentSession(); // validate user
        /* ----------------- Performing import operation while an unfiltered list is being shown -------------------- */

        /* Case: import one unique person into address book -> imported */
        String command = getCommandWord("Amy.xml");
        expectedModel.addPerson(AMY);
        assertCommandSuccess(command, expectedModel, 1);

        /* Case: import multiple unique persons into address book -> imported */
        Model modelBeforeAddingPersons = getModel();

        command = getCommandWord("Hoon_Bob.xml");
        expectedModel.addPerson(HOON);
        expectedModel.addPerson(BOB);
        assertCommandSuccess(command, expectedModel, 2);

        /* Case: undo import -> persons deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeAddingPersons, expectedMessage);

        /* Case: redo import -> persons imported again */
        command = RedoCommand.COMMAND_WORD;
        expectedMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedMessage);

        /* Case: import multiple persons containing the same person in address book
         * -> imported with duplicated persons ignored
         */
        ReadOnlyPerson james = new PersonBuilder().withName("James Turner").withPhone("66666666")
                .withEmail("james@example.com").withAddress("Sydney").withBirthday("2000/01/01")
                .withFacebook("30").withTags("friends").build();
        expectedModel.addPerson(james);
        command = getCommandWord("DuplicatedPersonInAddressBook.xml");
        expectedMessage = ImportCommand.MESSAGE_DUPLICATED_PERSON_IN_ADDRESS_BOOK_WARNING
                + String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, 2);
        assertCommandSuccess(command, expectedModel, expectedMessage);

        /* Case: import to an empty address book -> imported */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel = getModel();
        assert expectedModel.getAddressBook().getPersonList().size() == 0;

        command = getCommandWord("Amy.xml");
        expectedModel.addPerson(AMY);
        assertCommandSuccess(command, expectedModel, 1);

        /* Case: missing arguments -> rejected */
        command = ImportCommand.COMMAND_WORD;
        expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedMessage);

        /* Case: import file containing duplicated persons -> rejected */
        String filePath = addToTestDataPathIfNotNull("DuplicatedPersonsExportFile.xml");
        command = ImportCommand.COMMAND_WORD + " " + filePath;
        expectedMessage = String.format(ImportCommand.MESSAGE_DUPLICATED_PERSON_IN_FILE, filePath);
        assertCommandFailure(command, expectedMessage);

        /* Case: import missing file -> rejected */
        filePath = addToTestDataPathIfNotNull("MissingFile.xml");
        command = ImportCommand.COMMAND_WORD + " " + filePath;
        expectedMessage = String.format(ImportCommand.MESSAGE_MISSING_FILE, filePath);
        assertCommandFailure(command, expectedMessage);

        /* Case: import file not in xml format -> rejected */
        filePath = addToTestDataPathIfNotNull("NotXmlFormatExportFile.xml");
        command = ImportCommand.COMMAND_WORD + " " + filePath;
        expectedMessage = String.format(ImportCommand.MESSAGE_INVALID_XML_FILE, filePath);
        assertCommandFailure(command, expectedMessage);

        /* Case: mix case command word -> rejected */
        command = "ExPort SomeFile.xml";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * @see ImportCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, int numberOfPersonsImported) {
        expectedModel.getUserCreds().validateCurrentSession(); // validatae user
        String expectedResultMessage = String.format(ImportCommand.MESSAGE_IMPORT_SUCCESS, numberOfPersonsImported);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the status bar's sync status changes.<br>
     * 5. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        expectedModel.getUserCreds().validateCurrentSession(); // validate user
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();
        executeCommand(command);
        assertApplicationDisplaysExpected(
            autoComplete(command, expectedModel.getFilteredPersonList()), expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

    private String getCommandWord(String filePath) {
        return ImportCommand.COMMAND_WORD + " " + addToTestDataPathIfNotNull(filePath);
    }

    /**
     * Add {@code prefsFileInTestDataFolder} to {@code testFolder}.
     */
    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
               ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
               : null;
    }

}
