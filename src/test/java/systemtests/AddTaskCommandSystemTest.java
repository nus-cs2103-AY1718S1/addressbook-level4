package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ChangeModeCommand.MESSAGE_CHANGE_MODE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.testutil.TypicalTasks.GRAD_SCHOOL;
import static seedu.address.testutil.TypicalTasks.INTERNSHIP;

import org.junit.Test;

import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.tasks.AddTaskCommand;
import seedu.address.model.Model;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {

        /* Case: change the current command mode to task manager -> success */
        Model expectedModel = getModel();
        String commandMode = ChangeModeCommand.COMMAND_WORD + " tm";
        String expectedResultMessage = String.format(MESSAGE_CHANGE_MODE_SUCCESS, "taskmanager");
        assertCommandSuccess(commandMode, expectedModel, expectedResultMessage);

        Model model = getModel();

        /* Case: add a task without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added */
        ReadOnlyTask toAdd = INTERNSHIP;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + VALID_DESCRIPTION_INTERNSHIP + "  "
                + STARTDATE_DESC_INTERNSHIP + " " + DEADLINE_DESC_INTERNSHIP + " " + TAG_DESC_URGENT;
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
                + STARTDATE_DESC_INTERNSHIP + " " + DEADLINE_DESC_INTERNSHIP + " ";
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a task with all fields same as another task in the address book except description -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_GRAD_SCHOOL)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_GRAD_SCHOOL + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except start date -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_GRAD_SCHOOL).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_GRAD_SCHOOL
                + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except deadline -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_GRAD_SCHOOL)
                .withTags(VALID_TAG_URGENT).build();
        command = AddTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP + STARTDATE_DESC_INTERNSHIP
                + DEADLINE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        assertCommandSuccess(GRAD_SCHOOL);

        /* Case: add a task, missing task dates -> added */
        //assertCommandSuccess(BUY_PRESENTS);

        /* Case: add a task, missing start date -> added */
        // assertCommandSuccess(SUBMISSION);

        /* Case: add a task, missing deadline -> added */
        // assertCommandSuccess(GYM);

        /* Case: missing description -> rejected */
        command = AddTaskCommand.COMMAND_WORD + " " + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTaskCommand.MESSAGE_USAGE));
    }

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
        } catch (DuplicateTaskException dpe) {
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
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
