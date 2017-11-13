//@@author Houjisan
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private String nameField = "name";
    private String phoneField = "phone";
    private String emailField = "email";
    private String addressField = "address";
    private boolean favIgnored = true;
    private boolean favNotIgnored = false;
    private boolean notReverseOrder = false;
    private boolean reverseOrder = true;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFilteredSortsByName_sameList() {
        SortCommand sortCommand = prepareCommand(nameField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByName_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(nameField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByPhone_sameList() {
        SortCommand sortCommand = prepareCommand(phoneField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, phoneField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByPhone_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(phoneField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, phoneField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByEmail_sameList() {
        SortCommand sortCommand = prepareCommand(emailField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, emailField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByEmail_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(emailField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, emailField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByAddress_sameList() {
        SortCommand sortCommand = prepareCommand(addressField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, addressField), expectedModel);
    }

    @Test
    public void execute_listIsFilteredSortsByAddress_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(addressField, favNotIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, addressField), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByNameIgnoreFav_sameList() {
        SortCommand sortCommand = prepareCommand(nameField, favIgnored, notReverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField)
                        + " ignoring favourites", expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredSortsByNameReverseOrder_sameList() {
        SortCommand sortCommand = prepareCommand(nameField, favNotIgnored, reverseOrder);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField)
                        + " in reverse order", expectedModel);
    }

    /**
     * Returns an {@code SortCommand} with parameter {@code datafield}
     */
    private SortCommand prepareCommand(String datafield, boolean isFavIgnored, boolean isReverseOrder) {
        SortCommand sortCommand = new SortCommand(datafield, isFavIgnored, isReverseOrder);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
