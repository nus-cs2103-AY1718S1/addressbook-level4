package systemtests;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.MergeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

public class MergeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void merge_success() throws Exception {
        Model expectedModel = getModel();

        /* Case: Merge a new file into the default address book data -> merged **/
        final String TEST_NEW_FILE_PATH = "./src/test/data/XmlAddressBookStorageTest/TestNewFile.xml";
        String command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);

        /* Case: Undo the previous merge -> address book data back to previous state **/
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: Merge the same file twice into the default address book -> merged **/
        command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);

        command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);

        /* Case: Merge the new file into an empty address book -> merged **/
        command = ClearCommand.COMMAND_WORD;
        expectedResultMessage = ClearCommand.MESSAGE_SUCCESS;
        expectedModel.resetData(new AddressBook());
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        command = MergeCommand.COMMAND_WORD + " " + TEST_NEW_FILE_PATH;
        assertCommandSuccess(command, TEST_NEW_FILE_PATH);
    }

    @Test
    public void merge_failure() throws Exception {
        String command = MergeCommand.COMMAND_WORD + " " + "./empty/file/path/file.xml";
        assertCommandFailure(command, MergeCommand.MESSAGE_FILE_NOT_FOUND);

        command = MergeCommand.COMMAND_WORD + " " + "./src/test/data/XmlAddressBookStorageTest/DataConversionError.xml";
        assertCommandFailure(command, MergeCommand.MESSAGE_DATA_CONVERSION_ERROR);
    }

    /**
     * Executes the {@code MergeCommand} that adds {@code toAdd} to the model and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing {@code MergeCommand} with the
     * details from {@code newFilePath}, and the model related components equal to the current model added with {@code newFilePath}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String newFilePath) {
        Model expectedModel = getModel();
        String expectedResultMessage = MergeCommand.MESSAGE_SUCCESS;

        File newFile = new File(newFilePath);
        XmlSerializableAddressBook xmlSerializableAddressBook = new XmlSerializableAddressBook();
        ObservableList<ReadOnlyPerson> newPersons;
        try {
            xmlSerializableAddressBook = XmlFileStorage.loadDataFromSaveFile(newFile);
        } catch (FileNotFoundException fnfe) {
            expectedResultMessage = MergeCommand.MESSAGE_FILE_NOT_FOUND;
        } catch (DataConversionException dce) {
            expectedResultMessage = MergeCommand.MESSAGE_DATA_CONVERSION_ERROR;
        }
        newPersons = xmlSerializableAddressBook.getPersonList();

        for (ReadOnlyPerson rop : newPersons) {
            try {
                expectedModel.addPerson(rop);
            } catch (DuplicatePersonException dpe) {
                continue; // simulates merge command to ignore duplicated person
            }
        }

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ArrayList<ReadOnlyPerson>)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     *
     * @see MergeCommandSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
