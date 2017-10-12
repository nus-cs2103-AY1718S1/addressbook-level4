package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getSortedTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;
    private final String filterType = "name";

    @Test
    public void execute_unsortedList_becomesSorted() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void execute_filteredList_showsEverything() {
        model = new ModelManager(getUnsortedTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs());
        showFirstPersonOnly(model);

        sortCommand = prepareCommand(filterType);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);

    }

    @Test
    public void equals() {

        final SortCommand standardCommand = new SortCommand(filterType);

        // same filterTypes -> returns true
        SortCommand commandWithSameValues = new SortCommand(filterType);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different filterTypes -> returns false
        assertFalse(standardCommand.equals(new SortCommand("default")));

    }

    /**
     * Returns a {@code SortCommand} with parameters {@code filterType}.
     */
    private SortCommand prepareCommand(String filterType) {
        SortCommand sortCommand = new SortCommand(filterType);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
