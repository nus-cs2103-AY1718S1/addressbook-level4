package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_MULTIPLE_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteTaskCommand}.
 */
public class DeleteTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    //@@author lancehaoh
    @Test
    public void executeValidMultipleIndexUnfilteredListSuccess() throws Exception {
        ReadOnlyTask taskToDelete1 = model.getFilteredTaskList().get(INDEX_MULTIPLE_TASK.get(0).getZeroBased());
        ReadOnlyTask taskToDelete2 = model.getFilteredTaskList().get(INDEX_MULTIPLE_TASK.get(1).getZeroBased());
        DeleteTaskCommand deleteCommand = prepareCommand(INDEX_MULTIPLE_TASK);
        String expectedMessage = "";
        expectedMessage =  expectedMessage.concat("\n" + taskToDelete2.toString());
        expectedMessage = expectedMessage.concat("\n" + taskToDelete1.toString());
        expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, expectedMessage);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete1);
        expectedModel.deleteTask(taskToDelete2);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author lancehaoh
    @Test
    public void executeValidSingleIndexUnfilteredListSuccess() throws Exception {

        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.get(0).getZeroBased());
        DeleteTaskCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author lancehaoh
    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        ArrayList<Index> outOfBound = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        DeleteTaskCommand deleteCommand = prepareCommand(outOfBound);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    //@@author lancehaoh
    @Test
    public void executeValidIndexFilteredListSuccess() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskToDelete = model.getFilteredTaskList().get(INDEX_FIRST_TASK.get(0).getZeroBased());
        DeleteTaskCommand deleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(DeleteTaskCommand.MESSAGE_DELETE_TASK_SUCCESS, taskToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteTask(taskToDelete);
        showNoTask(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    //@@author lancehaoh
    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK.get(0);
        ArrayList<Index> outOfBound = new ArrayList<>(Arrays.asList(outOfBoundIndex));
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTaskList().size());

        DeleteTaskCommand deleteCommand = prepareCommand(outOfBound);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    //@@author lancehaoh
    @Test
    public void equals() {
        DeleteTaskCommand deleteFirstCommand = new DeleteTaskCommand(INDEX_FIRST_TASK);
        DeleteTaskCommand deleteSecondCommand = new DeleteTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteTaskCommand deleteFirstCommandCopy = new DeleteTaskCommand(INDEX_FIRST_TASK);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // different task -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    //@@author lancehaoh
    /**
     * Returns a {@code DeleteTaskCommand} with the parameter {@code index}.
     */
    private DeleteTaskCommand prepareCommand(ArrayList <Index> index) {
        DeleteTaskCommand deleteCommand = new DeleteTaskCommand(index);
        deleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteCommand;
    }

    //@@author lancehaoh
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }
}
