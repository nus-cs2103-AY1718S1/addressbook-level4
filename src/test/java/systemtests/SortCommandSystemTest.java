package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_EMAIL;
import static seedu.address.logic.commands.SortCommand.ARGUMENT_NAME;
import static seedu.address.logic.commands.SortCommand.MESSAGE_NO_CONTACTS_TO_SORT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_SORT_SUCCESS;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.Model;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;

//@@author LimeFallacie
public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        String command;
        Model expectedModel = getModel();

        /* Case: sort the list by name -> sorted */
        command = SortCommand.COMMAND_WORD + " " + ARGUMENT_NAME;
        try {
            expectedModel.sort(ARGUMENT_NAME);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_CONTACTS_TO_SORT);
        } catch (DuplicatePersonException dpe) {
            assertCommandFailure(command, MESSAGE_USAGE);
        }
        assertCommandSuccess(command, expectedModel, String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_NAME));

        /* Case: sort the list by email -> sorted */
        command = SortCommand.COMMAND_WORD + " " + ARGUMENT_EMAIL;
        try {
            expectedModel.sort(ARGUMENT_EMAIL);
        } catch (EmptyAddressBookException e) {
            assertCommandFailure(command, MESSAGE_NO_CONTACTS_TO_SORT);
        } catch (DuplicatePersonException dpe) {
            assertCommandFailure(command, MESSAGE_USAGE);
        }
        assertCommandSuccess(command, expectedModel, String.format(MESSAGE_SORT_SUCCESS, ARGUMENT_EMAIL));

        /* Case: invalid arguments -> rejected */
        assertCommandFailure(SortCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        /* Case: sort from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assertCommandFailure(SortCommand.COMMAND_WORD + " " + ARGUMENT_NAME,
                MESSAGE_NO_CONTACTS_TO_SORT);

    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     *
     * @see DeleteCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
