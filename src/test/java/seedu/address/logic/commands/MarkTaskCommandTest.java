package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

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
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteCommand}.
 */
public class MarkTaskCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() throws Exception {
        ReadOnlyTask taskToMark = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        MarkTaskCommand markTaskCommand = prepareCommand(INDEX_FIRST_TASK);

        //markTaskCommand.executeUndoableCommand();
        String expectedMessage = String.format(MarkTaskCommand.MESSAGE_MARK_TASK_SUCCESS, taskToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getTaskBook(), new UserPrefs());
        //System.out.println(expectedModel);
        //expectedModel.deleteTask(taskToDelete);

        assertCommandSuccess(markTaskCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);
        MarkTaskCommand markTaskCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(markTaskCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkTaskCommand markFirstCommand = new MarkTaskCommand(INDEX_FIRST_TASK);
        MarkTaskCommand markSecondCommand = new MarkTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(markFirstCommand.equals(markFirstCommand));

        // same values -> returns true
        MarkTaskCommand markFirstCommandCopy = new MarkTaskCommand(INDEX_FIRST_TASK);
        assertTrue(markFirstCommand.equals(markFirstCommandCopy));

        // different types -> returns false
        assertFalse(markFirstCommand.equals(1));

        // null -> returns false
        assertFalse(markFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(markFirstCommand.equals(markSecondCommand));
    }

    /**
     * Returns a {@code MarkTaskCommand} with the parameter {@code index}.
     */
    private MarkTaskCommand prepareCommand(Index index) {
        MarkTaskCommand markTaskCommand = new MarkTaskCommand(index);
        markTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return markTaskCommand;
    }

    /**
     * Updates {@code model}'s filtered list to show no task.
     */
    private void showNoTask(Model model) {
        model.updateFilteredTaskList(p -> false);

        assert model.getFilteredTaskList().isEmpty();
    }
}
