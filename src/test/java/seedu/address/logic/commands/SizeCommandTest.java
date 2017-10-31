// @@author donjar
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.FontSizeChangeRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SizeCommand}.
 */
public class SizeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_resetSize() {
        SizeCommand sizeCommand = prepareCommand();
        try {
            CommandResult commandResult = sizeCommand.execute();

            assertEquals(SizeCommand.MESSAGE_RESET_FONT_SUCCESS, commandResult.feedbackToUser);

            FontSizeChangeRequestEvent lastEvent =
                    (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(0, lastEvent.sizeChange);
        } catch (CommandException e) {
            fail("This exception should not be thrown.");
        }
    }

    @Test
    public void execute_increaseSize() {
        assertExecutionSuccess(3, String.format(SizeCommand.MESSAGE_CHANGE_FONT_SUCCESS, "increased", 3, 3));
    }

    @Test
    public void execute_decreaseSize() {
        assertExecutionSuccess(-3, String.format(SizeCommand.MESSAGE_CHANGE_FONT_SUCCESS, "decreased", 3, -3));
    }

    @Test
    public void execute_increaseSizeOutOfBounds() {
        assertExecutionFailure(6);
    }

    @Test
    public void execute_decreaseSizeOutOfBounds() {
        assertExecutionFailure(-6);
    }

    @Test
    public void equals() {
        SizeCommand sizeResetCommand = new SizeCommand();
        SizeCommand sizeIncrementCommand = new SizeCommand(1);
        SizeCommand sizeDecrementCommand = new SizeCommand(-1);

        // same object -> returns true
        assertTrue(sizeResetCommand.equals(sizeResetCommand));

        // same values -> returns true
        SizeCommand sizeResetCommandCopy = new SizeCommand();
        assertTrue(sizeResetCommand.equals(sizeResetCommandCopy));

        // different types -> returns false
        assertFalse(sizeResetCommand.equals(1));

        // null -> returns false
        assertFalse(sizeResetCommand.equals(null));

        // different type -> returns false
        assertFalse(sizeResetCommand.equals(sizeIncrementCommand));

        // different value -> returns false
        assertFalse(sizeDecrementCommand.equals(sizeIncrementCommand));
    }

    /**
     * Returns a {@code SizeCommand} with parameters given.
     */
    private SizeCommand prepareCommand() {
        SizeCommand sizeCommand = new SizeCommand();
        sizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sizeCommand;
    }

    private SizeCommand prepareCommand(int change) {
        SizeCommand sizeCommand = new SizeCommand(change);
        sizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sizeCommand;
    }

    /**
     * Executes a {@code SizeCommand} with the given {@code change}, and checks that {@code FontSizeChangeRequestEvent}
     * is raised with the correct size change.
     */
    private void assertExecutionSuccess(int change, String message) {
        SizeCommand sizeCommand = prepareCommand(change);
        try {
            CommandResult commandResult = sizeCommand.execute();

            assertEquals(message, commandResult.feedbackToUser);

            FontSizeChangeRequestEvent lastEvent =
                    (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
            assertEquals(change, lastEvent.sizeChange);
        } catch (CommandException e) {
            fail("This exception should not be thrown.");
        }
    }

    /**
     * Executes a {@code SizeCommand} with the given {@code change}, and checks that a {@code CommandException}
     * is thrown.
     */
    private void assertExecutionFailure(int change) {
        SizeCommand sizeCommand = prepareCommand(change);
        try {
            sizeCommand.execute();
            fail("CommandException should be thrown.");
        } catch (CommandException e) {
            assertEquals(String.format(SizeCommand.MESSAGE_FAILURE, 0, change), e.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
