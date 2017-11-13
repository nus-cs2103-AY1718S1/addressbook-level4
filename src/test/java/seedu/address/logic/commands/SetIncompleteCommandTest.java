package seedu.address.logic.commands;
//@@author Esilocke
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.logic.commands.CommandTestUtil.showSecondTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalTasks.getTypicalTasksOnlyAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;

public class SetIncompleteCommandTest {
    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_validTaskIndex_success() throws Exception {
        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_THIRD_TASK.getZeroBased());
        SetIncompleteCommand setIncompleteCommand = prepareCommand(INDEX_THIRD_TASK);

        String expectedMessage = String.format(SetIncompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, false);

        assertCommandSuccess(setIncompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTaskIndex_failure() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        SetIncompleteCommand setIncompleteCommand = prepareCommand(outOfRangeIndex);

        assertCommandFailure(setIncompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_changeIncompleteTask_failure() throws Exception {
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        SetIncompleteCommand setIncompleteCommand = new SetIncompleteCommand(INDEX_FIRST_TASK);
        setIncompleteCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(setIncompleteCommand, expectedModel, SetIncompleteCommand.MESSAGE_TASK_ALREADY_COMPLETE);
    }

    @Test
    public void equals() {
        SetIncompleteCommand setFirstCommand = new SetIncompleteCommand(INDEX_FIRST_TASK);
        SetIncompleteCommand setSecondCommand = new SetIncompleteCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns true
        SetIncompleteCommand setFirstCommandCopy = new SetIncompleteCommand(INDEX_FIRST_TASK);
        assertTrue(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    @Test
    public void execute_filteredListValidIndex_success() throws Exception {
        showSecondTaskOnly(model);

        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        SetIncompleteCommand setIncompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(SetIncompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, false);
        showSecondTaskOnly(expectedModel);

        assertCommandSuccess(setIncompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListInvalidIndex_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        SetIncompleteCommand setIncompleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(setIncompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code SetIncompleteCommand} with the parameter {@code index}.
     */
    public SetIncompleteCommand prepareCommand(Index taskIndex) {
        SetIncompleteCommand command = new SetIncompleteCommand(taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
