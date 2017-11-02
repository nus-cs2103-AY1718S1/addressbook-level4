package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TAB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TAB;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToTabRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code TabCommand}.
 */
//@@author vicisapotato
public class TabCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexTabList_success() {
        assertExecutionSuccess(INDEX_FIRST_TAB);
        assertExecutionSuccess(INDEX_SECOND_TAB);
    }

    @Test
    public void execute_invalidIndexTabList_failure() {
        Index outOfBoundsIndex = Index.fromZeroBased(3);

        assertExecutionFailure(outOfBoundsIndex, TabCommand.MESSAGE_INVALID_TAB_INDEX);
    }

    @Test
    public void equals() {
        TabCommand tabFirstCommand = new TabCommand(INDEX_FIRST_TAB);
        TabCommand tabSecondCommand = new TabCommand(INDEX_SECOND_TAB);

        // same object -> returns true
        assertTrue(tabFirstCommand.equals(tabFirstCommand));

        // same values -> returns true
        TabCommand tabFirstCommandCopy = new TabCommand(INDEX_FIRST_TAB);
        assertTrue(tabFirstCommand.equals(tabFirstCommandCopy));

        // different types -> returns false
        assertFalse(tabFirstCommand.equals(1));

        // null -> returns false
        assertFalse(tabFirstCommand.equals(null));

        // different parcel -> returns false
        assertFalse(tabFirstCommand.equals(tabSecondCommand));
    }

    /**
     * Executes a {@code TabCommand} with the given {@code index}, and checks that {@code JumpToTabRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        TabCommand tabCommand = prepareCommand(index);

        try {
            CommandResult commandResult = tabCommand.execute();
            assertEquals(String.format(TabCommand.MESSAGE_SELECT_TAB_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToTabRequestEvent lastEvent = (JumpToTabRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        TabCommand tabCommand = prepareCommand(index);

        try {
            tabCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private TabCommand prepareCommand(Index index) {
        TabCommand tabCommand = new TabCommand(index);
        tabCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tabCommand;
    }
}
//@@author
