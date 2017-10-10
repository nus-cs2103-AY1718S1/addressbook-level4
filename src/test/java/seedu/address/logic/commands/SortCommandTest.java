package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.SortCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
/**
 * Contains integration tests (interaction with the Model) for {@code SortCommand}.
 */
public class SortCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        assertCommandFailure(prepareCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }

    /**
     * Returns a {@code SortCommand}.
     */
    private SortCommand prepareCommand() {
        SortCommand sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return sortCommand;
    }
}
