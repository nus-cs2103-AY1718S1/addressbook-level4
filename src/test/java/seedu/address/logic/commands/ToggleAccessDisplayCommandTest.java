package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ToggleAccessDisplayCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        assertCommandSuccess(prepareCommand(model, true), model,
                ToggleAccessDisplayCommand.MESSAGE_SUCCESS + "on. ", model);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model, true), model,
                ToggleAccessDisplayCommand.MESSAGE_SUCCESS + "on. ", model);
    }

    //@@author Zzmobie
    @Test
    public void execute_toggleOffNonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model, false), model,
                ToggleAccessDisplayCommand.MESSAGE_SUCCESS + "off. ", model);
    }
    //@@author

    /**
     * Generates a new {@code ClearCommand} which upon execution, clears the contents in {@code model}.
     */
    private ToggleAccessDisplayCommand prepareCommand(Model model, boolean isDisplayed) {
        ToggleAccessDisplayCommand command = new ToggleAccessDisplayCommand(isDisplayed);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
