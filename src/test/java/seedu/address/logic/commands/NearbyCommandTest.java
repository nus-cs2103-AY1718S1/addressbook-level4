package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToNearbyListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author khooroko
/**
 * Contains integration tests (interaction with the Model) for {@code NearbyCommand}.
 */
public class NearbyCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_SECOND_PERSON);
    }

    @Test
    public void execute_noPersonSelected_failure() {
        assertExecutionFailure(INDEX_FIRST_PERSON, Messages.MESSAGE_NO_PERSON_SELECTED);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()));
        Index outOfBoundsIndex = Index.fromOneBased(model.getNearbyPersons().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        model.updateSelectedPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void equals() {
        NearbyCommand nearbyFirstCommand = new NearbyCommand(INDEX_FIRST_PERSON);
        NearbyCommand nearbySecondCommand = new NearbyCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(nearbyFirstCommand.equals(nearbyFirstCommand));

        // same values -> returns true
        NearbyCommand nearbyFirstCommandCopy = new NearbyCommand(INDEX_FIRST_PERSON);
        assertTrue(nearbyFirstCommand.equals(nearbyFirstCommandCopy));

        // different types -> returns false
        assertFalse(nearbyFirstCommand.equals(1));

        // null -> returns false
        assertFalse(nearbyFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(nearbyFirstCommand.equals(nearbySecondCommand));
    }

    /**
     * Executes a {@code NearbyCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        NearbyCommand nearbyCommand = prepareCommand(index);

        try {
            CommandResult commandResult = nearbyCommand.execute();
            assertEquals(String.format(ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                            + NearbyCommand.MESSAGE_NEARBY_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToNearbyListRequestEvent lastEvent =
                (JumpToNearbyListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code NearbyCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        NearbyCommand nearbyCommand = prepareCommand(index);

        try {
            nearbyCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code NearbyCommand} with parameters {@code index}.
     */
    private NearbyCommand prepareCommand(Index index) {
        NearbyCommand nearbyCommand = new NearbyCommand(index);
        nearbyCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return nearbyCommand;
    }
}
