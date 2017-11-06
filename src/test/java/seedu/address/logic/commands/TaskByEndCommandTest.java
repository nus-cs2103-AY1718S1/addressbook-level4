package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
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
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class TaskByEndCommandTest {

    private Model model;
    private Model expectedModel;
    private TaskByEndCommand taskByEndCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        taskByEndCommand = new TaskByEndCommand();
        taskByEndCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute() {
        expectedModel = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        expectedModel.taskByEnd();
        assertCommandSuccess(taskByEndCommand, model, taskByEndCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
