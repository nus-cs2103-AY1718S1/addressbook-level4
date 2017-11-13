package systemtests;

import org.junit.Test;
import seedu.address.logic.commands.CreateDefaultAccountCommand;
import seedu.address.model.Model;

// @@author derickjw

public class CreateDefaultAccountCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void createDefaultAccount() {
        /* Case : No default account exists
         * -> Default account created successfully
         */
        String command = CreateDefaultAccountCommand.COMMAND_WORD;
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);

        /* Case : Default account already exists
         * -> Default account failed to be created
         */

        expectedModel = getModel();
        assertCommandFailure(command,expectedModel);

    }

    /**
     * Executes the {@code command} and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing
     * {@code command}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedMessage = "Default account created successfully!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes the {@code CreateDefaultAccountCommand} and verifies that the command box displays
     * an empty string, the result display box displays the success message of executing
     * {@code CreateDefaultAccountCommand}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, Model expectedModel) {
        String expectedMessage = "Account already exists!";

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }
}
