package seedu.room.logic.commands;

import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Before;
import org.junit.Test;

import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalResidentBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
