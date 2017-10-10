package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.SortCommand.MESSAGE_ARGUMENTS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        final String filterType = "name";

        assertCommandFailure(prepareCommand(filterType), model, String.format(MESSAGE_ARGUMENTS, filterType));
    }

    @Test
    public void equals() {

        final String filterType = "name";
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
