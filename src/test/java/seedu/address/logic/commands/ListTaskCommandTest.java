package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.TaskTestUtil.showFirstTaskOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

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
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listTaskCommand = new ListTaskCommand();
        listTaskCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    //@@author lancehaoh
    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //@@author lancehaoh
    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstTaskOnly(model);
        assertCommandSuccess(listTaskCommand, model, ListTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
