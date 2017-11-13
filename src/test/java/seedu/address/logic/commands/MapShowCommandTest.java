package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelShowLocationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author dalessr
/**
 * Contains integration tests (interaction with the Model) for {@code MapShowCommand}.
 */
public class MapShowCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MapShowCommand mapShowFirstCommand = new MapShowCommand(INDEX_FIRST_PERSON);
        MapShowCommand mapShowSecondCommand = new MapShowCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(mapShowFirstCommand.equals(mapShowFirstCommand));

        // same values -> returns true
        MapShowCommand mapShowFirstCommandCopy = new MapShowCommand(INDEX_FIRST_PERSON);
        assertTrue(mapShowFirstCommand.equals(mapShowFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapShowFirstCommand.equals(1));

        // null -> returns false
        assertFalse(mapShowFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapShowFirstCommand.equals(mapShowSecondCommand));
    }

    /**
     * Executes a {@code MapShowCommand} with the given {@code index}, and checks that
     * {@code BrowserPanelShowLocationEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        MapShowCommand mapShowCommand = prepareCommand(index);

        try {
            CommandResult commandResult = mapShowCommand.execute();
            assertEquals(String.format(MapShowCommand.MESSAGE_LOCATE_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        BrowserPanelShowLocationEvent lastEvent = (BrowserPanelShowLocationEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.getNewSelection());
    }

    /**
     * Executes a {@code MapShowCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        MapShowCommand mapShowCommand = prepareCommand(index);

        try {
            mapShowCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MapShowCommand} with parameters {@code index}.
     */
    private MapShowCommand prepareCommand(Index index) {
        MapShowCommand mapShowCommand = new MapShowCommand(index);
        mapShowCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapShowCommand;
    }
}
