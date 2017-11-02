package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.TabCommand.MESSAGE_INVALID_TAB_INDEX;
import static seedu.address.logic.commands.TabCommand.MESSAGE_SELECT_TAB_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAB;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.TabCommand;
import seedu.address.model.Model;

//@@author vicisapotato
public class TabCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void tab() {
        /* Case: select the first tab in the parcel list, command with leading spaces and trailing spaces
         * -> tab selected
         */
        String command = "   " + TabCommand.COMMAND_WORD + " " + INDEX_FIRST_TAB.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_TAB);

        /* Case: invalid index (number of tabs + 1) -> rejected */
        int invalidIndex = TabCommand.NUM_TAB + 1;
        assertCommandFailure(TabCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_TAB_INDEX);

        /* Case: select the current selected tab -> tab selected */
        assertCommandSuccess(command, INDEX_FIRST_TAB);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(TabCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TabCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("tAB 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> tab selected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getParcelList().size() == 0;
        assertCommandSuccess(command, INDEX_FIRST_TAB);
    }
    //@@author

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing tab command.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * selected tab will be verified if the current selected tab and the tab at
     * {@code expectedSelectedTabIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedTabIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_TAB_SUCCESS, expectedSelectedTabIndex.getOneBased());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
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
