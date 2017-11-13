package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ImportCommand.MESSAGE_FILE_INVALID;
import static seedu.address.logic.commands.SortCommand.MESSAGE_NO_PERSON_TO_SORT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SUCCESS;
import static seedu.address.logic.commands.SortCommand.SORT_EMAIL;
import static seedu.address.logic.commands.SortCommand.SORT_NAME;

import java.io.IOException;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ImportAnalysis;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.RecycleBin;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
import seedu.address.storage.AddressBookData;


public class SortCommandSystemTest extends AddressBookSystemTest {

    //@@author stan789
    @Test
    public void sort() {
        Model expectedModel = getModel();

        /* Case: import to existing address book and sort -> sorted  */
        executeCommand(ImportCommand.COMMAND_WORD + " src/test/data/VCardFileTest/contacts.vcf");
        String command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        ImportAnalysis importAnalysis = new ImportAnalysis();
        try {
            expectedModel.importFile(Paths.get("src/test/data/VCardFileTest/contacts.vcf"), importAnalysis);
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        } catch (IOException e) {
            assertCommandFailure(command, MESSAGE_FILE_INVALID, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by name -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_NAME;
        try {
            expectedModel.executeSort(SORT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: sort the list by email -> sorted */
        command = SortCommand.COMMAND_WORD + " " + SORT_EMAIL;
        try {
            expectedModel.executeSort(SORT_EMAIL);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_PERSON_TO_SORT, expectedModel);
        }
        assertCommandSuccess(command, expectedModel);

        /* Case: invalid arguments -> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: invalid arguments (no sort type)-> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), expectedModel);

        /* Case: sort from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        expectedModel.resetData(new AddressBookData(new AddressBook(), new RecycleBin()));
        assertCommandFailure(SortCommand.COMMAND_WORD + " " + SORT_NAME,
                MESSAGE_NO_PERSON_TO_SORT, expectedModel);

    }

    //@@author stan789-reused
    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code MESSAGE_SUCCESS},
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", MESSAGE_SUCCESS, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    //@@author stan789-reused
    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
