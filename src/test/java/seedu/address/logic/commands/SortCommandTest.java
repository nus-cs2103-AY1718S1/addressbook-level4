package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPlaces.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author aungmyin23
public class SortCommandTest {

    private Model compareModel;
    private Model expectedModel;
    private SortCommand executeSort;

    /*
    * Setting up the model to compare with for the testing.
     */
    @Before
    public void setUp() {
        compareModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(compareModel.getAddressBook(), new UserPrefs());
        executeSort = new SortCommand();
        executeSort.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
    }

    /*
    * Comparing the two model to check whether the sort command give expected outcome
     */
    @Test
    public void execute() {
        assertCommandSuccess(executeSort, compareModel, SortCommand.SHOWING_SORT_MESSAGE, expectedModel);
    }
}
