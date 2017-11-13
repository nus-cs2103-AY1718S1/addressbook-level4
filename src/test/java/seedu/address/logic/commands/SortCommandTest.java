package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalDescendingOrderAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.FindHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author karrui
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    public static final boolean DESCENDING = true;
    public static final boolean ASCENDING = false;

    private Model model;

    @Test
    public void execute_decendingSort_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand sortCommand = prepareCommand(DESCENDING);
        Model expectedModel = new ModelManager(getTypicalDescendingOrderAddressBook(), new UserPrefs());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_ascendingSort_success() {
        model = new ModelManager(getTypicalDescendingOrderAddressBook(), new UserPrefs());
        SortCommand sortCommand = prepareCommand(ASCENDING);
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(sortCommand, model, expectedMessage, expectedModel);
    }

    private SortCommand prepareCommand(boolean isDescendingSort) {
        SortCommand sortCommand = new SortCommand(isDescendingSort);
        sortCommand.setData(model, new CommandHistory(), new FindHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
