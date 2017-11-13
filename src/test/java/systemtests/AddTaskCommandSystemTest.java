package systemtests;

import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUEDATE_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_SHOPPING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_ASSIGNMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_SHOPPING;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.task.DueDate;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;


//@@author zhangshuoyang
public class AddTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        /* Case: add a task to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyTask toAdd = ASSIGNMENT;
        String command = "   " + AddTaskCommand.COMMAND_WORD + "  " + DESCRIPTION_DESC_ASSIGNMENT + "  "
                + PRIORITY_DESC_ASSIGNMENT + " " + DUEDATE_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);


        //TODO: undo/redo cases need to be checked again.

        /* Case: undo adding Assignment to the list -> ASSIGNMENT deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Assignment to the list -> Assignment added again */
        command = RedoCommand.COMMAND_WORD;
        model.addTask(toAdd);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate task -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + DUEDATE_DESC_ASSIGNMENT;
        assertCommandFailure(command, AddTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: add a task with all fields same as another task in the address book except description -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_SHOPPING).withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUEDATE_ASSIGNMENT).build();
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_SHOPPING + PRIORITY_DESC_ASSIGNMENT
                + DUEDATE_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except priority -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_ASSIGNMENT).withPriority(VALID_PRIORITY_SHOPPING)
                .withDueDate(VALID_DUEDATE_ASSIGNMENT).build();
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_SHOPPING
                + DUEDATE_DESC_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add a task with all fields same as another task in the address book except due date -> added */
        toAdd = new TaskBuilder().withDescription(VALID_DESCRIPTION_ASSIGNMENT).withPriority(VALID_PRIORITY_ASSIGNMENT)
                .withDueDate(VALID_DUEDATE_SHOPPING).build();
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_ASSIGNMENT + PRIORITY_DESC_ASSIGNMENT
                + DUEDATE_DESC_SHOPPING;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getTaskList().size() == 0;
        assertCommandSuccess(ASSIGNMENT);

        /* Case: invalid keyword -> rejected */
        command = "taks" + TaskUtil.getTaskDetails(toAdd);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid priority -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_SHOPPING + INVALID_PRIORITY_DESC_SHOPPING
                + DUEDATE_DESC_SHOPPING;
        assertCommandFailure(command, Priority.MESSAGE_PRIORITY_CONSTRAINTS);

        /* Case: invalid due date -> rejected */
        command = AddTaskCommand.COMMAND_WORD + DESCRIPTION_DESC_SHOPPING + PRIORITY_DESC_SHOPPING
                + INVALID_DUEDATE_DESC_SHOPPING;
        assertCommandFailure(command, DueDate.MESSAGE_DATE_INVALID_CONSTRAINTS);
    }


    /**
     * Executes the {@code AddTaskCommand} that adds {@code toAdd} to the model and verifies that the command
     * box displays an empty string, the result display box displays the success message of executing
     * {@code AddTaskCommand} with the details of {@code toAdd}, and the model related components equal to the
     * current model added with {@code toAdd}. These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class, the status bar's sync status changes,
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
        } catch (DuplicateTaskException dte) {
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
