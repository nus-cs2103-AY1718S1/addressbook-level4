package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.FontSizeChangeRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
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
        CommandResult commandResult = sizeCommand.execute();
        assertEquals(SizeCommand.MESSAGE_RESET_FONT_SUCCESS, commandResult.feedbackToUser);

        FontSizeChangeRequestEvent lastEvent =
                (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertTrue(lastEvent.isReset);
    }

    @Test
    public void execute_increaseSize() {
        SizeCommand sizeCommand = prepareCommand(3);
        CommandResult commandResult = sizeCommand.execute();
        assertEquals(String.format(SizeCommand.MESSAGE_CHANGE_FONT_SUCCESS, "increased", 3),
                commandResult.feedbackToUser);

        FontSizeChangeRequestEvent lastEvent =
                (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertFalse(lastEvent.isReset);
        assertEquals(3, lastEvent.sizeChange);
    }

    @Test
    public void execute_decreaseSize() {
        SizeCommand sizeCommand = prepareCommand(-3);
        CommandResult commandResult = sizeCommand.execute();
        assertEquals(String.format(SizeCommand.MESSAGE_CHANGE_FONT_SUCCESS, "decreased", 3),
                     commandResult.feedbackToUser);

        FontSizeChangeRequestEvent lastEvent =
                (FontSizeChangeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertFalse(lastEvent.isReset);
        assertEquals(-3, lastEvent.sizeChange);
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
}
