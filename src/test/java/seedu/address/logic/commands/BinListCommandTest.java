package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalRecycleBin;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author frozventus
/**
 * Contains integration tests (interaction with the Model) and unit tests for BinListCommand.
 */
public class BinListCommandTest {

    private Model model;
    private Model expectedModel;
    private BinListCommand binListCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        model.setBinDisplay();
        expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.setBinDisplay();

        binListCommand = new BinListCommand();
        binListCommand.setData(model, new CommandHistory(), new UndoRedoStack(), true);
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(binListCommand, model, BinListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model, true);
        assertCommandSuccess(binListCommand, model, BinListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
