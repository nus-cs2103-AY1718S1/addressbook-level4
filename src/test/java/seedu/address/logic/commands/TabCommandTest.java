package seedu.address.logic.commands;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.TabCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.testutil.TypicalParcels.getTypicalAddressBook;
import org.junit.Test;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class TabCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void execute() throws Exception {
        assertCommandFailure(prepareCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }
    /**
     * Returns an {@code RemarkCommand}.
     */
    private TabCommand prepareCommand() {
        TabCommand tabCommand = new TabCommand();
        tabCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return tabCommand;
    }
}
