package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstMeetingOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_MEETING;
import static seedu.address.testutil.TypicalMeetings.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author Melvin-leo
/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class SelectMeetingCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredMeetingList().size());

        assertExecutionSuccess(INDEX_FIRST_MEETING);
        assertExecutionSuccess(INDEX_THIRD_MEETING);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredMeetingList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstMeetingOnly(model);

        assertExecutionSuccess(INDEX_FIRST_MEETING);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstMeetingOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_MEETING;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getMeetingList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectMeetingCommand selectFirstCommand = new SelectMeetingCommand(INDEX_FIRST_MEETING);
        SelectMeetingCommand selectSecondCommand = new SelectMeetingCommand(INDEX_SECOND_MEETING);

        // same object -> returns true
        assertTrue(selectFirstCommand.equals(selectFirstCommand));

        // same values -> returns true
        SelectMeetingCommand selectFirstCommandCopy = new SelectMeetingCommand(INDEX_FIRST_MEETING);
        assertTrue(selectFirstCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(selectFirstCommand.equals(selectSecondCommand));
    }

    /**
     * Executes a {@code SelectMeetingCommand} with the given {@code index}, and checks that
     * {@code JumpToMeetingListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        SelectMeetingCommand selectMeetingCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectMeetingCommand.execute();
            assertEquals(String.format(SelectMeetingCommand.MESSAGE_SELECT_MEETING_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }
        JumpToMeetingListRequestEvent lastEvent =
                (JumpToMeetingListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
    }

    /**
     * Executes a {@code SelectMeetingCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectMeetingCommand selectMeetingCommand = prepareCommand(index);

        try {
            selectMeetingCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectMeetingCommand} with parameters {@code index}.
     */
    private SelectMeetingCommand prepareCommand(Index index) {
        SelectMeetingCommand selectMeetingCommand = new SelectMeetingCommand(index);
        selectMeetingCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectMeetingCommand;
    }
}
