//@@author
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getSortedAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model modelSortedByPhone = new ModelManager(getSortedAddressBook("phone"), new UserPrefs());
    private Model modelSortedByAddress = new ModelManager(getSortedAddressBook("address"), new UserPrefs());
    private Model modelSortedByEmail = new ModelManager(getSortedAddressBook("email"), new UserPrefs());

    @Test
    public void execute_sortByName_success() throws Exception {
        SortCommand sortCommand = prepareCommand("n/", model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "name");
        assertCommandSuccess(sortCommand, model, expectedMessage, model);
    }

    @Test
    public void execute_sortByPhone_success() throws Exception {
        SortCommand sortCommand = prepareCommand("p/", model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "phone");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByPhone);
    }

    @Test
    public void execute_sortByAddress_success() throws Exception {
        SortCommand sortCommand = prepareCommand("a/", model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "address");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByAddress);
    }

    @Test
    public void execute_sortByEmail_success() throws Exception {
        SortCommand sortCommand = prepareCommand("e/", model);
        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, "email");
        assertCommandSuccess(sortCommand, model, expectedMessage, modelSortedByEmail);
    }

    /**
     * Returns a {@code sortCommand} with the parameter {@code sortBy}.
     */
    private SortCommand prepareCommand(String sortBy,  Model model) {
        SortCommand sortCommand = new SortCommand(sortBy);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
