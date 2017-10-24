package systemtests;

import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX;
import static seedu.address.logic.commands.ChangeModeCommand.MESSAGE_CHANGE_MODE_SUCCESS;
import static seedu.address.logic.commands.tasks.DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS;
import static seedu.address.testutil.TestUtil.getLastTask;
import static seedu.address.testutil.TestUtil.getMidTask;
import static seedu.address.testutil.TestUtil.getTask;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.KEYWORD_MATCHING_FINISH;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.tasks.DeleteTaskCommand;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.TaskNotFoundException;

public class DeleteTaskCommandSystemTest extends AddressBookSystemTest {

    private static final String MESSAGE_INVALID_DELETE_COMMAND_FORMAT =
            String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteTaskCommand.MESSAGE_USAGE);

    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /*Case: change the current command mode to task manager -> success*/
        Model expectedModel = getModel();
        String command = ChangeModeCommand.COMMAND_WORD + " tm";
        String expectedResultMessage = String.format(MESSAGE_CHANGE_MODE_SUCCESS, "taskmanager");
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the first task in the list, command with leading spaces and trailing spaces -> deleted */

        command = "     " + DeleteTaskCommand.COMMAND_WORD + "      "
                + INDEX_FIRST_TASK.getOneBased() + "       ";
        ReadOnlyTask deletedTask = removeTask(expectedModel, INDEX_FIRST_TASK);
        expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: delete the last task in the list -> deleted */
        Model modelBeforeDeletingLast = getModel();
        Index lastTaskIndex = getLastTask(modelBeforeDeletingLast);
        assertCommandSuccess(lastTaskIndex);

        /* Case: undo deleting the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: mixed case command word -> deleted */
        Model modelAfterDeletingLast = getModel();
        ReadOnlyTask removedTask = removeTask(modelAfterDeletingLast, lastTaskIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, removedTask);
        assertCommandSuccess("DelEtE 5", modelAfterDeletingLast, expectedResultMessage);

        /* Case: undo deleting the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last task in the list -> last task deleted again */
        command = RedoCommand.COMMAND_WORD;
        removeTask(modelBeforeDeletingLast, lastTaskIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle task in the list -> deleted */
        Index middleTaskIndex = getMidTask(getModel());
        assertCommandSuccess(middleTaskIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered task list, delete index within bounds of address book and task list -> deleted */
        showTasksWithDescription("Online");
        Index index = INDEX_FIRST_TASK;
        assertTrue(index.getZeroBased() < getModel().getFilteredTaskList().size());
        assertCommandSuccess(index);

        /* Case: filtered task list, delete index within bounds of address book but out of bounds of task list
         * -> rejected
         */
        showTasksWithDescription(KEYWORD_MATCHING_FINISH);
        int invalidIndex = getModel().getAddressBook().getTaskList().size();
        command = DeleteTaskCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a task card is selected ------------------------ */

        /* Case: delete the selected task -> task list panel selects the task before the deleted task */
        showAllTasks();
        expectedModel = getModel();
        Index selectedIndex = getLastTask(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectTask(selectedIndex);
        command = DeleteTaskCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedTask = removeTask(expectedModel, selectedIndex);
        expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected */
        command = DeleteTaskCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getTaskList().size() + 1);
        command = DeleteTaskCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(DeleteTaskCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

    }

    /**
     * Removes the {@code ReadOnlyTask} at the specified {@code index} in {@code model}'s address book.
     * @return the removed task
     */
    private ReadOnlyTask removeTask(Model model, Index index) {
        ReadOnlyTask targetTask = getTask(model, index);
        try {
            model.deleteTask(targetTask);
        } catch (TaskNotFoundException pnfe) {
            throw new AssertionError("targetTask is retrieved from model.");
        }
        return targetTask;
    }

    /**
     * DeleteTasks the task at {@code toDeleteTask} by creating a default
     * {@code DeleteTaskCommand} using {@code toDeleteTask} and
     * performs the same verification as {@code assertCommandSuccess(String, Model, String)}.
     * @see DeleteTaskCommandSystemTest#assertCommandSuccess(String, Model, String)
     */
    private void assertCommandSuccess(Index toDeleteTask) {
        Model expectedModel = getModel();
        ReadOnlyTask deletedTask = removeTask(expectedModel, toDeleteTask);
        String expectedResultMessage = String.format(MESSAGE_DELETE_TASK_SUCCESS, deletedTask);

        assertCommandSuccess(
                DeleteTaskCommand.COMMAND_WORD + " " + toDeleteTask.getOneBased(), expectedModel,
                expectedResultMessage);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card remains unchanged.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the browser url
     * and selected card are expected to update accordingly depending on the card at {@code expectedSelectedCardIndex}.
     * @see DeleteTaskCommandSystemTest#assertCommandSuccess(String, Model, String)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (expectedSelectedCardIndex != null) {
            assertSelectedTaskCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedTaskCardUnchanged();
        }

        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
