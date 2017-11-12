package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.person.FbCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author dennaloh
/**
 * Contains integration tests (interaction with the Model) for {@code FbCommand}.
 */
public class FbCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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
        FbCommand fbFirstCommand = new FbCommand(INDEX_FIRST_PERSON);
        FbCommand fbSecondCommand = new FbCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(fbFirstCommand.equals(fbFirstCommand));

        // same values -> returns true
        FbCommand fbFirstCommandCopy = new FbCommand(INDEX_FIRST_PERSON);
        assertTrue(fbFirstCommand.equals(fbFirstCommandCopy));

        // different types -> returns false
        assertFalse(fbFirstCommand.equals(1));

        // null -> returns false
        assertFalse(fbFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(fbFirstCommand.equals(fbSecondCommand));
    }

    /**
     * Executes a {@code FbCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        FbCommand fbCommand = prepareCommand(index);

        try {
            fbCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code FbCommand} with parameters {@code index}.
     */
    private FbCommand prepareCommand(Index index) {
        FbCommand fbCommand = new FbCommand(index);
        fbCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return fbCommand;
    }
}
