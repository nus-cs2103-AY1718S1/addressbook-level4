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

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.AccessLocationRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code LocationCommand}.
 */
public class LocationCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
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

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LocationCommand locateFirstPersonCommand = new LocationCommand(INDEX_FIRST_PERSON);
        LocationCommand locateSecondPersonCommand = new LocationCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(locateFirstPersonCommand.equals(locateFirstPersonCommand));

        // same values -> returns true
        LocationCommand locateFirstCommandCopy = new LocationCommand(INDEX_FIRST_PERSON);
        assertTrue(locateFirstPersonCommand.equals(locateFirstCommandCopy));

        // different types -> returns false
        assertFalse(locateFirstPersonCommand.equals(1));

        // null -> returns false
        assertFalse(locateFirstCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(locateFirstCommandCopy.equals(locateSecondPersonCommand));
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocationCommand locationCommand = prepareCommand(index);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        String name = person.getName().toString();

        try {
            CommandResult commandResult = locationCommand.execute();
            assertEquals(String.format(locationCommand.MESSAGE_LOCATE_PERSON_SUCCESS, index.getOneBased(), name),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        AccessLocationRequestEvent lastEvent = (AccessLocationRequestEvent) eventsCollectorRule
                .eventsCollector.getMostRecent();
        assertEquals(person.getAddress().toString(), lastEvent.location);
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            locationCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocationCommand} with parameters {@code index}.
     */
    private LocationCommand prepareCommand(Index index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
}
