package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

//@@author sidhmads
public class SortCommandTest {

    @Test
    public void executeSortOnEmptyAddressBookSuccess() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model), model, SortCommand.COMMAND_SUCCESS, model);
    }

    @Test
    public void executeSortOnNonEmptyAddressBookSuccess() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, SortCommand.COMMAND_SUCCESS, model);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contents in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
