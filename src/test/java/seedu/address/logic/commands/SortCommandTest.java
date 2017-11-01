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
    private String FIELD_NAME = "name";
    private String FIELD_PHONE = "phone";
    private String FIELD_EMAIL = "email";
    private String FIELD_ADDRESS = "address";

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_sortsByName_sameList() {
        SortCommand sortCommand = prepareCommand(FIELD_NAME);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_NAME), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByName_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(FIELD_NAME);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_NAME), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByPhone_sameList() {
        SortCommand sortCommand = prepareCommand(FIELD_PHONE);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_PHONE), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByPhone_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(FIELD_PHONE);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_PHONE), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByEmail_sameList() {
        SortCommand sortCommand = prepareCommand(FIELD_EMAIL);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_EMAIL), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByEmail_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(FIELD_EMAIL);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_EMAIL), expectedModel);
    }

    @Test
    public void execute_listIsNotFiltered_sortsByAddress_sameList() {
        SortCommand sortCommand = prepareCommand(FIELD_ADDRESS);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_ADDRESS), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_sortsByAddress_sameList() {
        showFirstPersonOnly(model);
        showFirstPersonOnly(expectedModel);
        SortCommand sortCommand = prepareCommand(FIELD_ADDRESS);
        assertCommandSuccess(sortCommand, model, String.format(SortCommand.MESSAGE_SORT_LIST_SUCCESS, FIELD_ADDRESS), expectedModel);
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
