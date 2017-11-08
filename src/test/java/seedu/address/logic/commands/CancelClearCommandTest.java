package seedu.address.logic.commands;
//@@author pjunwei95
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CancelClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, CancelClearCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, CancelClearCommand.MESSAGE_FAILURE, model);
    }

    /**
     * Generates a new {@code CancelClearCommand} which upon execution, retains the contents in {@code model}.
     */
    private CancelClearCommand prepareCommand(Model model) {
        CancelClearCommand command = new CancelClearCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
