package systemtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.ChangeModeCommand.MESSAGE_CHANGE_MODE_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_QUOTED_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STARTDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.STARTDATE_DESC_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.UNQUOTED_DESCRIPTION_PAPER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DEADLINE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GRAD_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE_BY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalTasks.GRAD_SCHOOL;
import static seedu.address.testutil.TypicalTasks.INTERNSHIP;
import static seedu.address.testutil.TypicalTasks.KEYWORD_MATCHING_FINISH;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeModeCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Description;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDates;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TaskUtil;

public class EditTaskCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void edit() throws Exception {
        /* Case: change the current command mode to task manager -> success */
        Model expectedModel = getModel();
        String commandMode = ChangeModeCommand.COMMAND_WORD + " tm";
        String expectedResultMessage = String.format(MESSAGE_CHANGE_MODE_SUCCESS, "taskmanager");
        assertCommandSuccess(commandMode, expectedModel, expectedResultMessage);

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */

        /* Case: edit all fields, command with leading spaces, trailing spaces and multiple spaces between each field
         * -> edited
         */
        Index index = INDEX_FIRST_TASK;
        String command = " " + EditTaskCommand.COMMAND_WORD + "  " + index.getOneBased() + "  "
                + VALID_DESCRIPTION_INTERNSHIP + "  " + STARTDATE_DESC_INTERNSHIP + " " + DEADLINE_DESC_INTERNSHIP
                + "  " + TAG_DESC_URGENT + " ";
        Task editedTask = new TaskBuilder().withDescription(VALID_DESCRIPTION_INTERNSHIP)
                .withStartDate(VALID_STARTDATE_INTERNSHIP).withDeadline(VALID_DEADLINE_INTERNSHIP)
                .withTags(VALID_TAG_URGENT).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: undo editing the last task in the list -> last task restored */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: redo editing the last person in the list -> last person edited again */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        expectedModel.updateTask(
                getModel().getFilteredTaskList().get(INDEX_FIRST_PERSON.getZeroBased()), editedTask);
        assertCommandSuccess(command, expectedModel, expectedResultMessage);

        /* Case: edit a task with new values same as existing values -> edited */
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_INTERNSHIP
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        assertCommandSuccess(command, index, INTERNSHIP);

        /* Case: edit some fields -> edited */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_NOT_URGENT;
        ReadOnlyTask taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withTags(VALID_TAG_NOT_URGENT).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear tags -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_TAG.getPrefix();
        editedTask = new TaskBuilder(taskToEdit).withTags().build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear start dates -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_STARTDATE.getPrefix()
                + TAG_DESC_URGENT;
        editedTask = new TaskBuilder(taskToEdit).withStartDate("").build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: clear deadlines -> cleared */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_DEADLINE_BY.getPrefix()
                + STARTDATE_DESC_INTERNSHIP;
        editedTask = new TaskBuilder(taskToEdit).withDeadline("").build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: deadline prefix inside quoted description -> only description edited */
        index = INDEX_FIRST_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + DESCRIPTION_QUOTED_PAPER
                + DEADLINE_DESC_INTERNSHIP;
        editedTask = new TaskBuilder(taskToEdit).withDescription(UNQUOTED_DESCRIPTION_PAPER).build();
        assertCommandSuccess(command, index, editedTask);

        /* ------------------ Performing edit operation while a filtered list is being shown ------------------------ */

        /* Case: filtered task list, edit index within bounds of address book and task list -> edited */
        showTasksWithDescription(KEYWORD_MATCHING_FINISH);
        index = INDEX_FIRST_TASK;
        assertTrue(index.getZeroBased() < getModel().getFilteredTaskList().size());
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + " " + VALID_DESCRIPTION_INTERNSHIP;
        taskToEdit = getModel().getFilteredTaskList().get(index.getZeroBased());
        editedTask = new TaskBuilder(taskToEdit).withDescription(VALID_DESCRIPTION_INTERNSHIP).build();
        assertCommandSuccess(command, index, editedTask);

        /* Case: filtered task list, edit index within bounds of address book but out of bounds of task list
         * -> rejected
         */
        showTasksWithDescription(KEYWORD_MATCHING_FINISH);
        int invalidIndex = getModel().getAddressBook().getTaskList().size();
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + invalidIndex + VALID_DESCRIPTION_INTERNSHIP,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* --------------------- Performing edit operation while a person card is selected -------------------------- */

