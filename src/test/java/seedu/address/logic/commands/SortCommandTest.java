package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.ListObserver;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author khooroko
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private static final String ORDERING_DEFAULT = "";
    private static final String ORDERING_NAME = "name";
    private static final String ORDERING_DEBT = "debt";
    private static final String ORDERING_CLUSTER = "cluster";
    private static final String ORDERING_DEADLINE = "deadline";
    private static final String INVALID_ORDERING = "height";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allOrderings_success() throws Exception {
        SortCommand sortCommand;
        String expectedMessage;

        Model expectedModel = model;

        expectedModel.sortBy(ORDERING_NAME);
        sortCommand = prepareCommand(ORDERING_DEFAULT);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_NAME);
        sortCommand = prepareCommand(ORDERING_NAME);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_NAME);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_DEBT);
        sortCommand = prepareCommand(ORDERING_DEBT);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_DEBT);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_CLUSTER);
        sortCommand = prepareCommand(ORDERING_CLUSTER);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_CLUSTER);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

        expectedModel.sortBy(ORDERING_DEADLINE);
        sortCommand = prepareCommand(ORDERING_DEADLINE);
        expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(SortCommand.MESSAGE_SUCCESS, ORDERING_DEADLINE);
        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_internalInvalidOrdering_throwsInvalidArgumentException() {
        Model expectedModel = model;

        thrown.expect(IllegalArgumentException.class);
        expectedModel.sortBy(INVALID_ORDERING);
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(ORDERING_NAME);
        SortCommand sortSecondCommand = new SortCommand(ORDERING_DEBT);

        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(ORDERING_NAME);
        assertTrue(sortFirstCommand.equals(sortFirstCommandCopy));

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
    }

    private SortCommand prepareCommand(String order) {
        SortCommand sortCommand = new SortCommand(order);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
