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
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listPersonCommand;
    private ListCommand listAddressCommand;
    private ListCommand listEmailCommand;
    private ListCommand listPhoneCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listPersonCommand = new ListCommand();
        listAddressCommand = new ListCommand("address");
        listEmailCommand = new ListCommand("email");
        listPhoneCommand = new ListCommand("phone");

        listPersonCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listAddressCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listEmailCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        listPhoneCommand.setData(model, new CommandHistory(), new UndoRedoStack());

    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listPersonCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.DEFAULT_LISTING_ELEMENT), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listPersonCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.DEFAULT_LISTING_ELEMENT), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllAddress() {
        assertCommandSuccess(listAddressCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.ATTRIBUTE_ADDRESS), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllEmail() {
        assertCommandSuccess(listEmailCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.ATTRIBUTE_EMAIL), expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsAllPhone() {
        assertCommandSuccess(listPhoneCommand, model, String.format(ListCommand.MESSAGE_SUCCESS,
                ListCommand.ATTRIBUTE_PHONE), expectedModel);
    }

}
