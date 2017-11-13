package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPersons.getTypicalRecycleBin;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@ author frozventus
public class BinClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, BinClearCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecycleBin(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, BinClearCommand.MESSAGE_SUCCESS, model);
    }

    /**
     * Generates a new {@code BinClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private BinClearCommand prepareCommand(Model model) {
        BinClearCommand command = new BinClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack(), false);
        return command;
    }
}
