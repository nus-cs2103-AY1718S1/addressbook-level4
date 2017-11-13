//@@author tshradheya
package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalReminders.getUniqueTypicalReminders;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

public class LocationCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        LocationCommand locationCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still within bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        LocationCommand locationCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(locationCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final LocationCommand standardCommand = new LocationCommand(INDEX_FIRST_PERSON);
        final LocationCommand commandWithSameIndex = new LocationCommand(INDEX_FIRST_PERSON);
        final LocationCommand commandWithDifferentIndex = new LocationCommand(INDEX_SECOND_PERSON);


        // same object -> Returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different object, same type with same values -> return true
        assertTrue(standardCommand.equals(commandWithSameIndex));

        // same type but different index -> returns false
        assertFalse(standardCommand.equals(commandWithDifferentIndex));

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void checkIfEventCollected() throws CommandException {
        LocationCommand locationCommand = prepareCommand(INDEX_FIRST_PERSON);
        locationCommand.execute();
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowLocationEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 3);
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that {@code ShowLocationEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            CommandResult commandResult = locationCommand.execute();
            assertEquals(String.format(LocationCommand.MESSAGE_FIND_LOCATION_SUCCESS,
                    model.getFilteredPersonList().get(index.getZeroBased()).getName().fullName,
                    model.getFilteredPersonList().get(index.getZeroBased()).getAddress().value),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ShowLocationEvent lastEvent = (ShowLocationEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(model.getFilteredPersonList().get(index.getZeroBased()), lastEvent.person);
    }

    /**
     * Returns an {@code LocationCommand} with parameters {@code index}}
     */
    private LocationCommand prepareCommand(Index index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
}
