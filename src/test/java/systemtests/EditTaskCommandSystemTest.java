// @@author ahmadalkaff
package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MEETING;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalTasks.MEETING;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.Appointment;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;

public class EditTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        Model model = getModel();

        /* --------------- Performing edit task operation while an unfiltered list is being shown ------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TASK;
        String command = " " + EditTaskCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + APPOINTMENT_DESC_MEETING + "  " + DATE_DESC_MEETING + "  " + START_TIME_DESC_MEETING + " ";
        Task editedTask = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MEETING).withDate(VALID_DATE_MEETING)
                .withStartTime(VALID_START_TIME_MEETING).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: undo editing the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo editing the last task in the list -> last task edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        model.updateTask(
                getModel().getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()), editedTask);
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: edit a task with new values same as existing values -> edited */
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + APPOINTMENT_DESC_MEETING
                + DATE_DESC_MEETING + START_TIME_DESC_MEETING;
        assertCommandSuccess(command, index, MEETING);
        // @@author tanchc
        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + DATE_DESC_MEETING;
        ReadOnlyTask taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withDate(VALID_DATE_MEETING).build();
        assertCommandSuccess(command, index, editedTask);
        // @@author ahmadalkaff
        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " 0" + APPOINTMENT_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " -1" + APPOINTMENT_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredTaskList().size() + 1;
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + invalidIndex + APPOINTMENT_DESC_MEETING,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MEETING,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
                EditTaskCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid appointment -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_APPOINTMENT_DESC,
                Appointment.MESSAGE_APPOINTMENT_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_DATE_DESC,
                Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid start time -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_START_TIME_DESC,
                StartTime.MESSAGE_TIME_CONSTRAINTS);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyTask, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditTaskCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyTask, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyTask editedTask) {
        assertCommandSuccess(command, toEdit, editedTask, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditTaskCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the task at index {@code toEdit} being
     * updated to values specified {@code editedTask}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditTaskCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyTask editedTask,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTask(
                    expectedModel.getFilteredTaskList().get(toEdit.getZeroBased()), editedTask);
            expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        } catch (TaskNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedTask isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditTaskCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      Index expectedSelectedCardIndex) {
        executeCommand(command);

        expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        System.out.println(command);
        System.out.println(expectedModel.getFilteredTaskList());
        // assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
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
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
