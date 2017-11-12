package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.DESC_DEMO;
import static seedu.address.logic.commands.CommandTestUtil.DESC_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HOTPOT;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.assertTaskCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditTaskCommand.EditTaskDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.testutil.EditTaskDescriptorBuilder;
import seedu.address.testutil.TaskBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditTaskCommand.
 */
public class EditTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());

    /*@Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() throws Exception {
        Task editedTask = new TaskBuilder().build();
        //model.updateTaskPriority(model.getFilteredTaskList().get(0), 3);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(editedTask).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }*/

    /*@Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() throws Exception {
        Index indexLastTask = Index.fromOneBased(model.getFilteredTaskList().size());
        ReadOnlyTask lastTask = model.getFilteredTaskList().get(indexLastTask.getZeroBased());

        TaskBuilder taskInList = new TaskBuilder(lastTask);
        Task editedTask = taskInList.withName(VALID_NAME_HOTPOT).withDescription(VALID_DESCRIPTION_HOTPOT)
                .withTags(VALID_TAG_HOTPOT).build();

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT)
                .withDescription(VALID_DESCRIPTION_HOTPOT).withTags(VALID_TAG_HOTPOT).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(indexLastTask, descriptor);

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());
        expectedModel.updateTask(lastTask, editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK, new EditTaskDescriptor());
        ReadOnlyTask editedTask = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskInFilteredList = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        Task editedTask = new TaskBuilder(taskInFilteredList).withName(VALID_NAME_HOTPOT).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT).build());

        String expectedMessage = String.format(EditTaskCommand.MESSAGE_EDIT_TASK_SUCCESS, editedTask);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getTaskBook(), new UserPrefs());
        expectedModel.updateTask(model.getFilteredTaskList().get(0), editedTask);

        assertCommandSuccess(editTaskCommand, model, expectedMessage, expectedModel);
    }
    /*
    @Test
    public void execute_duplicateTaskUnfilteredList_failure() {
        Task firstTask = new Task(model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased()));
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder(firstTask).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_SECOND_TASK, descriptor);

        assertTaskCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }
    */
    /*@Test
    public void execute_duplicateTaskFilteredList_failure() {
        showFirstTaskOnly(model);

        // edit task in filtered list into a duplicate in task book
        ReadOnlyTask taskInList = model.getTaskBook().getTaskList().get(INDEX_SECOND_TASK.getZeroBased());
        EditTaskCommand editTaskCommand = prepareTaskCommand(INDEX_FIRST_TASK,
                new EditTaskDescriptorBuilder(taskInList).build());

        assertTaskCommandFailure(editTaskCommand, model, EditTaskCommand.MESSAGE_DUPLICATE_TASK);
    }*/

    @Test
    public void execute_invalidTaskIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT).build();
        EditTaskCommand editTaskCommand = prepareTaskCommand(outOfBoundIndex, descriptor);

        assertTaskCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of task book
     */
    @Test
    public void execute_invalidTaskIndexFilteredList_failure() {
        showFirstTaskOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of task book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getTaskBook().getTaskList().size());

        EditTaskCommand editTaskCommand = prepareTaskCommand(outOfBoundIndex,
                new EditTaskDescriptorBuilder().withName(VALID_NAME_HOTPOT).build());

        assertTaskCommandFailure(editTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditTaskCommand standardCommand = new EditTaskCommand(INDEX_FIRST_TASK, DESC_DEMO);

        // same values -> returns true
        EditTaskDescriptor copyDescriptor = new EditTaskDescriptor(DESC_DEMO);
        EditTaskCommand commandWithSameValues = new EditTaskCommand(INDEX_FIRST_TASK, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_SECOND_TASK, DESC_DEMO)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditTaskCommand(INDEX_FIRST_TASK, DESC_HOTPOT)));
    }

    /**
     * Returns an {@code EditTaskCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditTaskCommand prepareTaskCommand(Index index, EditTaskDescriptor descriptor) {
        EditTaskCommand editTaskCommand = new EditTaskCommand(index, descriptor);
        editTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editTaskCommand;
    }
}

