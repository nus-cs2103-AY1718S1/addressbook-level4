package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ResizeMainWindowEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author newalter
/**
 * Contains integration tests (interaction with the Model) for {@code ResizeCommand}.
 */
public class ResizeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validParameters_success() {
        assertExecutionSuccess(ResizeCommand.MAX_WIDTH, ResizeCommand.MAX_HEIGHT);
        assertExecutionSuccess(ResizeCommand.MAX_WIDTH - 100, ResizeCommand.MAX_HEIGHT - 100);
    }

    @Test
    public void execute_invalidParameters_failure() {
        assertExecutionFailure(ResizeCommand.MAX_WIDTH + 1, ResizeCommand.MAX_HEIGHT,
                Messages.MESSAGE_INVALID_COMMAND_PARAMETERS);
    }

    @Test
    public void equals() {
        ResizeCommand resizeFirstCommand = new ResizeCommand(1280, 720);
        ResizeCommand resizeSecondCommand = new ResizeCommand(1280, 600);

        // same object -> returns true
        assertTrue(resizeFirstCommand.equals(resizeFirstCommand));

        // same values -> returns true
        ResizeCommand resizeFirstCommandCopy = new ResizeCommand(1280, 720);
        assertTrue(resizeFirstCommand.equals(resizeFirstCommandCopy));

        // different types -> returns false
        assertFalse(resizeFirstCommand.equals(1));

        // null -> returns false
        assertFalse(resizeFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(resizeFirstCommand.equals(resizeSecondCommand));
    }

    /**
     * Executes a {@code ResizeCommand} with the given {@code width, height},
     * and checks that {@code ResizeMainWindowEvent} is raised with the correct parameters.
     */
    private void assertExecutionSuccess(int width, int height) {
        ResizeCommand resizeCommand = prepareCommand(width, height);

        try {
            CommandResult commandResult = resizeCommand.execute();
            assertEquals(String.format(ResizeCommand.MESSAGE_SUCCESS, width, height),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ResizeMainWindowEvent lastEvent = (ResizeMainWindowEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(width, lastEvent.getWidth());
        assertEquals(height, lastEvent.getHeight());
    }

    /**
     * Executes a {@code ResizeCommand} with the given {@code width, height}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(int width, int height, String expectedMessage) {
        ResizeCommand resizeCommand = prepareCommand(width, height);

        try {
            resizeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code ResizeCommand} with parameters {@code width, height}.
     */
    private ResizeCommand prepareCommand(int width, int height) {
        ResizeCommand resizeCommand = new ResizeCommand(width, height);
        resizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return resizeCommand;
    }
}
