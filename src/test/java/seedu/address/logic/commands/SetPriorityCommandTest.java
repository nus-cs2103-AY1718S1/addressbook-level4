package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SetPriorityCommandTest}.
 */
public class SetPriorityCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionSuccess(INDEX_FIRST_TASK, 1);
        assertExecutionSuccess(INDEX_THIRD_TASK, 1);
        assertExecutionSuccess(lastTaskIndex, 1);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, 1, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPriorityValue_failure() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());

        assertExecutionFailure(lastTaskIndex, 100, SetPriorityCommand.MESSAGE_UPDATE_TASK_PRIORITY_OUT_OF_RANGE);

    }

    @Test
    public void equals() {
        SetPriorityCommand setPriorityFirstCommand = new SetPriorityCommand(INDEX_FIRST_TASK, 1);
        SetPriorityCommand setPrioritySecondCommand = new SetPriorityCommand(INDEX_SECOND_TASK, 2);

        // same object
        assertTrue(setPriorityFirstCommand.equals(setPriorityFirstCommand));

        // same values -> returns true
        SetPriorityCommand setPriorityFirstCommandCopy = new SetPriorityCommand(INDEX_FIRST_TASK, 1);
        assertTrue(setPriorityFirstCommand.equals(setPriorityFirstCommandCopy));

        // different types -> returns false
        assertFalse(setPriorityFirstCommand.equals(1));

        // null -> returns false
        assertFalse(setPriorityFirstCommand.equals(null));

        // different task -> returns false
        assertFalse(setPriorityFirstCommand.equals(setPrioritySecondCommand));
    }

    /**
     * Executes a {@code SetPriorityCommand} with the given {@code index, value}, and checks that
     * the updated task priority is properly reflected.
     */
    private void assertExecutionSuccess(Index index, Integer value) {
        SetPriorityCommand setPriorityCommand = prepareCommand(index, value);
        int indexInteger = index.getZeroBased();

        try {
            CommandResult commandResult = setPriorityCommand.execute();
            assertEquals(String.format(SetPriorityCommand.MESSAGE_UPDATE_TASK_PRIORITY_SUCCESS,
                    getTypicalTaskbook().getTaskList().get(indexInteger).getName()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        assertEquals(value, model.getTaskBook().getTaskList().get(indexInteger).getPriority());
    }

    /**
     * Executes a {@code SetPriorityCommand} with the given {@code index, value}, and checks that a
     * {@code CommandException is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, Integer value, String expectedMessage) {
        SetPriorityCommand setPriorityCommand = prepareCommand(index, value);

        try {
            setPriorityCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SetPriorityCommand} with parameters {@code index, value}.
     */
    private SetPriorityCommand prepareCommand(Index index, Integer value) {
        SetPriorityCommand setPriorityCommand = new SetPriorityCommand(index, value);
        setPriorityCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setPriorityCommand;
    }
}
