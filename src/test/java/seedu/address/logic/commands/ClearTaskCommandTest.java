//@@author ShaocongDong
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalTasks.getTypicalTaskbook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.TaskBook;
import seedu.address.model.UserPrefs;

public class ClearTaskCommandTest {

    @Test
    public void executeEmptyAddressBookSuccess() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, ClearTaskCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeNonEmptyTaskBookSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalTaskbook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new TaskBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, ClearTaskCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code ClearTaskCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearTaskCommand prepareCommand(Model model) {
        ClearTaskCommand command = new ClearTaskCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
