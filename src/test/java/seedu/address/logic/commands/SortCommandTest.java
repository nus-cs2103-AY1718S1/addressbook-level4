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


public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommandName;
    private SortCommand sortCommandPhone;
    private SortCommand sortCommandEmail;


    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortCommandName = new SortCommand("name");
        sortCommandName.setData(model, new CommandHistory(), new UndoRedoStack());

        sortCommandPhone = new SortCommand("name");
        sortCommandPhone.setData(model, new CommandHistory(), new UndoRedoStack());

        sortCommandEmail = new SortCommand("name");
        sortCommandEmail.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortByName() {
        assertCommandSuccess(sortCommandName, model, sortCommandName.MESSAGE_SORT_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortByPhone() {
        assertCommandSuccess(sortCommandPhone, model, sortCommandPhone.MESSAGE_SORT_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortByEmail() {
        assertCommandSuccess(sortCommandEmail, model, sortCommandPhone.MESSAGE_SORT_SUCCESS, expectedModel);
    }
}
