package seedu.address.logic.commands;

import static seedu.address.logic.commands.ChangePictureCommand.MESSAGE_NOT_IMPLEMENTED_YET;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ChangePictureCommand.
 */
public class ChangePictureCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() throws Exception {
        assertCommandFailure(prepareCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
    }

    /**
     * Returns an {@code ChangePictureCommand}.
     */
    private ChangePictureCommand prepareCommand() {
        ChangePictureCommand changePictureCommand = new ChangePictureCommand();
        changePictureCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return changePictureCommand;
    }
}
