package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.tasks.SelectTaskCommand.MESSAGE_SELECT_TASK_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.GYM;
import static seedu.address.testutil.TypicalTasks.getTypicalTasks;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.tasks.SelectTaskCommand;
import seedu.address.model.Model;

public class SelectTaskCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void select() {

        /*change the current command mode to task manager*/
        executeCommand(ChangeModeCommand.COMMAND_WORD + " tm");

        /* Case: select the first card in the task list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_TASK);

        /* Case: select the last card in the person list -> selected */
        Index personCount = Index.fromOneBased(getTypicalTasks().size());
        command = SelectTaskCommand.COMMAND_WORD + " " + personCount.getOneBased();
        assertCommandSuccess(command, personCount);

        /* Case: mixed case command word -> selected */
        assertCommandSuccess("SeLeCt 1", INDEX_FIRST_TASK);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the person list -> selected */
        Index middleIndex = Index.fromOneBased(personCount.getOneBased() / 2);
        command = SelectTaskCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredTaskList().size() + 1;
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: filtered person list, select index within bounds of address book but out of bounds of person list
         * -> rejected
         */
        showTasksWithDescription(GYM.getDescription().taskDescription);
        invalidIndex = getModel().getAddressBook().getTaskList().size();
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: filtered person list, select index within bounds of address book and person list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredTaskList().size();
        command = SelectTaskCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " " + 0,

            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " " + -1,
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " 1 abc",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectTaskCommand.MESSAGE_USAGE));

        /* Case: select from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        assertCommandFailure(SelectTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
            MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected person, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedTaskCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
            MESSAGE_SELECT_TASK_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getTaskListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedTaskCardUnchanged();
        } else {
            assertSelectedTaskCardChanged(expectedSelectedCardIndex);
        }

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
        assertSelectedTaskCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
