package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPlaceOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PLACE;
//import static seedu.address.testutil.TypicalIndexes.INDEX_NINETH_PLACE;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PLACE;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PLACE;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.GotoRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;
//@@author Chng-Zhi-Xuan-reused
/**
 * Contains integration tests (interaction with the Model) for {@code DirectionCommand}.
 */
public class DirectionCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    /* Carrying out the test will lead to FXML.Load() failure.
    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPlaceIndex = Index.fromOneBased(model.getFilteredPlaceList().size());

        assertExecutionSuccess(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        assertExecutionSuccess(INDEX_THIRD_PLACE, INDEX_FIRST_PLACE);
        assertExecutionSuccess(lastPlaceIndex, INDEX_NINETH_PLACE);
    }
    */

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPlaceList().size() + 1);

        assertExecutionFailure(INDEX_FIRST_PLACE, outOfBoundsIndex, Messages.MESSAGE_INVALID_PLACE_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPlaceOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PLACE;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPlaceList().size());

        assertExecutionFailure(INDEX_FIRST_PLACE, outOfBoundsIndex, Messages.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void equals() {
        DirectionCommand dirFirstToSecondCommand = new DirectionCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        DirectionCommand dirSecondToThirdCommand = new DirectionCommand(INDEX_SECOND_PLACE, INDEX_THIRD_PLACE);

        // same object -> returns true
        assertTrue(dirFirstToSecondCommand.equals(dirFirstToSecondCommand));

        // same values -> returns true
        DirectionCommand dirFirstToSecondCommandCopy = new DirectionCommand(INDEX_FIRST_PLACE, INDEX_SECOND_PLACE);
        assertTrue(dirFirstToSecondCommand.equals(dirFirstToSecondCommandCopy));

        // different types -> returns false
        assertFalse(dirFirstToSecondCommand.equals(1));

        // null -> returns false
        assertFalse(dirFirstToSecondCommand.equals(null));

        // different place -> returns false
        assertFalse(dirFirstToSecondCommand.equals(dirSecondToThirdCommand));
    }

    /**
     * Executes a {@code DirectionCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index fromIndex, Index toIndex) {
        DirectionCommand directionCommand = prepareCommand(fromIndex, toIndex);

        try {
            CommandResult commandResult = directionCommand.execute();
            assertEquals(DirectionCommand.MESSAGE_DIR_SUCCESS,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        GotoRequestEvent lastEvent = (GotoRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(toIndex, toIndex.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code DirectionCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index fromIndex, Index toIndex, String expectedMessage) {
        DirectionCommand directionCommand = prepareCommand(fromIndex, toIndex);

        try {
            directionCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code DirectionCommand} with parameters {@code index}.
     */
    private DirectionCommand prepareCommand(Index fromIndex, Index toIndex) {
        DirectionCommand directionCommand = new DirectionCommand(fromIndex, toIndex);
        directionCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return directionCommand;
    }
}
