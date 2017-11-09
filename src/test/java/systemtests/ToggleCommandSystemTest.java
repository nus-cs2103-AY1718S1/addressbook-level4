package systemtests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.ToggleCommand;

//@@author seankwekjk
public class ToggleCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void toggle() throws Exception {
        /* Case: toggles to social media*/
        String command = ToggleCommand.COMMAND_WORD;
        String result = ToggleCommand.TOGGLE_SUCCESS + "Display Social Media";
        assertCommandSuccess(command, result);

        /* Case: toggles to maps*/
        command = ToggleCommand.COMMAND_WORD;
        result = ToggleCommand.TOGGLE_SUCCESS + "Display Address";
        assertCommandSuccess(command, result);
    }

    /**
     * Executes @code command
     * 1. Asserts that @code remark is equal to the updated remark @code toCheck
     * 2. Asserts that @code expectedMessage is the expected type of message result
     */
    private void assertCommandSuccess(String command, String expectedMessage) {
        executeCommand(command);
        assertEquals(expectedMessage, getResultDisplay().getText());
    }
}
