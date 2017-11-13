// @@author tanchc
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.APPOINTMENT_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.START_TIME_DESC_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_APPOINTMENT_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MOVIE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_MOVIE;
import static seedu.address.testutil.TypicalTasks.MOVIE;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.Date;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.StartTime;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a task without mods to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyTask toAdd = MOVIE;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + APPOINTMENT_DESC_MOVIE + "  " + DATE_DESC_MOVIE
                + "  " + START_TIME_DESC_MOVIE + " ";
        assertTaskCommandSuccess(command, toAdd);

        /* Case: undo adding Meeting to the list -> Meeting deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertTaskCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Meeting to the list -> Meeting added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertTaskCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a task with all fields same as another task in the address book except appointment -> added */
        toAdd = new TaskBuilder().withAppointment(VALID_APPOINTMENT_EVENT).withDate(VALID_DATE_MOVIE)
                .withStartTime(VALID_START_TIME_MOVIE).build();
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_EVENT + DATE_DESC_MOVIE + START_TIME_DESC_MOVIE;
        assertTaskCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except date -> added */
        toAdd = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MOVIE).withDate(VALID_DATE_EVENT)
                .withStartTime(VALID_START_TIME_MOVIE).build();
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_EVENT + START_TIME_DESC_MOVIE;
        assertTaskCommandSuccess(command, toAdd);

        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new TaskBuilder().withAppointment(VALID_APPOINTMENT_MOVIE).withDate(VALID_DATE_MOVIE)
                .withStartTime(VALID_START_TIME_EVENT).build();
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_MOVIE + START_TIME_DESC_EVENT;
        assertTaskCommandSuccess(command, toAdd);

        /* Case: missing appointment -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DATE_DESC_MOVIE + START_TIME_DESC_MOVIE;
        assertTaskCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + START_TIME_DESC_MOVIE;
        assertTaskCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: missing startTime -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_MOVIE;
        assertTaskCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addtasks " + TaskUtil.getTaskDetails(toAdd);
        assertTaskCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + INVALID_DATE_DESC + START_TIME_DESC_MOVIE;
        assertTaskCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid startTime -> rejected */
        command = AddTaskCommand.COMMAND_WORD + APPOINTMENT_DESC_MOVIE + DATE_DESC_MOVIE + INVALID_START_TIME_DESC;
        assertTaskCommandFailure(command, StartTime.MESSAGE_TIME_CONSTRAINTS);
    }

    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing {@code AddCommand}
     * with the details of {@code toAdd}, and the model related components equal to the current model added with
     * {@code toAdd}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertTaskCommandSuccess(ReadOnlyTask toAdd) {
        assertTaskCommandSuccess(TaskUtil.getAddTaskCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertTaskCommandSuccess(ReadOnlyTask)}. Executes {@code command}
     * instead.
     * @see AddTaskCommandSystemTest#assertTaskCommandSuccess(ReadOnlyTask)
     */
    private void assertTaskCommandSuccess(String command, ReadOnlyTask toAdd) {
        Model expectedModel = getModel();
        expectedModel.addTask(toAdd);

        String expectedResultMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, toAdd);

        assertTaskCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertTaskCommandSuccess(String, ReadOnlyTask)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddTaskCommandSystemTest#assertTaskCommandSuccess(String, ReadOnlyTask)
     */
    private void assertTaskCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
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
    private void assertTaskCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
