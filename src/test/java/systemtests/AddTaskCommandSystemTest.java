package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ChangeModeCommand.MESSAGE_CHANGE_MODE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTTIME_VALID_ENDTIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_INCORRECT_ORDER;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_TIME_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.MIXED_TIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_GYM;
import static seedu.address.logic.commands.CommandTestUtil.TIME_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GYM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENDTIME_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTTIME_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.testutil.TypicalTasks.BUY_PRESENTS;
import static seedu.address.testutil.TypicalTasks.GYM;
import static seedu.address.testutil.TypicalTasks.INTERNSHIP;
import static seedu.address.testutil.TypicalTasks.MEETUP;
import static seedu.address.testutil.TypicalTasks.SUBMISSION;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.DateTimeValidator;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

//@@author raisa2010
public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {

        /* Change the current command mode to task manager */
        String commandMode = ChangeModeCommand.COMMAND_WORD + " tm";
        String expectedResultMessage = String.format(MESSAGE_CHANGE_MODE_SUCCESS, "TaskManager");
        Model model = getModel();
        assertCommandSuccess(commandMode, model, expectedResultMessage);

        /* Case: add a task to a non-empty address book, command with leading spaces and trailing spaces
         * -> added */
        ReadOnlyTask toAdd = INTERNSHIP;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + VALID_DESCRIPTION_INTERNSHIP + "  "
                + DEADLINE_DESC_INTERNSHIP + " " + TIME_DESC_INTERNSHIP + " " + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: undo adding Internship to the list -> Internship deleted */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Internship to the list -> Internship added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate task -> rejected */
        command = "   " + AddTaskCommand.COMMAND_WORD + "  " + VALID_DESCRIPTION_INTERNSHIP + "  "
                 + DEADLINE_DESC_INTERNSHIP + " " + TIME_DESC_INTERNSHIP;
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a task with all fields same as another task in the address book except description -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime(VALID_STARTTIME_INTERNSHIP)
                .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_GRAD_SCHOOL + DEADLINE_DESC_INTERNSHIP
                + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except deadline -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_GRAD_SCHOOL).withStartTime(VALID_STARTTIME_INTERNSHIP)
                .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_GRAD_SCHOOL
                + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except start time -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime(VALID_STARTTIME_GRAD_SCHOOL)
                .withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + MIXED_TIME_DESC_GRAD_SCHOOL + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except end time -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withDeadline(VALID_DEADLINE_INTERNSHIP).withStartTime(VALID_STARTTIME_INTERNSHIP)
                .withEndTime(VALID_ENDTIME_GRAD_SCHOOL).withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + MIXED_TIME_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with an invalid start time and a valid end time -> only end time accepted */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withStartTime("").withEndTime(VALID_ENDTIME_INTERNSHIP).withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + INVALID_STARTTIME_VALID_ENDTIME_DESC + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        assertCommandSuccess(INTERNSHIP);

        /* Case: add a task, missing deadline -> current day added as deadline */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_GYM + TIME_DESC_GYM + TAG_DESC_URGENT;
        assertCommandSuccess(command, GYM);

        /* Case: add a task, missing date and time -> added */
        assertCommandSuccess(BUY_PRESENTS);

        /* Case: add a task, missing start time -> added */
        assertCommandSuccess(SUBMISSION);

        /* Case: add a task, missing end time -> added */
        assertCommandSuccess(MEETUP);

        /* Case: missing description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " " + DEADLINE_DESC_INTERNSHIP + TIME_DESC_INTERNSHIP;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "adds " + TaskUtil.getTaskDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + INVALID_DESCRIPTION + DEADLINE_DESC_INTERNSHIP
                + TIME_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandFailure(command, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid unquoted description containing deadline prefix -> rejected for invalid deadline */
        command = AddTaskCommand.COMMAND_WORD + " " + UNQUOTED_DESCRIPTION_PAPER + TIME_DESC_INTERNSHIP;
        assertCommandFailure(command, DateTimeValidator.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid start time -> rejected */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + INVALID_STARTTIME_DESC + TAG_DESC_URGENT;
        assertCommandFailure(command, DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        /* Case: add a task with start time after end time -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " " + VALID_DESCRIPTION_INTERNSHIP + DEADLINE_DESC_INTERNSHIP
                + INVALID_TIME_DESC_INCORRECT_ORDER;
        assertCommandFailure(command, DateTimeValidator.MESSAGE_TIME_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + TIME_DESC_INTERNSHIP
                + INVALID_DEADLINE_DESC + TAG_DESC_URGENT;
        assertCommandFailure(command, DateTimeValidator.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + TIME_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + INVALID_TAG_DESC;
        assertCommandFailure(command, Tag.MESSAGE_TAG_CONSTRAINTS);
    }
    //@@author

    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and verifies that
     * the command box displays an empty string, the result display box displays the success message of executing
     * {@code AddTaskCommand} with the details of {@code toAdd}, and the model related components equal to the
     * current model added with {@code toAdd}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *Also verifies that the command box has the default style class, the status bar's sync status changes,
     * the browser url and selected card remains unchanged.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyTask toAdd) {
        assertCommandSuccess(TaskUtil.getAddTaskCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyTask)}. Executes {@code command}
     * instead.
     * @see AddTaskCommandSystemTest#assertCommandSuccess(ReadOnlyTask)
     */
    private void assertCommandSuccess(String command, ReadOnlyTask toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addTask(toAdd);
        } catch (IllegalValueException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddTaskCommand.MESSAGE_SUCCESS, toAdd);

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyTask)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddTaskCommandSystemTest#assertCommandSuccess(String, ReadOnlyTask)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedTaskCardUnchanged();
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
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedTaskCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
