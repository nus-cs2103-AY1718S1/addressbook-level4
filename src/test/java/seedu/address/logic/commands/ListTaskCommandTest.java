//@@author Qi Kongjia
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListTaskCommand.
 */
public class ListTaskCommandTest {

    private Model model;
    private Model expectedModel;
    private ListTaskCommand listTaskCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), getTypicalTaskbook(), new UserPrefs());

        listTaskCommand = new ListTaskCommand();
        listTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeListIsNotFilteredShowsSameList() {
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsFilteredShowsEverything() {
        showFirstTaskOnly(model);
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
