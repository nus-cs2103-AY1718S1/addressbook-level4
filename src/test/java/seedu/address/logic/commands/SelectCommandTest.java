package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectCommandTest extends CommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

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
    public void execute_noIndex_success() {
        assertExecutionSuccess(null);
        assertExecutionSuccess(null);
    }

    @Test
    public void execute_emptyList_failure() {
        model = new ModelManager();
        ListObserver.init(model);
        assertExecutionFailure(null, SelectCommand.MESSAGE_EMPTY_LIST_SELECTION_FAILURE);
    }

    @Test
    public void equals() {
        try {
            SelectCommand selectFirstCommand = new SelectCommand(INDEX_FIRST_PERSON);
            SelectCommand selectSecondCommand = new SelectCommand(INDEX_SECOND_PERSON);
            SelectCommand selectThirdCommand = new SelectCommand();

            // same object -> returns true
            assertTrue(selectFirstCommand.equals(selectFirstCommand));
            assertTrue(selectThirdCommand.equals(selectThirdCommand));

            // same values -> returns true
            SelectCommand selectFirstCommandCopy = new SelectCommand(INDEX_FIRST_PERSON);
            assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

            // different types -> returns false
            assertFalse(selectFirstCommand.equals(1));

            // null -> returns false
            assertFalse(selectFirstCommand.equals(null));

            // different person -> returns false
            assertFalse(selectFirstCommand.equals(selectSecondCommand));
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        try {
            SelectCommand selectCommand = prepareCommand(index);
            CommandResult commandResult = selectCommand.execute();
            assertEquals(ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(SelectCommand.MESSAGE_SELECT_PERSON_SUCCESS,
                    ListObserver.getIndexOfSelectedPersonInCurrentList().getOneBased()), commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        JumpToListRequestEvent lastEvent = (JumpToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        if (index != null) {
            assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        }
        assertEquals(ListObserver.getIndexOfSelectedPersonInCurrentList(),
                Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        try {
            SelectCommand selectCommand = prepareCommand(index);
            selectCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectCommand} with parameters {@code index}.
     */
    private SelectCommand prepareCommand(Index index) throws CommandException {
        SelectCommand selectCommand;
        if (index == null) {
            selectCommand = new SelectCommand();
        } else {
            selectCommand = new SelectCommand(index);
        }
        selectCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectCommand;
    }
}
