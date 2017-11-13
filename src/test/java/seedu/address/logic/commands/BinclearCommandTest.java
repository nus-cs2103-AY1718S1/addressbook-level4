package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecycleBin.getTypicalRecyclbin;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
//@@author Pengyuz
public class BinclearCommandTest {

    @Test
    public void execute_emptyRecyclebin_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, BinclearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecyclbin(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, BinclearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code BinclearCommand} which upon execution, clears the contents in address book in {@code model}.
     */
    private BinclearCommand prepareCommand(Model model) {
        BinclearCommand command = new BinclearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