        /* Case: selects first card in the person list, edit a person -> edited, card selection remains unchanged
         */
        showAllTasks();
        index = INDEX_FIRST_TASK;
        selectTask(index);
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_INTERNSHIP
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_URGENT;
        // this can be misleading: card selection actually remains unchanged
        assertCommandSuccess(command, index, INTERNSHIP, index);

        /* --------------------------------- Performing invalid edit operation -------------------------------------- */

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " 0" + VALID_DESCRIPTION_INTERNSHIP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " -1" + VALID_DESCRIPTION_INTERNSHIP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: invalid index (size + 1) -> rejected */
        invalidIndex = getModel().getFilteredTaskList().size() + 1;
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + invalidIndex + VALID_DESCRIPTION_INTERNSHIP,
                Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);

        /* Case: missing index -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + VALID_DESCRIPTION_INTERNSHIP,
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, EditTaskCommand.MESSAGE_USAGE));

        /* Case: missing all fields -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased(),
                EditTaskCommand.MESSAGE_NOT_EDITED);

        /* Case: invalid description -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_DESCRIPTION, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        /* Case: invalid start date -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                + INVALID_STARTDATE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid deadline -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_DEADLINE_DESC, TaskDates.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid tag -> rejected */
        assertCommandFailure(EditTaskCommand.COMMAND_WORD + " " + INDEX_FIRST_TASK.getOneBased()
                        + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        /* Case: edit a task with new values same as another task's values -> rejected */
        executeCommand(TaskUtil.getAddTaskCommand(GRAD_SCHOOL));
        assertTrue(getModel().getAddressBook().getTaskList().contains(GRAD_SCHOOL));
        index = INDEX_FIRST_TASK;
        assertFalse(getModel().getFilteredTaskList().get(index.getZeroBased()).equals(GRAD_SCHOOL));
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_GRAD_SCHOOL
                + STARTDATE_DESC_GRAD_SCHOOL + DEADLINE_DESC_GRAD_SCHOOL + TAG_DESC_URGENT;
        assertCommandFailure(command, EditTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: edit a task with new values same as another task's values but with different tags -> rejected */
        index = INDEX_SECOND_TASK;
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + VALID_DESCRIPTION_INTERNSHIP
                + STARTDATE_DESC_INTERNSHIP + DEADLINE_DESC_INTERNSHIP + TAG_DESC_NOT_URGENT;
        assertCommandFailure(command, EditTaskCommand.MESSAGE_DUPLICATE_TASK);

        /* Case: description contains deadline prefix but without quotes -> rejected */
        command = EditTaskCommand.COMMAND_WORD + " " + index.getOneBased() + UNQUOTED_DESCRIPTION_PAPER;
        assertCommandFailure(command, String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                EditTaskCommand.MESSAGE_USAGE));
    }
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Index, ReadOnlyTask, Index)} except that
     * the browser url and selected card remain unchanged.
     * @param toEdit the index of the current model's filtered list
     * @see EditCommandSystemTest#assertCommandSuccess(String, Index, ReadOnlyTask, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyTask editedTask) {
        assertCommandSuccess(command, toEdit, editedTask, null);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} and in addition,<br>
     * 1. Asserts that result display box displays the success message of executing {@code EditCommand}.<br>
     * 2. Asserts that the model related components are updated to reflect the person at index {@code toEdit} being
     * updated to values specified {@code editedPerson}.<br>
     * @param toEdit the index of the current model's filtered list.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Index toEdit, ReadOnlyTask editedTask,
                                      Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        try {
            expectedModel.updateTask(
                    expectedModel.getFilteredTaskList().get(toEdit.getZeroBased()), editedTask);
            expectedModel.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        } catch (DuplicateTaskException | TaskNotFoundException e) {
            throw new IllegalArgumentException(
                    "editedTask is a duplicate in expectedModel, or it isn't found in the model.");
        }

        assertCommandSuccess(command, expectedModel,
                String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
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
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedTaskCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedTaskCardUnchanged();
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
        assertSelectedTaskCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
