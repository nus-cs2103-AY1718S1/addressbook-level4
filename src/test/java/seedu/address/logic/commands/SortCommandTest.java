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

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_sortsByName_sameList() {
        SortCommand sortCommand = prepareCommand(nameField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByName_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(nameField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, nameField), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByPhone_sameList() {
        SortCommand sortCommand = prepareCommand(phoneField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, phoneField), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByPhone_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(phoneField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, phoneField), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByEmail_sameList() {
        SortCommand sortCommand = prepareCommand(emailField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, emailField), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByEmail_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(emailField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, emailField), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByAddress_sameList() {
        SortCommand sortCommand = prepareCommand(addressField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, addressField), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByAddress_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(addressField);
        assertCommandSuccess(sortCommand, model,
                String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, addressField), expectedModel);
    }

    /**
     * Returns an {@code SortCommand} with parameter {@code datafield}
     */
    private SortCommand prepareCommand(String datafield) {
        SortCommand sortCommand = new SortCommand(datafield);
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
