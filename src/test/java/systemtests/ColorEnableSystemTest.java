package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.ColorKeywordCommand;
import seedu.address.model.Model;
//@@author caoliangnus
public class ColorEnableSystemTest extends AddressBookSystemTest {
    @Test
    public void colorEnable() {
        String command;
        String expectedResultMessage;

        /* Case: enable highlighting feature with leading spaces and trailing space
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " enable   ");

        /* Case: enable highlighting feature when already enabled
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " enable   ");

        /* Case: attempt to enable highlighting command keyword that are undefined */
        command = ColorKeywordCommand.COMMAND_WORD + " " + "Enabled";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ColorKeywordCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: disable highlighting feature with leading spaces and trailing space
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " disable   ");

        /* Case: disable highlighting feature when already disabled
         */
        assertCommandSuccess("   " + ColorKeywordCommand.COMMAND_WORD + " disable   ");

        /* Case: attempt to disable highlighting command keyword that are undefined */
        command = ColorKeywordCommand.COMMAND_WORD + " " + "Disabled";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                ColorKeywordCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);


        /* Case: mixed case command word -> rejected */
        assertCommandFailure("EnaBle", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Wrong command word -> rejected */
        assertCommandFailure("enabledisable", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ColorKeywordCommand#MESSAGE_SUCCESS} and the model related components equal to an
     * empty model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command) {
        String text;
        if (command.contains("enable")) {
            text = ColorKeywordCommand.ENABLE_COLOR + ColorKeywordCommand.MESSAGE_SUCCESS;
        } else {
            text = ColorKeywordCommand.DISABLE_COLOR + ColorKeywordCommand.MESSAGE_SUCCESS;
        }

        assertCommandSuccess(command, text, getModel());
        assertStatusBarUnchanged();


    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ColorEnableSystemTest # assertCommandSuccess(String)
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
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
