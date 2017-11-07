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
import seedu.address.commons.events.ui.BrowserPanelFindRouteEvent;
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
 * Contains integration tests (interaction with the Model) for {@code MapRouteCommand}.
 */
public class MapRouteCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private String startLocation = "Clementi Street";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, startLocation);
        assertExecutionSuccess(INDEX_THIRD_PERSON, startLocation);
        assertExecutionSuccess(lastPersonIndex, startLocation);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, startLocation, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON, startLocation);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, startLocation, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MapRouteCommand mapRouteFirstCommand = new MapRouteCommand(INDEX_FIRST_PERSON, startLocation);
        MapRouteCommand mapRouteSecondCommand = new MapRouteCommand(INDEX_SECOND_PERSON, startLocation);

        // same object -> returns true
        assertTrue(mapRouteFirstCommand.equals(mapRouteFirstCommand));

        // same values -> returns true
        MapRouteCommand mapRouteFirstCommandCopy = new MapRouteCommand(INDEX_FIRST_PERSON, startLocation);
        assertTrue(mapRouteFirstCommand.equals(mapRouteFirstCommandCopy));

        // different types -> returns false
        assertFalse(mapRouteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(mapRouteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(mapRouteFirstCommand.equals(mapRouteSecondCommand));
    }

    /**
     * Executes a {@code MapRouteCommand} with the given {@code index, location}, and checks that
     * {@code BrowserPanelFindRouteEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String location) {
        MapRouteCommand mapRouteCommand = prepareCommand(index, location);

        try {
            CommandResult commandResult = mapRouteCommand.execute();
            assertEquals(String.format(mapRouteCommand.MESSAGE_FIND_ROUTE_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        BrowserPanelFindRouteEvent lastEvent = (BrowserPanelFindRouteEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.getSelectedPerson());
        assertEquals(location, lastEvent.getAddress());
    }

    /**
     * Executes a {@code MapRouteCommand} with the given {@code index, location}, and checks that a
     * {@code CommandException} is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String location, String expectedMessage) {
        MapRouteCommand mapRouteCommand = prepareCommand(index, location);

        try {
            mapRouteCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code MapRouteCommand} with parameters {@code index, location}.
     */
    private MapRouteCommand prepareCommand(Index index, String location) {
        MapRouteCommand mapRouteCommand = new MapRouteCommand(index, location);
        mapRouteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return mapRouteCommand;
    }
}
