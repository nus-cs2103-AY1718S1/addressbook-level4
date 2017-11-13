//@@author yuheng222

package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;


public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        Model model = getModel();

        /* Case: sort -> list will be sorted alphabetically by name */
        Model modelBeforeSort = getModel();
        String command = SortCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        model.sort();
        assertCommandSuccess(command, expectedResultMessage, model);


        /* Case: undo the sorting of list -> list will revert back to the state before sort */
        Model modelBeforeUndo = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeSort);

        /* Case: redo the sorting of list -> list will be sorted alphabetically by name */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeUndo);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
