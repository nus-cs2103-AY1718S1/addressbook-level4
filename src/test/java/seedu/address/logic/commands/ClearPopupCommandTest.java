package seedu.address.logic.commands;
//@@author pjunwei95
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearPopupCommandTest {

    @Test
    public void execute_emptyAddressBook() {
        Model model = new ModelManager();
        ClearPopupCommand clearPopupCommand = new ClearPopupCommand();
        assertEquals(prepareCommand(model, clearPopupCommand), clearPopupCommand);
    }

    @Test
    public void execute_nonEmptyAddressBook() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        ClearPopupCommand clearPopupCommand = new ClearPopupCommand();
        assertEquals(prepareCommand(model, clearPopupCommand), clearPopupCommand);
    }

    /**
     * Generates a new {@code ClearPopupCommand} which upon execution, clears the contents in {@code model}.
     */
    private ClearPopupCommand prepareCommand(Model model, ClearPopupCommand clearPopupCommand) {
        ClearPopupCommand command = new ClearPopupCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return clearPopupCommand;
    }
}
