package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.ChangeUsernameCommand;
import seedu.address.model.Model;

// @@author derickjw

public class ChangeUsernameCommandTest extends AddressBookSystemTest {

    @Test
    public void changeUsername() {
        /* Case: Change username of Default Account
         * -> Username set
         */
        String command = ChangeUsernameCommand.COMMAND_WORD + " " + "admin " + "newUser " + "admin";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);
    }

    /**
     * Executes {@code ChangeUsernameCommand} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_PERSONS_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     *
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {

        executeCommand("createDefaultAcc");
        String expectedResultMessage = "Username changed successfully!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
