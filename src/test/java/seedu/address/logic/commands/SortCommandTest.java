package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalSortTypes.SORT_TYPE_NAME;
import static seedu.address.testutil.TypicalSortTypes.SORT_TYPE_PHONE;
import static seedu.address.testutil.TypicalSortTypes.SORT_TYPE_EMAIL;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class SortCommandTest {
    private Model model;
    private SortCommand sortCommandName;
    private SortCommand sortCommandPhone;
    private SortCommand sortCommandEmail;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_sortByName() {
        sortCommandName = new SortCommand("name");
        sortCommandName.setData(model, new CommandHistory(), new UndoRedoStack());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sort("name");

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, SORT_TYPE_NAME);
        assertCommandSuccess(sortCommandName, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByPhone() {
        sortCommandPhone = new SortCommand("phone");
        sortCommandPhone.setData(model, new CommandHistory(), new UndoRedoStack());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sort("phone");

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, (SORT_TYPE_PHONE + " number"));
        assertCommandSuccess(sortCommandPhone, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_sortByEmail() {
        sortCommandEmail = new SortCommand("email");
        sortCommandEmail.setData(model, new CommandHistory(), new UndoRedoStack());

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.sort("email");

        String expectedMessage = String.format(SortCommand.MESSAGE_SORT_SUCCESS, SORT_TYPE_EMAIL);
        assertCommandSuccess(sortCommandEmail, model, expectedMessage, expectedModel);
    }
}
