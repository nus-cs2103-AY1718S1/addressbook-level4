package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.ChangeWindowSizeRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author vivekscl
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code ChangeWindowSizeCommand}.
 */
public class ChangeWindowSizeCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeChangeWindowSizeSuccess() throws Exception {
        ChangeWindowSizeCommand changeWindowSizeCommand =
                prepareCommand(ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM);
        CommandResult commandResult = changeWindowSizeCommand.execute();

        String expectedMessage = ChangeWindowSizeCommand.MESSAGE_SUCCESS + ChangeWindowSizeCommand.SMALL_WIDTH
                + " x " + ChangeWindowSizeCommand.SMALL_HEIGHT;

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeWindowSizeRequestEvent);
    }

    @Test
    public void executeInvalidChangeWindowSizeFailure() throws Exception {
        ChangeWindowSizeCommand changeWindowSizeCommand =
                prepareCommand(ChangeWindowSizeCommand.INVALID_WINDOW_SIZE_PARAM);

        String expectedMessage = ChangeWindowSizeCommand.MESSAGE_WINDOW_SIZE_CONSTRAINTS;

        assertCommandFailure(changeWindowSizeCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        ChangeWindowSizeCommand changeWindowSizeToSmall =
                new ChangeWindowSizeCommand(ChangeWindowSizeCommand.SMALL_WINDOW_SIZE_PARAM);
        ChangeWindowSizeCommand changeWindowSizeToMedium =
                new ChangeWindowSizeCommand(ChangeWindowSizeCommand.MEDIUM_WINDOW_SIZE_PARAM);
        ChangeWindowSizeCommand changeWindowSizeToBig =
                new ChangeWindowSizeCommand(ChangeWindowSizeCommand.BIG_WINDOW_SIZE_PARAM);

        // same object -> returns true
        assertTrue(changeWindowSizeToSmall.equals(changeWindowSizeToSmall));

        // same values -> returns true
        ChangeWindowSizeCommand changeWindowSizeToSmallCopy = new ChangeWindowSizeCommand("small");
        assertTrue(changeWindowSizeToSmall.equals(changeWindowSizeToSmallCopy));

        // different types -> returns false
        assertFalse(changeWindowSizeToSmall.equals(1));
        assertFalse(changeWindowSizeToSmall.equals(new ShowFavouriteCommand()));

        // null -> returns false
        assertFalse(changeWindowSizeToSmall.equals(null));

        // different person -> returns false
        assertFalse(changeWindowSizeToSmall.equals(changeWindowSizeToBig));
        assertFalse(changeWindowSizeToSmall.equals(changeWindowSizeToMedium));
    }

    /**
     * Returns a {@code ChangeWindowSizeCommand} with the parameter {@code index}.
     */
    private ChangeWindowSizeCommand prepareCommand(String windowSize) {
        ChangeWindowSizeCommand changeWindowSizeCommand = new ChangeWindowSizeCommand(windowSize);
        changeWindowSizeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changeWindowSizeCommand;
    }

}
