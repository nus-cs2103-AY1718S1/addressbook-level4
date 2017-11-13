package systemtests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ThemeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

//@@author cctdaniel
public class ThemeCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void theme() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        /* Case: change theme to dark theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.DARK_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_SUCCESS_MESSAGE + ThemeCommand.DARK_THEME + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change theme to dark even when already in dark theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.DARK_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_FAILURE_MESSAGE;
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change theme to light theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.LIGHT_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_SUCCESS_MESSAGE + ThemeCommand.LIGHT_THEME + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change theme to light even when already in light theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.LIGHT_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_FAILURE_MESSAGE;
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change to other themes that are undefined */
        command = ThemeCommand.COMMAND_WORD + " " + "blue";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to undo theme command */
        // ThemeCommand is not undoable and hence should display an error message
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to redo theme command */
        // ThemeCommand is not undoable and hence should display an error message
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

    }

    /**
     * Executes {@code command} and verifies that the result equals to {@code expectedResultMessage}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model model) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, model);
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
    }

}
