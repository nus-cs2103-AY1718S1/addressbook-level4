package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.StorageUtil.getDummyStorage;
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
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    //@@author keithsoc
    @Test
    public void execute_noOptionUnfilteredList_showsSameList() {
        assertCommandSuccess(prepareCommand(""), model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_noOptionFilteredList_showsAllPersons() {
        showFirstPersonOnly(model);
        assertCommandSuccess(prepareCommand(""), model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_noOptionExtraArgumentsUnfilteredList_showsSameList() {
        assertCommandSuccess(prepareCommand("abc"),
                model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);

        assertCommandSuccess(prepareCommand("FaV"),
                model, ListCommand.MESSAGE_SUCCESS_LIST_ALL, expectedModel);
    }

    @Test
    public void execute_favOptionUnfilteredList_showsAllFavoritePersons() {
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_FAV_PERSONS);
        assertCommandSuccess(prepareCommand(ListCommand.COMMAND_OPTION_FAV),
                model, ListCommand.MESSAGE_SUCCESS_LIST_FAV, expectedModel);
    }

    @Test
    public void execute_favOptionFilteredList_showsAllFavoritePersons() {
        showFirstPersonOnly(model);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_FAV_PERSONS);
        assertCommandSuccess(prepareCommand(ListCommand.COMMAND_OPTION_FAV),
                model, ListCommand.MESSAGE_SUCCESS_LIST_FAV, expectedModel);
    }

    /**
     * Returns a {@code ListCommand} with the parameter {@code argument}.
     */
    private ListCommand prepareCommand(String argument) {
        ListCommand listCommand = new ListCommand(argument);
        listCommand.setData(model, getDummyStorage(), new CommandHistory(), new UndoRedoStack());
        return listCommand;
    }
    //@@author
}
