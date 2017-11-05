package systemtests;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CustomiseCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.font.FontSize;

//@@author cctdaniel
public class CustomiseCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void customise() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        /* Case: change font size to xsmall */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_XSMALL;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_XSMALL + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to small */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_SMALL;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_SMALL + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to normal */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_NORMAL;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_NORMAL + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to large */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_LARGE;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_LARGE + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to xlarge */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_XLARGE;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_XLARGE + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change font size without typing prefix */
        command = CustomiseCommand.COMMAND_WORD + " " + CustomiseCommand.FONT_SIZE_XLARGE;
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to change font size to undefined sizes */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + "xxxlarge";
        expectedResultMessage = FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to undo customise command */
        // CustomiseCommand is not undoable and hence should display an error message
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to redo customise command */
        // CustomiseCommand is not undoable and hence should display an error message
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
