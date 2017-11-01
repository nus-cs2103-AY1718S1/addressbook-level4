package systemtests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.person.SortCommand;
import seedu.address.model.Model;

//@@author Alim95
public class SortCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_SORT_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE);
    private static final String SORT_NAME_WORD = "name";
    private static final String SORT_PHONE_WORD = "phone";
    private static final String SORT_EMAIL_WORD = "email";
    private static final String SORT_ADDRESS_WORD = "address";

    @Test
    public void sort() {
        Model model = getModel();

        /* Case: sort by phone -> list will be sorted numerically by phone number */
        String command = SortCommand.COMMAND_WORD + " " + SORT_PHONE_WORD;
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_PHONE_WORD);
        model.sortList(SORT_PHONE_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by name -> list will be sorted alphabetically by name */
        command = SortCommand.COMMAND_WORD + " " + SORT_NAME_WORD;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_NAME_WORD);
        model.sortList(SORT_NAME_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by email -> list will be sorted alphabetically by email */
        command = SortCommand.COMMAND_WORD + " " + SORT_EMAIL_WORD;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_EMAIL_WORD);
        model.sortList(SORT_EMAIL_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: sort by phone -> list will be sorted alphabetically by address */
        Model modelBeforeSortPhone = getModel();
        command = SortCommand.COMMAND_WORD + " " + SORT_ADDRESS_WORD;
        expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS, SORT_ADDRESS_WORD);
        model.sortList(SORT_ADDRESS_WORD);
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: undo the command sort phone -> list will be sorted alphabetically by email */
        Model modelBeforeUndo = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeSortPhone);

        /* Case: redo sorting the list by address -> list will be sorted alphabetically by address  */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeUndo);

        /* Case: keyword is wrong -> rejected */
        assertCommandFailure("sort home address", MESSAGE_INVALID_SORT_COMMAND_FORMAT);

    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing sort command
     * and the model related components equal to the current model.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
