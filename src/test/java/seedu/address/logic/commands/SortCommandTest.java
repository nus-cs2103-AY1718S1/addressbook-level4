package seedu.address.logic.commands;

import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.graph.GraphWrapper;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private Model unsortedModel;
    private SortCommand sortCommand;
    private GraphWrapper graphWrapper;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        graphWrapper = new GraphWrapper();
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        unsortedModel = new ModelManager(getUnsortedAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack(), graphWrapper);
    }

    @Test
    public void execute_sortListIsNotFiltered_showsSameSortList() {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortListIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        unsortedModel.sortPersons();

        // sort success -> returns true
        assertTrue(expectedModel.equals(unsortedModel));
    }
}
