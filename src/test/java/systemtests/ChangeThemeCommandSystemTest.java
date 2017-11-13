package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.ChangeThemeCommand;
import seedu.address.model.Model;

//@@author moomeowroar
public class ChangeThemeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTheme() {
        /* Case: Change to light theme */
        String command = ChangeThemeCommand.COMMAND_WORD + " " + "light";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case: Change to dark theme */
        command = ChangeThemeCommand.COMMAND_WORD + " " + "dark";
        expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case: Change to youtube theme */
        command = ChangeThemeCommand.COMMAND_WORD + " " + "youtube";
        expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case: Entering wrong theme name
         * -> Rejected
         */
        command = ChangeThemeCommand.COMMAND_WORD + " " + "nosuchtheme";
        assertCommandFailure(command, ChangeThemeCommand.MESSAGE_NO_THEME + "\n" + ChangeThemeCommand.MESSAGE_USAGE);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = ChangeThemeCommand.MESSAGE_CHANGE_SUCCESS;

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
        assertStatusBarUnchanged();
    }
}
