package seedu.address.logic.commands;

import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.*;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.task.exceptions.DuplicateTaskException;

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
        TaskBook expectedTaskBook = new TaskBook();
        try {
            expectedTaskBook.addTask(MEETING);
            expectedTaskBook.addTask(EXAM);
            expectedTaskBook.addTask(PICNIC);
        } catch (DuplicateTaskException e) {
            fail("Impossible");
        }
        expectedModel = new ModelManager(getTypicalAddressBook(), expectedTaskBook, new UserPrefs());
        assertCommandSuccess(taskByEndCommand, model, taskByEndCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
