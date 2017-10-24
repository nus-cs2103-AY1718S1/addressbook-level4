package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code ExportTaskCommand}.
 */
public class ExportTaskCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
    }

    @Test
    public void executeValidIndexUnfilteredListSuccess() {
        Index lastTaskIndex = Index.fromOneBased(model.getFilteredTaskList().size());
        ReadOnlyTask firstTaskToExport = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());
        ReadOnlyTask lastTaskToExport;
        lastTaskToExport = model.getFilteredTaskList().get(lastTaskIndex.getZeroBased());

        final StringBuilder firstBuilder = new StringBuilder();
        firstTaskToExport.getTags().forEach(firstBuilder::append);
        String firstMessage = String.format(ExportTaskCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + firstTaskToExport.getName(),
                        "d/" + firstTaskToExport.getDescription(),
                        "s/" + firstTaskToExport.getStartDateTime(),
                        "e/" + firstTaskToExport.getEndDateTime(),
                        "t/" + firstBuilder));
        final StringBuilder lastBuilder = new StringBuilder();
        lastTaskToExport.getTags().forEach(lastBuilder::append);
        String lastMessage = String.format(ExportTaskCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + lastTaskToExport.getName(),
                        "d/" + lastTaskToExport.getDescription(),
                        "s/" + lastTaskToExport.getStartDateTime(),
                        "e/" + lastTaskToExport.getEndDateTime(),
                        "t/" + lastBuilder));

        assertExecutionSuccess(INDEX_FIRST_TASK, firstMessage);
        //assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastTaskIndex, lastMessage);
    }

    @Test
    public void executeInvalidIndexUnfilteredListFailure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredTaskList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void executeValidIndexFilteredListSuccess() {
        showFirstTaskOnly(model);
        ReadOnlyTask taskToExport = model.getFilteredTaskList().get(INDEX_FIRST_TASK.getZeroBased());

        final StringBuilder builder = new StringBuilder();
        taskToExport.getTags().forEach(builder::append);
        String expectedMessage = String.format(ExportTaskCommand.MESSAGE_SUCCESS,
                String.join(" ", "n/" + taskToExport.getName(),
                        "d/" + taskToExport.getDescription(),
                        "s/" + taskToExport.getStartDateTime(),
                        "e/" + taskToExport.getEndDateTime(),
                        "t/" + builder));

        assertExecutionSuccess(INDEX_FIRST_TASK, expectedMessage);
    }

    @Test
    public void executeInvalidIndexFilteredListFailure() {
        showFirstTaskOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_TASK;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getTaskBook().getTaskList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        ExportTaskCommand exportTaskFirstCommand = new ExportTaskCommand(INDEX_FIRST_TASK);
        ExportTaskCommand exportTaskSecondCommand = new ExportTaskCommand(INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(exportTaskFirstCommand.equals(exportTaskFirstCommand));

        // same values -> returns true
        ExportTaskCommand exportTaskFirstCommandCopy = new ExportTaskCommand(INDEX_FIRST_TASK);
        assertTrue(exportTaskFirstCommand.equals(exportTaskFirstCommandCopy));

        // different types -> returns false
        assertFalse(exportTaskFirstCommand.equals(1));

        // null -> returns false
        assertFalse(exportTaskFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(exportTaskFirstCommand.equals(exportTaskSecondCommand));
    }

    /**
     * Executes a {@code ExportCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String expectedMessage) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        ExportTaskCommand exportTaskCommand = prepareCommand(index);

        try {
            CommandResult commandResult = exportTaskCommand.execute();
            assertEquals(expectedMessage, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToTaskListRequestEvent lastEvent =
                (JumpToTaskListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

    /**
     * Executes a {@code ExportTaskCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        ExportTaskCommand exportTaskCommand = prepareCommand(index);

        try {
            exportTaskCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ExportCommand} with parameters {@code index}.
     */
    private ExportTaskCommand prepareCommand(Index index) {
        ExportTaskCommand exportTaskCommand = new ExportTaskCommand(index);
        exportTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return exportTaskCommand;
    }
}
