package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalPersons.getTypicalRolodex;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;


public class EmailCommandTest {

    private Model model = new ModelManager(getTypicalRolodex(), new UserPrefs());

    /*
    This test is disabled because the Desktop class is not supported on Ubuntu
    AppVeyor build succeeds, but Travis build fails because of the above reason
    This test can be enabled on local (Windows or MacOS)

    @Test
    public void executeOpenEmailSuccess() throws CommandException {
        EmailCommand command = new EmailCommand(INDEX_FIRST_PERSON, "Hello");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
    }
    */

    @Test
    public void executeInvalidPersonIndexFailure() throws CommandException {
        Index outOfBoundIndex = Index.fromOneBased(model.getLatestPersonList().size() + 1);
        EmailCommand command = new EmailCommand(outOfBoundIndex, "Hello");
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
}
