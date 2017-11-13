//@@author yuheng222

package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_THEME_DISPLAYED_INDEX;
import static seedu.address.logic.commands.SelectThemeCommand.MESSAGE_SWITCH_THEME_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_THEME;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectThemeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class SelectThemeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void selectTheme() {
        /* Case: select the first theme in the themes list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectThemeCommand.COMMAND_WORD + " " + INDEX_FIRST_THEME.getOneBased() + "   ";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);


        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last theme in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getThemesList().size() + 1;
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_THEME_DISPLAYED_INDEX);

        /* Case: themes list, select index within bounds of address book and themes list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getThemesList().size();
        command = SelectThemeCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, getModel());

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_SWITCH_THEME_SUCCESS} with the number of themes in the theme list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_SWITCH_THEME_SUCCESS, expectedModel.getThemesList().get(INDEX_FIRST_THEME.getOneBased() - 1));

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
