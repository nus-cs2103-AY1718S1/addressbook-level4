package seedu.address.logic.commands;
//@@author Esilocke
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
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

public class SetCompleteCommandTest {
    private Model model = new ModelManager(getTypicalTasksOnlyAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredListValidIndex_success() throws Exception {
        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        SetCompleteCommand setCompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(SetCompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, true);

        assertCommandSuccess(setCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_unfilteredListInvalidIndex_failure() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        SetCompleteCommand setCompleteCommand = prepareCommand(outOfRangeIndex);

        assertCommandFailure(setCompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_changeCompletedTask_failure() throws Exception {
        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, true);
        SetCompleteCommand setCompleteCommand = new SetCompleteCommand(INDEX_FIRST_TASK);
        setCompleteCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());

        assertCommandFailure(setCompleteCommand, expectedModel, SetCompleteCommand.MESSAGE_TASK_ALREADY_COMPLETE);
    }

    @Test
    public void execute_filteredListValidIndex_success() throws Exception {
        showFirstTaskOnly(model);

        ReadOnlyTask taskToChange = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        SetCompleteCommand setCompleteCommand = prepareCommand(INDEX_FIRST_TASK);

        String expectedMessage = String.format(SetCompleteCommand.MESSAGE_SUCCESS, taskToChange);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setAsComplete(taskToChange, true);
        showFirstTaskOnly(expectedModel);

        assertCommandSuccess(setCompleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredListInvalidIndex_throwsCommandException() {
        showFirstTaskOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getTasksList().size());

        SetCompleteCommand setCompleteCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(setCompleteCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SetCompleteCommand setFirstCommand = new SetCompleteCommand(INDEX_FIRST_TASK);
        SetCompleteCommand setSecondCommand = new SetCompleteCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(setFirstCommand.equals(setFirstCommand));

        // same values -> returns true
        SetCompleteCommand setFirstCommandCopy = new SetCompleteCommand(INDEX_FIRST_TASK);
        assertTrue(setFirstCommand.equals(setFirstCommandCopy));

        // different types -> returns false
        assertFalse(setFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(setFirstCommand.equals(setSecondCommand));
    }

    /**
     * Returns a {@code SetCompleteCommand} with the parameter {@code index}.
     */
    public SetCompleteCommand prepareCommand(Index taskIndex) {
        SetCompleteCommand command = new SetCompleteCommand(taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
