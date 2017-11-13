package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.AutoComplete.autoComplete;
import static seedu.address.testutil.TestUtil.getLastIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.ExportCommandParser;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;

//@@author HanYaodong
public class ExportCommandSystemTest extends AddressBookSystemTest {

    private static final String EXPECTED_MESSAGE_INVALID_COMMAND =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE);

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();


    @Test
    public void export() throws Exception {
        String testFile = testFolder.getRoot().getPath() + "TestFile.xml";
        Model model = getModel();
        model.getUserCreds().validateCurrentSession(); // validate user

        /* ----------------- Performing export operation while an unfiltered list is being shown -------------------- */

        /* Case: export the first person in the list, command with leading and trailing whitespaces
         * -> first person exported
         */
        Index firstPersonIndex = INDEX_FIRST_PERSON;
        String command  = "    " + getExportCommand(testFile, firstPersonIndex) + "    ";
        String expectedMessage = getExpectedSuccessMessage(model, testFile, firstPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export the last person in the list -> last person exported */
        Index lastPersonIndex = getLastIndex(model);
        assertCommandSuccess(getExportCommand(testFile, lastPersonIndex), model,
            getExpectedSuccessMessage(model, testFile, lastPersonIndex));

        /* Case: export first and second person in the list with index separated by "," -> 2 persons exported */
        command = ExportCommand.COMMAND_WORD + " 1,2 ; " + testFile;
        expectedMessage = getExpectedSuccessMessage(model, testFile,  Index.fromOneBased(1), Index.fromOneBased(2));
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: repeat the previous command with index separated by whitespaces -> 2 persons exported */
        command = ExportCommand.COMMAND_WORD + " 1 2 ; " + testFile;
        expectedMessage = getExpectedSuccessMessage(model, testFile, Index.fromOneBased(1), Index.fromOneBased(2));
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first three persons in the list with index separated by a mix of "," and whitespaces
         * -> 3 persons exported
         */
        command = ExportCommand.COMMAND_WORD + " 1,, 2  3 ; " + testFile;
        expectedMessage = getExpectedSuccessMessage(model, testFile,
            Index.fromOneBased(1), Index.fromOneBased(2), Index.fromOneBased(3));
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person to non-existing file -> exported and create new file */
        String nonExistingFile = testFolder.getRoot().getPath() + "NonExistingFile.xml";
        command = getExportCommand(nonExistingFile, firstPersonIndex);
        expectedMessage = getExpectedSuccessMessage(model, nonExistingFile, firstPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export last person to existed file -> exported to overwrite file */
        command = getExportCommand(nonExistingFile, lastPersonIndex);
        expectedMessage = getExpectedSuccessMessage(model, nonExistingFile, lastPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path without extension -> exported and add .xml extension */
        String noExtensionFile = testFolder.getRoot().getPath() + "NoExtensionFile";
        command = getExportCommand(noExtensionFile, firstPersonIndex);
        expectedMessage = getExpectedSuccessMessage(model, noExtensionFile + ".xml", firstPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path with wrong extension -> exported and add .xml extension */
        String wrongExtensionFile = testFolder.getRoot().getPath() + "WrongExtensionFile.txt";
        command = getExportCommand(wrongExtensionFile, firstPersonIndex);
        expectedMessage = getExpectedSuccessMessage(model, wrongExtensionFile + ".xml", firstPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path with no file name -> exported and add .xml extension */
        String noNameFile = testFolder.getRoot().getPath() + File.separator + ".xml";
        command = getExportCommand(noNameFile, firstPersonIndex);
        expectedMessage = getExpectedSuccessMessage(model, noNameFile + ".xml", firstPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* Case: export first person with file path with extension in upper case -> exported to given file name */
        String upperCaseExtensionFile = testFolder.getRoot().getPath() + "UpperCaseFile.XML";
        command = getExportCommand(upperCaseExtensionFile, firstPersonIndex);
        expectedMessage = getExpectedSuccessMessage(model, upperCaseExtensionFile, firstPersonIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* ------------------ Performing export operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, export within boundary of address book and person list -> exported */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        model = getModel();
        command = getExportCommand(testFile, firstPersonIndex);
        assertTrue(firstPersonIndex.getZeroBased() < model.getFilteredPersonList().size());
        assertCommandSuccess(command, model, getExpectedSuccessMessage(model, testFile, firstPersonIndex));

        /* Case: filtered person list, export within boundary of address book but out of bounds of person list
         * -> rejected
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index invalidIndex = Index.fromZeroBased(getModel().getAddressBook().getPersonList().size());
        assertCommandFailure(getExportCommand(testFile, invalidIndex), MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing export operation while a person card is selected ------------------------ */

        /* Case: export the selected person -> exported and selection remains */
        showAllPersons();
        model = getModel();
        Index selectedIndex = getLastIndex(model);
        selectPerson(selectedIndex);
        command = getExportCommand(testFile, selectedIndex);
        expectedMessage = getExpectedSuccessMessage(model, testFile, selectedIndex);
        assertCommandSuccess(command, model, expectedMessage);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = getExportCommand(testFile, 0);
        assertCommandFailure(command, EXPECTED_MESSAGE_INVALID_COMMAND);

        /* Case: invalid index (-1) -> rejected */
        command = getExportCommand(testFile, -1);
        assertCommandFailure(command, EXPECTED_MESSAGE_INVALID_COMMAND);

        /* Case: invalid index (size + 1)  */
        command = getExportCommand(testFile, model.getFilteredPersonList().size() + 1);
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets in index part) -> rejected */
        command = getExportCommand(testFile, "abc");
        assertCommandFailure(command, EXPECTED_MESSAGE_INVALID_COMMAND);

        /* Case: invalid arguments (indexes separated by invalid char) -> rejected */
        command = getExportCommand(testFile, "1 . 2");
        assertCommandFailure(command, EXPECTED_MESSAGE_INVALID_COMMAND);

        /* Case: missing index list -> rejected */
        command = getExportCommand(testFile, " ");
        assertCommandFailure(command, EXPECTED_MESSAGE_INVALID_COMMAND);

        /* Case: missing semicolon ";" -> rejected */
        command = ExportCommand.COMMAND_WORD + " 1, 2 " + testFile;
        assertCommandFailure(command, EXPECTED_MESSAGE_INVALID_COMMAND);

        /* Case: missing file path -> rejected */
        command = getExportCommand(" ", firstPersonIndex);
        assertCommandFailure(command, String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, ExportCommandParser.MISSING_FILE_PATH + ExportCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("ExpOrt 1;" + testFile, MESSAGE_UNKNOWN_COMMAND);

    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar remains unchanged.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see ExportCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        expectedModel.getUserCreds().validateCurrentSession(); // validate user
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(
            autoComplete(command, expectedModel.getFilteredPersonList()), expectedMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertSelectedCardUnchanged();
        assertStatusBarUnchanged();
    }

    private String getNameListString(Model model, Index... indexes) {
        List<ReadOnlyPerson> filteredList = model.getFilteredPersonList();
        StringBuilder personNameBuilder = new StringBuilder();
        for (Index index : indexes) {
            personNameBuilder.append(filteredList.get(index.getZeroBased()).getName().fullName).append(", ");
        }
        return personNameBuilder.deleteCharAt(personNameBuilder.lastIndexOf(",")).toString();
    }

    private String getExportCommand(String filePath, Index... indexes) {
        return ExportCommand.COMMAND_WORD + " "
            + Arrays.stream(indexes).map(Index::getOneBased).map(Object::toString).collect(Collectors.joining(","))
            + " ; " + filePath;
    }

    private String getExportCommand(String filePath, Integer... oneBasedIndexes) {
        return ExportCommand.COMMAND_WORD + " "
            + Arrays.stream(oneBasedIndexes).map(Object::toString).collect(Collectors.joining(","))
            + " ; " + filePath;
    }

    private String getExportCommand(String filePath, String oneBasedIndexesString) {
        return ExportCommand.COMMAND_WORD + " " + oneBasedIndexesString + " ; " + filePath;
    }

    private String getExpectedSuccessMessage(Model model, String filePath, Index... indexes) {
        return String.format(ExportCommand.MESSAGE_EXPORT_PERSON_SUCCESS, getNameListString(model, indexes), filePath);
    }

}
