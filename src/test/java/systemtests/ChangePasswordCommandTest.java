package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.ChangePasswordCommand;
import seedu.address.model.Model;

// @@author derickjw

public class ChangePasswordCommandTest extends AddressBookSystemTest {

    @Test
    public void changePassword() {
        /* Case: Change password of Default Account
         * -> Password set
         */
        String command = ChangePasswordCommand.COMMAND_WORD + " " + "admin " + "admin " + "password";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);
    }

    /**
     * Executes {@code ChangePasswordCommand} and verifies that the command box displays an empty string,
     * the result display box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of
     * people in the filtered list, and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {

        executeCommand("createDefaultAcc");
        String expectedResultMessage = "Password changed successfully!";

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
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel) {

        String expectedResultMessage = "Invalid Credentials!";

        executeCommand("changepw wrongUser wrongPw failureTest");
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
